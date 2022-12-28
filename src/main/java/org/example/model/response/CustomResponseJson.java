package org.example.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Kelas untuk memodelkan objek custom dari hasil response
 * endpoint API tertentu
 * @author Dwi Satria Patra
 */
@Data
@AllArgsConstructor
public class CustomResponseJson {
    @Schema(example = "Operasi berhasil")
    private String message;
    @Schema(example = "200")
    private String statusCode;
}

