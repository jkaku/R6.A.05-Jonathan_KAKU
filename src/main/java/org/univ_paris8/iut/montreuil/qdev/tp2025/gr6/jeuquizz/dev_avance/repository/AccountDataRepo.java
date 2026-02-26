package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import java.util.Optional;

public interface AccountDataRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}