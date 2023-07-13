package com.example.ticketKing.domain.Member.dto;

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
public class JoinFormDto {
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
