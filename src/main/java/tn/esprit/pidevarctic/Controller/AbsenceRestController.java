package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.AbsenceService;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.TaskService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/absence")
@AllArgsConstructor
@RestController
public class AbsenceRestController {
    private AbsenceService absenceService;
    private ClassroomService classroomService;
    private UserService userService;

    @PostMapping("/add")
    public Absence addAbsence(@RequestBody Absence absence ,@RequestParam Long classroom ,@RequestParam Long student){
        return absenceService.addAbsence(absence,classroom,student);
    }
@PutMapping("/update/{absenceId}")
public Absence updateAbsence(@RequestBody Absence updatedAbsence, @PathVariable Long absenceId) {
        return  absenceService.updateAbsence(updatedAbsence,absenceId);

}

    @GetMapping("/get/{numAbsence}")
    public Absence getAbsence(@PathVariable Long numAbsence) {
        return absenceService.getAbsenceById(numAbsence);
    }

//    @DeleteMapping("/delete/{numAbsence}")
//    public void removeAbsence(@PathVariable Long numAbsence) {
//        absenceService.deleteAbsence(numAbsence);
//    }
    @DeleteMapping("/delete/{numAbsence}")
    public ResponseEntity<String> removeAbsence(@PathVariable Long numAbsence) {
        // Supprimer l'absence
        absenceService.deleteAbsence(numAbsence);

        // Retourner un message de suppression réussie
        String message = "Delete successful";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public List<Absence> getAll() {
        return absenceService.getAllAbsence();
    }
    @GetMapping("/search/{state}")
    public ResponseEntity<?> searchAbsence(@PathVariable StatusAbsence state, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate state) {
        List<Absence> absences = absenceService.SearchAbsence(name, name);
        if (!absences.isEmpty()) {
            return ResponseEntity.ok(absences);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Absence Not Found");
        }
    }



}
