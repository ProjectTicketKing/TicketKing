package com.example.ticketKing.domain.Member.controller;

import com.example.ticketKing.domain.Member.dto.MemberDto;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.finder.FindPasswordForm;
import com.example.ticketKing.domain.Member.finder.FindUsernameForm;
import com.example.ticketKing.domain.Member.finder.ModifyForm;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.global.rq.Rq;
import com.example.ticketKing.global.rsData.RsData;
import com.example.ticketKing.global.security.MemberAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/usr/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login") // 로그인 폼, 로그인 폼 처리는 스프링 시큐리티가 구현, 폼 처리시에 CustomUserDetailsService 가 사용됨
    public String showLogin() {
        return "usr/member/login";
    }

    @GetMapping("/home")
    public String showHome() {
        return "usr/main/home"; // Return the home page template
    }

    @PreAuthorize("isAuthenticated()") // 로그인 해야만 접속가능@GetMapping("/me") // 로그인 한 나의 정보 보여주는 페이지
    @GetMapping("/me")
    public String showMe(Principal principal, Model model) {
        Member entity = memberService.getMemberFromUsername(principal.getName());
        model.addAttribute("member", entity);
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
    public String join(@Valid @ModelAttribute MemberDto input, HttpServletRequest request) { // @Valid 가 없으면 @NotBlank 등이 작동하지 않음, 만약에 유효성 문제가 있다면 즉시 정지
        memberService.join(input);
        memberService.authenticateAccountAndSetSession(input, request);
        return "redirect:/usr/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/withdraw/{id}")
    @ResponseBody
    public ResponseEntity<String> withdraw(@PathVariable Long id, @RequestParam("password") String password,
                                           @AuthenticationPrincipal MemberAdapter memberAdapter, HttpServletRequest request) {
        if (!memberAdapter.getId().equals(id)) {
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }

        return memberService.withdraw(memberAdapter.getId(), password, request);
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

}