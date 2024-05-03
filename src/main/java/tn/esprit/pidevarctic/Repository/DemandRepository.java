package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Demand;

public interface DemandRepository extends JpaRepository<Demand, Long> {
}