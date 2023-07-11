package com.example.ticketKing.domain.Member.dto;

import com.example.ticketKing.domain.Member.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("created_at")
    private LocalDateTime createDate;

    @JsonProperty("updated_at")
    private LocalDateTime modifyDate;

    @NotBlank
    @Size(min = 4, max = 16)
    @JsonProperty("username")
    private String username;

    @NotBlank
    @Size(min = 4, max = 16)
    private String password;

    @NotBlank
    @Size(min = 4, max = 16)
    private String passwordValidation;

    @NotBlank
    private String email;

}