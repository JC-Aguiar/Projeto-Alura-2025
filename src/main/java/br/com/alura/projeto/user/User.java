package br.com.alura.projeto.user;

import br.com.alura.projeto.registration.domain.Enrollment;
import br.com.alura.projeto.util.EncryptUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String name;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    @JsonManagedReference("user-enrollment")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Enrollment> enrollments = new ArrayList<>();


    @Deprecated
    public User() {}

    public User(String name, String email, Role role, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = EncryptUtil.toMD5(password);
    }
}
