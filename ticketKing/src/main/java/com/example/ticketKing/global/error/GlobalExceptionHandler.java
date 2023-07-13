package com.example.ticketKing.global.error;

import com.example.ticketKing.global.exception.DuplicateUsernameException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ...

    @ExceptionHandler(DuplicateUsernameException.class)
    public String handleDuplicateUsernameException(@ModelAttribute DuplicateUsernameException ex, HttpServletRequest request) {
        String errorMessage = ex.getMessage();
        return "redirect:/usr/member/join"; // 회원 가입 페이지로 리다이렉트
    }

    // ...
}