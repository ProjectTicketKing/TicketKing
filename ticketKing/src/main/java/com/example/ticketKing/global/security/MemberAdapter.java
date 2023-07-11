package com.example.ticketKing.global.security;

import com.example.ticketKing.domain.Member.entity.Member;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class MemberAdapter extends User {
    private Long id;

    public MemberAdapter(Member member) {
        super(member.getUsername(), member.getPassword(), member.getGrantedAuthorities());

        this.id = member.getId();
    }
}