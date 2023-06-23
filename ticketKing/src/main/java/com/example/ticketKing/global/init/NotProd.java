package com.example.ticketKing.global.init;

import com.example.ticketKing.domain.Member.controller.MemberController;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile({"local", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(
            MemberService memberService
    ) {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {
            Member memberUser1 = memberService.join("user1","1234","user1@gmail.com").getData();
            Member memberUser2 = memberService.join("user2","1234","user2@gmail.com").getData();
            Member memberUser3 = memberService.join("user3","1234","user3@gmail.com").getData();
            Member memberUser4 = memberService.join("user4","1234","user4@gmail.com").getData();

            }
        };
    }
}
