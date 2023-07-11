package com.example.ticketKing.global.error;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/404")
    public String notFound(HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "/usr/error/404";
    }

    @GetMapping("/403")
    public String forbidden(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return "/usr/error/403";
    }

}