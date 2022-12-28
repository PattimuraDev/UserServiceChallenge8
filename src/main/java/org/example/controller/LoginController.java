package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.configuration.JwtUtils;
import org.example.model.ApplicationUser;
import org.example.model.ApplicationUserDetailsImpl;
import org.example.model.Role;
import org.example.model.dto.ApplicationUserDto;
import org.example.model.dto.SignInRequestDto;
import org.example.model.enumeration.ERole;
import org.example.model.response.CustomJwtResponse;
import org.example.model.response.CustomMessageResponse;
import org.example.repository.ApplicationUserRepository;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Kelas controller untuk menghandle endpoint terkait login dan signup
 * @author Dwi Satria Patra
 */
@Tag(name = "AUTHENTICATION")
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    public LoginController() {

    }

    public LoginController(AuthenticationManager authenticationManager, ApplicationUserRepository inputApplicationUserRepository,
                           JwtUtils jwtUtils, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.applicationUserRepository = inputApplicationUserRepository;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method controller untuk keperluan sign in
     *
     * @param signInRequestDto parameter untuk object signInRequestDto yang berisi data yang akan digunakan untuk sign in
     * @return response entity yang berisi berbagai informasi yang berkaitan dengan user dan token hasil generate
     */
    @SecurityRequirements
    @Operation(summary = "Endpoint API untuk melakukan login/sign in")
    @PostMapping("/signin")
    public ResponseEntity<CustomJwtResponse> authenticateUser(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        ApplicationUserDetailsImpl userDetails = (ApplicationUserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CustomJwtResponse(jwt,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
                roles));
    }


    /**
     * Method controller untuk keperluan signup
     *
     * @param applicationUserDto parameter untuk applicationUseraDto yang berisi informasi user yang akan signup
     * @return response entitiy hasil response endpoint API
     */
    @SecurityRequirements
    @Operation(summary = "Endpoint API untuk melakukan signup")
    @PostMapping("/signup")
    public ResponseEntity<CustomMessageResponse> registerUser(@Valid @RequestBody ApplicationUserDto applicationUserDto) {
        Boolean usernameIsExist = applicationUserRepository.existsByUsername(applicationUserDto.getUsername());
        if (Boolean.TRUE.equals(usernameIsExist)) {
            return ResponseEntity.badRequest()
                    .body(new CustomMessageResponse("Error: Username sudah ada!"));
        }

        Boolean emailIsExist = applicationUserRepository.existsByEmail(applicationUserDto.getEmail());
        if (Boolean.TRUE.equals(emailIsExist)) {
            return ResponseEntity.badRequest()
                    .body(new CustomMessageResponse("Error: Email sudah ada!"));
        }

        ApplicationUser applicationUser = new ApplicationUser(null, applicationUserDto.getUsername(), applicationUserDto.getEmail(),
                passwordEncoder.encode(applicationUserDto.getPassword()));

        Set<String> strRoles = applicationUserDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role role = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan"));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                Role roles1 = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " tidak ditemukan"));
                roles.add(roles1);
            });
        }
        applicationUser.setRoles(roles);
        applicationUserRepository.save(applicationUser);
        return ResponseEntity.ok(new CustomMessageResponse("Registrasi user berhasil"));
    }
}
