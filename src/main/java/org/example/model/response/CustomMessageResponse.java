package org.example.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Kelas untuk memodelkan objek message custom dari hasil response
 * endpoint API tertentu (berisi message berhasil/gagal)
 * @author Dwi Satria Patra
 */
@AllArgsConstructor
@Data
public class CustomMessageResponse {
    @Schema(example = "Operasi berhasil")
    private String message;
}
