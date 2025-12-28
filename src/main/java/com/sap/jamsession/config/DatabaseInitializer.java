package com.sap.jamsession.config;

import com.sap.jamsession.model.User;
import com.sap.jamsession.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@admin.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setName("Administrator");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("Password123!"));
            admin.setRole("ROLE_ADMIN");
            admin.setBio("Główny administrator systemu.");
            admin.setFavoriteGenres(new HashSet<>());
            admin.setInstrumentsAndRatings(new HashSet<>());
            admin.setOwnedJamSessions(new HashSet<>());

            userRepository.save(admin);
            System.out.println(">>> Stworzono domyślne konto administratora: admin@admin.com");
        } else {
            System.out.println(">>> Konto administratora już istnieje.");
        }
    }
}