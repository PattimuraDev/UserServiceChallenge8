package org.example.service;

import org.example.model.ApplicationUser;
import org.example.model.ApplicationUserDetailsImpl;
import org.example.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Kelas service untuk menghandle semua permintaan ke repository user
 * @author Dwi Satria Patra
 */
@Service
public class ApplicationUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    ApplicationUserRepository usersRepository;

    /**
     * Method yang digunakan untuk membangun user detail dari objek user
     * @param username parameter untuk username dari user
     * @return Objek UserDetails hasil build
     * @throws UsernameNotFoundException exception apabila user tidak ditemukan
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = usersRepository.findByUsername(username).orElseGet(null);
        return ApplicationUserDetailsImpl.build(user);
    }

    /**
     * Method untuk mengapus user berdasarkan id-nya
     * @param idUser parameter untuk id dari user
     */
    public void deleteUser(Long idUser){
        usersRepository.deleteById(idUser);
    }

    /**
     * Method yang digunakan untuk mengupdate data dari user
     * @param idUser parameter untuk id user yang ingin diupdate datanya
     * @param applicationUser parameter untuk objek data user yang baru
     * @return data user yang sudah diupdate
     */
    public ApplicationUser updateUser(Long idUser, ApplicationUser applicationUser){
        Optional<ApplicationUser> userOptional = usersRepository.findById(idUser);
        if(userOptional.isPresent()){
            final ApplicationUser result = userOptional.get();
            result.setEmail(applicationUser.getEmail());
            result.setPassword(applicationUser.getPassword());
            result.setUsername(applicationUser.getUsername());
            return usersRepository.save(result);
        }else{
            return null;
        }
    }
}
