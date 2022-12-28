package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enumeration.ERole;
import javax.persistence.*;

/**
 * Kelas untuk memodelkan table/entity role
 * @author Dwi Satria Patra
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Schema(example = "CUSTOMER")
    private ERole name;
}
