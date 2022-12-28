package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Kelas pemodelan DTO (data transfer object) dari objek data sign in
 * @author Dwi Satria Patra
 */
@Data
public class SignInRequestDto {
    @Schema(example = "username anda")
    @NotBlank
    private String username;
    @Schema(example = "password anda")
    @NotBlank
    private String password;
}
