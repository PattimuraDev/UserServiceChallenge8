package org.example.repository;

import org.example.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface repository untuk menghandle semua permintaan ke table user di database
 * @author Dwi Satria Patra
 */
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    /**
     * Method repository untuk menemukan user dengan menggunakan identitas username
     * @param username parameter username dari user
     * @return objek user hasil pencarian
     */
    Optional<ApplicationUser> findByUsername(String username);

    /**
     * @Description Method repository untuk mengecek apakah seorang user benar-benar ada di database
     * berdasarkan username nya
     * @param username paremeter untuk username dari user
     * @return boolean true/false, true jika ada dan false jika tidak
     */
    Boolean existsByUsername(String username);

    /**
     * Method repository untuk mengecek apakah seorang user benar-benar ada di database
     * berdasarkan email nya
     * @param emailAddress parameter untuk email dari user
     * @return boolean true/false, true jika ada dan false jika tidak
     */
    Boolean existsByEmail(String emailAddress);
}

