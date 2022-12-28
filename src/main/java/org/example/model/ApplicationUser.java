package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Kelas untuk memodelkan table/entity user
 * @author Dwi Satria Patra
 */
@Data
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;
    @Column(name = "username")
    @Schema(example = "user21")
    @NotBlank
    private String username;
    @Column(name = "email")
    @Schema(example = "user21@gmail.com")
    @NotBlank
    @Email
    private String email;
    @Column(name = "password")
    @Schema(example = "123abc")
    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userxrole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public ApplicationUser(Long id, String username, String emailAddress, String password) {
        this.id = id;
        this.username = username;
        this.email = emailAddress;
        this.password = password;
    }

    public ApplicationUser() {

    }
}
