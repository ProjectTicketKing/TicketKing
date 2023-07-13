package com.example.ticketKing.domain.Member.service;

import com.example.ticketKing.domain.Member.dto.EmailDto;
import com.example.ticketKing.domain.Member.dto.JoinFormDto;
import com.example.ticketKing.domain.Member.email.MailSenderRunner;
import com.example.ticketKing.domain.Member.dto.MemberDto;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.finder.ModifyForm;
import com.example.ticketKing.domain.Member.repository.MemberRepository;
import com.example.ticketKing.domain.photo.entity.Photo;
import com.example.ticketKing.domain.photo.controller.PhotoController;
import com.example.ticketKing.domain.photo.repository.PhotoRepository;
import com.example.ticketKing.global.exception.DuplicateUsernameException;
import com.example.ticketKing.global.rsData.RsData;

import org.springframework.security.authentication.AuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MailSenderRunner mailSenderRunner;

    private final AuthenticationManager authenticationManager;

    private final TemplateEngine templateEngine;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final PhotoController photoController;

    private final PhotoRepository photoRepository;

    public Member join(JoinFormDto dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();

        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new DuplicateUsernameException("이미 존재하는 아이디입니다.");
        } else if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateUsernameException("이미 존재하는 이메일입니다.");
        }
        return memberRepository.save(member);
    }

    public void authenticateAccountAndSetSession(JoinFormDto member, HttpServletRequest request) {
        // 사용자 인증
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                member.getUsername(),
                member.getPassword()
        );
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        } catch (DisabledException | LockedException | BadCredentialsException e) {
            String status;
            if (e.getClass().equals(BadCredentialsException.class)) {
                status = "invalid-password";
            } else if (e.getClass().equals(DisabledException.class)) {
                status = "locked";
            } else if (e.getClass().equals(LockedException.class)) {
                status = "disable";
            } else {
                status = "unknown";
            }
            System.out.println("catch" + status);
        }
    }

    public Member create(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberFromUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public RsData<Member> modifyInfo(Long id, ModifyForm input, HttpServletRequest request) throws IOException {
        Member target = memberRepository.findById(id).orElse(null);
        if (target == null) {
            return RsData.of("F-1", "대상을 찾을 수 없습니다.");
        }
        RsData<Member> result;

        /* 프로필 이미지 변경 */
        result = canModifyPhoto(target, input);
        if (result.isSuccess()) {
//            // 사진 업로드
//            RsData<Photo> photo = photoController.parseFileInfo(input.getFile());
//            if (photo.isSuccess()) {
//                if (target.getPhoto() != null) {
//                    Photo oldPhoto = target.getPhoto();
//                    target.setPhoto(null);
//                    memberRepository.save(target);
//                    photoRepository.delete(oldPhoto);
//                }
//                target.setPhoto(photo.getData());
//            } else { // 사진 업로드 실패할 경우 알림 출력
//                String photoResultCode = photo.getResultCode();
//                String photoMsg = photo.getMsg();
//                return RsData.of(photoResultCode, photoMsg);
//            }
        } else if (!result.getResultCode().equals("F-1")) {
            return result;
        }

        /* 비밀번호 변경 */
        result = canModifyPassword(input);
        if (result.isSuccess()) {
            target.setPassword(input.getPassword());
            modifyLoginSetSession(target, input.getPassword(), request);
        } else if (!result.getResultCode().equals("F-1")) {
            return result;
        }

        /* 이메일 변경 */
        result = canModifyEmail(target, input);
        if (result.isSuccess()) {
            target.setEmail(input.getEmail());
        } else if (!result.getResultCode().equals("F-1")) {
            return result;
        }

        return RsData.of("S-1", "회원정보가 수정되었습니다.", memberRepository.save(target));
    }

    private RsData<Member> canModifyPhoto(Member target, ModifyForm input) {
        if (input.getFile() == null) {
            return RsData.of("F-1", "변경 사항이 없습니다.");
        }
        return RsData.of("S-1", "프로필 사진을 변경할 수 있습니다.");
    }

    private RsData<Member> canModifyEmail(Member target, ModifyForm input) {
        if (input.getEmail().isBlank()) {
            return RsData.of("F-2", "이메일이 반드시 입력되어야 합니다.");
        }
        if (input.getEmail().equals(target.getEmail())) {
            return RsData.of("F-1", "변경 사항이 없습니다.");
        }
        if (memberRepository.findByEmail(input.getEmail()).isPresent()) {
            return RsData.of("F-3", "이미 존재하는 이메일입니다.");
        }
        return RsData.of("S-1", "이메일을 변경할 수 있습니다.");
    }

    private RsData<Member> canModifyPassword(ModifyForm input) {
        if (input.getPassword().isBlank()) {
            return RsData.of("F-1", "변경 사항이 없습니다.");
        }
        if (!input.getPassword().equals(input.getPasswordValidation())) {
            return RsData.of("F-2", "동일한 비밀번호가 입력되어야 합니다.");
        }
        return RsData.of("S-1", "비밀번호를 변경할 수 있습니다.");
    }

    private void modifyLoginSetSession(Member member, String password, HttpServletRequest request) {
        // 사용자 인증
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                member.getUsername(),
                password
        );
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        } catch (DisabledException | LockedException | BadCredentialsException e) {
            String status;
            if (e.getClass().equals(BadCredentialsException.class)) {
                status = "invalid-password";
            } else if (e.getClass().equals(DisabledException.class)) {
                status = "locked";
            } else if (e.getClass().equals(LockedException.class)) {
                status = "disable";
            } else {
                status = "unknown";
            }
            System.out.println("catch" + status);
        }
    }

    public ResponseEntity<String> withdraw(Long id, String password, HttpServletRequest request) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            if (passwordEncoder.matches(password, member.getPassword())) {
                member.setUsername(null);
                member.setEmail(null);
                member.setPhoto(null);
                member.setDeleted(true);

                memberRepository.save(member);
                logoutAndInvalidateSession(request);
                return new ResponseEntity<>("Account has been successfully deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Account does not exist", HttpStatus.NOT_FOUND);
        }
    }

    private void logoutAndInvalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();
    }

    public String findUsername(String email) {
        Optional<Member> sameEmailAccount = memberRepository.findByEmail(email);
        if (sameEmailAccount.isEmpty()) {
            return "존재하지 않는 회원입니다.";
        }

        sendUsernameEmail(sameEmailAccount.get());

        return "입력된 이메일로 아이디를 발송했습니다.";
    }

    private void sendUsernameEmail(Member target) {
        Context context = new Context();
        context.setVariable("username", target.getUsername());
        context.setVariable("email", target.getEmail());
        String message = templateEngine.process("email/find_username", context);
        EmailDto emailDto = EmailDto.builder()
                .to(target.getEmail())
                .subject("아이디 안내")
                .message(message)
                .build();

        mailSenderRunner.sendMessage(emailDto);
    }

    public String findPassword(String email, String username) {
        Optional<Member> sameEmailAccount = memberRepository.findByEmail(email);
        Optional<Member> sameUsernameAccount = memberRepository.findByUsername(username);

        if (sameEmailAccount.isEmpty() || sameUsernameAccount.isEmpty()) {
            return "존재하지 않는 회원입니다.";
        }
        if (!sameEmailAccount.get().getId().equals(sameUsernameAccount.get().getId())) {
            return "일치하는 계정이 존재하지 않습니다.";
        }

        String temporarPassword = createRandomPassword();
        sameEmailAccount.get().setPassword(temporarPassword);
        memberRepository.save(sameEmailAccount.get());

        mailSenderRunner.sendMessage(email, "비밀번호 안내",
                sameEmailAccount.get().getUsername() + "님의 티켓킹 임시 비밀번호는\n"
                        + temporarPassword + "\n입니다."
                        + "\n로그인 후 비밀번호를 변경해주세요."
        );

        return "입력된 이메일로 임시 비밀번호를 발송했습니다.";
    }

    private void sendPasswordEmail(Member target, String temporarPassword) {
        Context context = new Context();
        context.setVariable("username", target.getUsername());
        context.setVariable("password", temporarPassword);
        String message = templateEngine.process("email/find_password", context);
        EmailDto emailDto = EmailDto.builder()
                .to(target.getEmail())
                .subject("비밀번호 안내")
                .message(message)
                .build();

        mailSenderRunner.sendMessage(emailDto);
    }

    private String createRandomPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}