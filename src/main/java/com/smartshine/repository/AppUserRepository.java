package com.smartshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.smartshine.model.AppUser;
import com.smartshine.model.Client;
import com.smartshine.model.Role;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
    List<AppUser> findByRole(Role role);
}
