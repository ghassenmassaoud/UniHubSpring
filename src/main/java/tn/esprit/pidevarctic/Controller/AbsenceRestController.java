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
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
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
    @GetMapping("/search/status/{status}")
    public ResponseEntity<?> searchByStatus(@PathVariable StatusAbsence status) {
        List<Absence> absences = absenceService.searchByStatus(status);
        if (absences.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No absences found for status: " + status);
        }
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/search/date/{date}")
    public ResponseEntity<?> searchByDate(@PathVariable LocalDate date) {
        List<Absence> absences = absenceService.searchByDate(date);
        if (absences.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No absences found for date: " + date);
        }
        return ResponseEntity.ok(absences);
    }
@GetMapping("/getStudentBySpeciality/{speciality}")
    public List<User>getStudentBySpeciality(@PathVariable Speciality speciality){
        return  absenceService.getStudentBySpeciality(speciality);
}


}
