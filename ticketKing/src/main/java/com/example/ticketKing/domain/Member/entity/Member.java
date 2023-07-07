package com.example.ticketKing.domain.Member.entity;


import com.example.ticketKing.domain.Practice.entity.Practice;
import com.example.ticketKing.domain.photo.entity.Photo;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import static jakarta.persistence.FetchType.LAZY;



@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
public class Member extends BaseEntity {

    @Size(min = 4, max = 16)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @OneToOne(orphanRemoval = true)
    private Photo photo;

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<Practice> practices;


}
