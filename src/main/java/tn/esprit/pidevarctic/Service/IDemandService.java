package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;


import java.util.Set;

public interface IDemandService {
    public Demand addDemand(Demand demand);
    public Demand updateDemand(Demand demand);
    public void deleteDemand(Long demandId);
    public Demand getDemandById(Long demandId);
    Set<Demand> getDemandByType(DemandType demandType);
    Set<Demand> getAllDemands();

}
