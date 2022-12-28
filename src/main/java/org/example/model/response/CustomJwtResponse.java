package org.example.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * Kelas untuk memodelkan objek custom dari hasil jwt response
 * @author Dwi Satria Patra
 */
@Data
public class CustomJwtResponse {
    @Schema(example = "eyJ.....")
    private String token;
    @Schema(example = "Bearer")
    private String type = "Bearer";
    @Schema(example = "1")
    private Long id;
    @Schema(example = "user21")
    private String username;
    @Schema(example = "user21@gmail.com")
    private String email;
    private List<String> roles;

    public CustomJwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
