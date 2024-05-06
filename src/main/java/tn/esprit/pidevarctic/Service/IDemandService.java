package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IDemandService {
     Demand addDemand(Demand demand);
     Demand updateDemand(Demand demand);
     void deleteDemand(Long demandId);
     Demand getDemandById(Long demandId);
    Set<Demand> getDemandByType(DemandType demandType);
    List<Demand> getAllDemands();
     List<Demand> getAllSeenDemands();

     List<Demand> getAllUnseenDemands();


    public int numberofDemands();

    public int numberofSeenDemands();
    public int numberofUnseenDemands();

    public Demand SetAsSeen(long demandId);
    public Demand SetAsUnSeen(long demandId);
    public long countdailydemands();

    public List<Demand> getDemandByCreationDate(LocalDateTime CreationDate);

    public void countMonthlyDemands() ;

    public void countDemandsByYear();

    public void countDemandsByWeek();
}
