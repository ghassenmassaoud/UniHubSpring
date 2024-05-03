package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}