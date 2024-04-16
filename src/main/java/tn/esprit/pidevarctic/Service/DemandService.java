package tn.esprit.pidevarctic.Service;

//import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.DemandRepository;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
//@AllArgsConstructor

public class DemandService implements IDemandService{

    @Autowired
    private DemandRepository demandRepository;
    @Override
    public Demand addDemand(Demand demand) {
        return demandRepository.save(demand);
    }

    @Override
    public Demand updateDemand(Demand demand) {
        return demandRepository.save(demand);
    }

    @Override
    public void deleteDemand(Long demandId) {
       Optional<Demand> d = demandRepository.findById(demandId);
        //Verify if the demand exist or not
        if (d.isPresent())
        {
        demandRepository.deleteById(demandId);
        }
        else {
            throw new IllegalArgumentException("Demande inexistante");

        }

    }

    @Override
    public Demand getDemandById(Long demandId) {
        return demandRepository.findById(demandId).orElse(null);
    }

    @Override
    public Set<Demand> getDemandByType(DemandType demandType) {
        return demandRepository.getDemandByDemandType(demandType);
    }

    @Override
    public Set<Demand> getAllDemands() {
        return (Set<Demand>) demandRepository.findAll();
    }
}
