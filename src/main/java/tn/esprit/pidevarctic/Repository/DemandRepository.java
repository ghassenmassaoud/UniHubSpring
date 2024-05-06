package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface DemandRepository extends JpaRepository<Demand, Long> {
    public Set<Demand> getDemandByDemandType(DemandType demandType);
    public List<Demand> findDemandByStatus(boolean Status);

    public List<Demand> findByCreationDate(LocalDateTime CreationDate);



}