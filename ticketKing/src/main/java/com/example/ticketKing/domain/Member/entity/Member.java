package com.example.ticketKing.domain.Member.entity;


import com.example.ticketKing.domain.Practice.entity.Practice;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;




@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
public class Member extends BaseEntity {

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY )
    private List<Practice> practices;


}
