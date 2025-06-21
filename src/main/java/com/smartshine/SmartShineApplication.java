package com.smartshine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SmartShineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartShineApplication.class, args);
    }

   @Bean
public CommandLineRunner initAdmin(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Создан пользователь admin:admin с ролью ADMIN");
        }
    };
}
