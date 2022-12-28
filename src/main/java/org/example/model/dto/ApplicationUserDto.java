package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Kelas pemodelan DTO (data transfer object) dari objek user
 * @author Dwi Satria Patra
 */
@Data
@AllArgsConstructor
public class ApplicationUserDto {
    @Schema(example = "user21")
    @NotBlank
    private String username;
    @Schema(example = "user21@gmail.com")
    @NotBlank
    @Email
    private String email;
    @Schema(example = "123abc")
    @NotBlank
    private String password;
    private Set<String> roles;
}
