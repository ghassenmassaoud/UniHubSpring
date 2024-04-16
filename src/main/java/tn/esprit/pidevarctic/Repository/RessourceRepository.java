package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Ressource;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {
}