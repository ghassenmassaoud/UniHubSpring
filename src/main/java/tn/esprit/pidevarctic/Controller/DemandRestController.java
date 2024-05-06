package tn.esprit.pidevarctic.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.EmailService;
import tn.esprit.pidevarctic.Service.IUserService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;
import tn.esprit.pidevarctic.Service.IDemandService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demands")
public class DemandRestController {

    private final IDemandService idemandService;




    private final EmailService emailService;

    @PostMapping()
    public Demand addDemand(@RequestBody Demand demand) {
        emailService.sendEmail("abdouunaffeti647@gmail.com", "demand", demand.getDescription());
        return idemandService.addDemand(demand);
    }



    @PostMapping("/send")
    public void send(@RequestBody String demand) {
        emailService.sendEmail("abdouunaffeti647@gmail.com", "demand", demand);

    }

    @PutMapping("/{id}")
    public Demand updateDemand(@PathVariable Long id, @RequestBody Demand demand) {
        demand.setDemandId(id);
        return idemandService.updateDemand(demand);
    }

    @DeleteMapping("/{id}")
    public void deleteDemand(@PathVariable Long id) {
        idemandService.deleteDemand(id);
    }

    @GetMapping("/{id}")
    public Demand getDemandById(@PathVariable Long id) {
        return idemandService.getDemandById(id);
    }

    @GetMapping("/type/{demandType}")
    public Set<Demand> getDemandByType(@PathVariable DemandType demandType) {
        return idemandService.getDemandByType(demandType);
    }

    @GetMapping("/All")
    public List<Demand> getAllDemands() {
        return idemandService.getAllDemands().stream().toList();
    }


    @GetMapping("/seen")
    public List<Demand> getAllSeenDemands() {
        return idemandService.getAllSeenDemands();
    }


    @GetMapping("/unseen")
    public List<Demand> getAllUnseenDemands() {
        return idemandService.getAllUnseenDemands();
    }


    @GetMapping("/count")
    public int getNumberOfDemands() {
        return idemandService.numberofDemands();
    }



    @GetMapping("/count/seen")
    public int getNumberOfSeenDemands() {
        return idemandService.numberofSeenDemands();
    }


    @GetMapping("/count/unseen")
    public int getNumberOfUnseenDemands() {
        return idemandService.numberofUnseenDemands();
    }


    @PostMapping("/{id}/seen")
    public Demand setDemandAsSeen(@PathVariable Long id) {
        return idemandService.SetAsSeen(id);
    }


    @PostMapping("/{id}/unseen")
    public Demand setDemandAsUnseen(@PathVariable Long id) {
        return idemandService.SetAsUnSeen(id);
    }


   // -----------------------Daily--------------------------------

    @GetMapping("/count/daily")
    public long countDailyDemands() {
        LocalDateTime currentDay = LocalDateTime.now();
        long todaysDemands = getAllDemands().stream()
                .filter(demand -> demand.getCreationDate().toLocalDate().equals(currentDay.toLocalDate()))
                .count();
        return todaysDemands;
    }



    //-----------------------Monthly--------------------------------



    @GetMapping("/count/monthly")
    public ResponseEntity<Long> countMonthlyDemands() {
        YearMonth currentMonth = YearMonth.now();
        long monthlyDemands = idemandService.getAllDemands().stream()
                .filter(demand -> {
                    YearMonth demandMonth = YearMonth.from(demand.getCreationDate());
                    return demandMonth.equals(currentMonth);
                })
                .count();
        return ResponseEntity.ok(monthlyDemands);
    }


    //-----------------------ByYear--------------------------------

    @GetMapping("/count/byYear")
    public ResponseEntity<Long> countDemandsByYear() {
        Year currentYear = Year.now();
        long demandsByYear = idemandService.getAllDemands().stream()
                .filter(demand -> {
                    Year demandYear = Year.from(demand.getCreationDate());
                    return demandYear.equals(currentYear);
                })
                .count();
        return ResponseEntity.ok(demandsByYear);
    }



    //-----------------------WEEKLY--------------------------------


    @GetMapping("/count/weekly")
    public ResponseEntity<Long> countWeeklyDemands() {
        LocalDateTime currentDate = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
        long weeklyDemands = idemandService.getAllDemands().stream()
                .filter(demand -> {
                    int demandWeek = demand.getCreationDate().get(weekFields.weekOfWeekBasedYear());
                    return demandWeek == currentWeek;
                })
                .count();
        return ResponseEntity.ok(weeklyDemands);
    }

    @GetMapping("/creation-date/{creationDate}")
    public List<Demand> getDemandsByCreationDate(@PathVariable LocalDateTime creationDate) {
        // Parse the creationDate string to LocalDate if necessary
        // LocalDate parsedDate = LocalDate.parse(creationDate);
        // return demandService.getDemandByCreationDate(parsedDate);
        return idemandService.getDemandByCreationDate(creationDate);
    }
}
