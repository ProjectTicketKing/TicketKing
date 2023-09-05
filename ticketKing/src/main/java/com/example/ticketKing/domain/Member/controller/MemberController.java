package com.example.ticketKing.domain.Member.controller;

import com.example.ticketKing.domain.Member.dto.JoinFormDto;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.finder.FindPasswordForm;
import com.example.ticketKing.domain.Member.finder.FindUsernameForm;
import com.example.ticketKing.domain.Member.finder.ModifyForm;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import com.example.ticketKing.domain.PracticeResult.repository.PracticeResultRepository;
import com.example.ticketKing.domain.PracticeResult.service.PracticeResultService;
import com.example.ticketKing.global.exception.DuplicateUsernameException;
import com.example.ticketKing.global.rq.Rq;
import com.example.ticketKing.global.rsData.RsData;
import com.example.ticketKing.global.security.SecurityMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/usr/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    private final PracticeResultService practiceService;
    private final PracticeResultRepository practiceRepository;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login") // 로그인 폼, 로그인 폼 처리는 스프링 시큐리티가 구현, 폼 처리시에 CustomUserDetailsService 가 사용됨
    public String showLogin() {
        return "usr/member/login";
    }

    @GetMapping("/home")
    public String showHome() {
        return "/"; // Return the home page template
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me") // 로그인 한 나의 정보와 연습결과를 보여주는 페이지
    public String showMePage(Model model, @AuthenticationPrincipal SecurityMember securityMember) {
        Long memberId = securityMember.getId();
        Member member = memberService.getMemberFromUsername(securityMember.getUsername());
        List<PracticeResult> practices = practiceRepository.findByMemberId(memberId);

        model.addAttribute("member", member);
        model.addAttribute("practices", practices);

        return "usr/member/me";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/{id}")
    public String modifyMe(@PathVariable Long id, @Valid @ModelAttribute ModifyForm input,
                           Model model, HttpServletRequest request) throws IOException {
        RsData<Member> rsMember = memberService.modifyInfo(id, input, request);
        if (rsMember.isFail()) {
            return rq.historyBack(rsMember);
        }
        model.addAttribute("member", rsMember.getData());
        return rq.redirectWithMsg("/", rsMember);
    }

    @PreAuthorize("isAnonymous()") // 오직 로그인 안한 사람만 접근 가능하다.
    @GetMapping("/join") // 회원가입 폼
    public String showJoin() {
        return "usr/member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinFormDto input, HttpServletRequest request, Model model) { // @Valid 가 없으면 @NotBlank 등이 작동하지 않음, 만약에 유효성 문제가 있다면 즉시 정지
        try {
            memberService.join(input);
//            memberService.authenticateAccountAndSetSession(input, request);

            return "redirect:/usr/member/login"; // 회원 가입 성공 시 로그인 페이지로 이동
        } catch (DuplicateUsernameException e) {
            return rq.historyBack(e.getMessage());
        }
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/withdraw")
    @ResponseBody
    public ResponseEntity<String> withdraw(@RequestParam("password") String password,
                                           @AuthenticationPrincipal SecurityMember securityMember, HttpServletRequest request) {

        return memberService.withdraw(securityMember.getId(), password, request);
    }

    @GetMapping("/findMember")
    @PreAuthorize("isAnonymous()")
    public String find() {
        return "usr/member/find_member";
    }

    @PostMapping("/find/username")
    @ResponseBody
    public String findUsername(@Valid @ModelAttribute FindUsernameForm form) {
        return memberService.findUsername(form.getEmail());
    }

    @PostMapping("/find/password")
    @ResponseBody
    public String findPassword(@Valid @ModelAttribute FindPasswordForm form) {
        return memberService.findPassword(form.getEmail(), form.getUsername());
    }

    @GetMapping("/api/getUserName")
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // 사용자 이름 반환 (사용자 ID)
        } else {
            return null; // 인증되지 않은 경우
        }
    }


}