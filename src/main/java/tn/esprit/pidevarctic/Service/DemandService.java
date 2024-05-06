package tn.esprit.pidevarctic.Service;

//import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.DemandRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class DemandService implements IDemandService {

   int numofunseendemands ;
    int numofseendemands;
    int numT ;

    private final DemandRepository demandRepository;
    private final UserRepository userRepository;


    @Override
    public Demand addDemand(Demand demand)  {
        numT++;
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
        if (d.isPresent()) {
            demandRepository.deleteById(demandId);
        } else {
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
    public List<Demand> getAllDemands() {

        return (List<Demand>) demandRepository.findAll();
    }


    @Override
    public List<Demand> getAllSeenDemands() {
        Boolean status = true;
        List<Demand> SeenDemands = demandRepository.findDemandByStatus(status);

        return SeenDemands;
    }

    @Override
    public List<Demand> getAllUnseenDemands() {
        Boolean status = false;
        List<Demand> UnSeenDemands = demandRepository.findDemandByStatus(status);
        return UnSeenDemands;
    }





    public int numberofDemands()
    {
        List<Demand> demands = demandRepository.findAll();
        int numT = demands.size();
        return numT ;
    }

    @Override
    public int numberofSeenDemands() {
        int numberofseendemands = (int)demandRepository.findAll().stream()
                .filter(Demand -> Demand.isStatus() == true)
                .count();
        return numberofseendemands ;
    }

    @Override
    public int numberofUnseenDemands() {
        int numofunseendemands = (int)demandRepository.findAll().stream()
                .filter(Demand -> Demand.isStatus() == false)
                .count();
        return numofunseendemands ;
    }
    @Override
    public Demand SetAsSeen(long demandId) {
        Demand dem =demandRepository.findById(demandId).orElse(null);

        dem.setStatus(true);
        return demandRepository.save(dem);
    }

    @Override
    public Demand SetAsUnSeen(long demandId) {
        Demand dem =demandRepository.findById(demandId).orElse(null);

        dem.setStatus(false);
        return demandRepository.save(dem);
    }
    @Override
    public long countdailydemands() {
        LocalDateTime currentDay = LocalDateTime.now();
        long todaysDemands = getAllDemands().stream()
                .filter(demand -> demand.getCreationDate().equals(currentDay))
                .count();
        return todaysDemands;
    }

    public void countMonthlyDemands() {
        YearMonth currentMonth = YearMonth.now();
        long monthlyDemands = getAllDemands().stream()
                .filter(demand -> {
                    YearMonth demandMonth = YearMonth.from(demand.getCreationDate());
                    return demandMonth.equals(currentMonth);
                })
                .count();
        System.out.println("There are " + monthlyDemands + " demands added this month!");
    }

    public void countDemandsByYear() {
        Year currentYear = Year.now();
        long DemandsByYear = getAllDemands().stream()
                .filter(demand -> {
                    Year demandYear = Year.from(demand.getCreationDate());
                    return demandYear.equals(currentYear);
                })
                .count();
        System.out.println("There are " + DemandsByYear + " demands added this Year!");
    }

    public void countDemandsByWeek() {
        LocalDateTime currentDate = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
        long demandsByWeek = getAllDemands().stream()
                .filter(demand -> {
                    int demandWeek = demand.getCreationDate().get(weekFields.weekOfWeekBasedYear());
                    return demandWeek == currentWeek;
                })
                .count();
        System.out.println("There are " + demandsByWeek + " demands added this week!");
    }




    @Override
    public List<Demand> getDemandByCreationDate(LocalDateTime creationDate) {
        return demandRepository.findByCreationDate(creationDate);
    }
    }


