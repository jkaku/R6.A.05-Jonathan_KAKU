package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import java.util.Optional;

public interface ItemDataRepo extends JpaRepository<Annonce, Long>, JpaSpecificationExecutor<Annonce> {

    @EntityGraph(attributePaths = {"author", "category"})
    Optional<Annonce> findWithRelationsById(Long id);
}