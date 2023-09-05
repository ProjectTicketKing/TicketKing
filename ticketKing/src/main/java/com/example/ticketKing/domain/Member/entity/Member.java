package com.example.ticketKing.domain.Member.entity;


import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import com.example.ticketKing.domain.RealParticipant.entity.RealParticipant;
import com.example.ticketKing.domain.photo.entity.Photo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.FetchType.LAZY;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Member {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createDate;
    @Column(unique = true)
    @Size(min = 4, max = 16)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String email;
    @OneToOne(orphanRemoval = true)
    private Photo photo;

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<PracticeResult> practices;

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<RealParticipant> realParticipants;

    @Builder.Default
    private int authority = 1;
    @Builder.Default
    private int reported = 0;
    private LocalDateTime loginRejectedDeadline;
    @Builder.Default
    private boolean deleted = false;

    public boolean isAdmin() {
        return this.authority == 0;
    }

    public static class MemberBuilder {
        public MemberBuilder password(String password) {
            this.password = passwordEncoder.encode(password);
            return this;
        }
    }

    public String getUsername() {
        if (this.username == null) {
            return "알 수 없는 이용자";
        }
        return this.username;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public int reportUp() {
        return ++this.reported;
    }

    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("user"));

        if (this.authority == 0) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }

}