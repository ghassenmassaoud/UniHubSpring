package tn.esprit.pidevarctic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.EmailService;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;
import tn.esprit.pidevarctic.Service.IDemandService;


import java.util.Set;

@RestController
@RequestMapping("/api/demands")
public class DemandRestController {
    @Autowired
    private IDemandService demandService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public Demand addDemand(@RequestBody Demand demand) {
        emailService.sendEmail("abdouunaffeti647@gmail.com", "demand", demand.getDescription());
        return demandService.addDemand(demand);
    }

    @PutMapping("/{id}")
    public Demand updateDemand(@PathVariable Long id, @RequestBody Demand demand) {
        demand.setDemandId(id);
        return demandService.updateDemand(demand);
    }

    @DeleteMapping("/{id}")
    public void deleteDemand(@PathVariable Long id) {
        demandService.deleteDemand(id);
    }

    @GetMapping("/{id}")
    public Demand getDemandById(@PathVariable Long id) {
        return demandService.getDemandById(id);
    }

    @GetMapping("/type/{demandType}")
    public Set<Demand> getDemandByType(@PathVariable DemandType demandType) {
        return demandService.getDemandByType(demandType);
    }

    @GetMapping
    public Set<Demand> getAllDemands() {
        return demandService.getAllDemands();
    }
}
