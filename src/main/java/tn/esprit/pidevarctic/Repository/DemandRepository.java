package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;

import java.util.List;

public interface DemandRepository extends JpaRepository<Demand, Long> {
    public List<Demand> getDemandByDemandType(DemandType demandType);
}