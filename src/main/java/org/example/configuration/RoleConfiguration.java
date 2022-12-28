package org.example.configuration;

import org.example.model.Role;
import org.example.model.enumeration.ERole;
import org.example.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(RoleConfiguration.class);

    RoleConfiguration(RoleRepository roleRepository) {
        LOG.info("Mengecek role tersedia");
        for (ERole c : ERole.values()) {
            try {
                Role roles = roleRepository.findByName(c)
                        .orElseThrow(() -> new RuntimeException("Roles tidak ada"));
                LOG.info("Role {} tersedia", roles.getName());
            } catch (RuntimeException rte) {
                LOG.info("Role {} tidak ditemukan, menambahkan role ke database ...", c.name());
                Role roles = new Role();
                roles.setName(c);
                roleRepository.save(roles);
            }
        }
    }
}
