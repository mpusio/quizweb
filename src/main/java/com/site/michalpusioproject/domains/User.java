package com.site.michalpusioproject.domains;

import com.site.michalpusioproject.domains.validators.ValidIsExistEmail;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[A-Z]{1}[a-z]+", message="You should started by capital letter, without white signs, numbers e.g.: Michael, Fiona")
    private String firstName;

    @Pattern(regexp = "^[A-Z]{1}[a-z]+", message="You should started by capital letter, without white signs, numbers e.g.: Obama, Smith")
    private String lastName;

    @NonNull
    @Email
    @Column(unique=true, nullable=false)
    private String email;

    @Lob
    @NonNull
    @Size(min = 5, message = "Your password must have at least 5 characters")
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

}
