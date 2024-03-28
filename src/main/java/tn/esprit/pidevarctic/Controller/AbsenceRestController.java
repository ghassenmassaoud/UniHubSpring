package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.AbsenceService;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.TaskService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Task;
import tn.esprit.pidevarctic.entities.User;

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
        Classroom classroom1 = classroomService.getClassroomById(classroom);
        absence.setClassroom(classroom1);
        User student1 = userService.getUserById(student);
        absence.setUser(student1);
        return absenceService.addAbsence(absence);
    }
//    @PutMapping("/update/{absenceId}")
//    public Absence updateAbsence(@RequestBody Absence absence,  @PathVariable Long absenceId){
//
//        absence.setIdAbsence(absenceId);
//        return absenceService.updateAbsence(absence);
//    }
@PutMapping("/update/{absenceId}")
public ResponseEntity<Absence> updateAbsence(@RequestBody Absence updatedAbsence, @PathVariable Long absenceId) {
    Absence existingAbsence = absenceService.getAbsenceById(absenceId);
    if (existingAbsence != null) {
        // Mettre à jour les champs de l'absence existante avec les valeurs de la nouvelle absence
        existingAbsence.setStatusAbsence(updatedAbsence.getStatusAbsence());
        existingAbsence.setDateAbsence(updatedAbsence.getDateAbsence());

        if (updatedAbsence.getClassroom() != null) {

            Long classroomId = updatedAbsence.getClassroom().getIdClassroom();
            Classroom classroom = classroomService.getClassroomById(classroomId);
            if (classroom != null) {
                existingAbsence.setClassroom(classroom);
            } else {
                // Gérer le cas où la salle de classe n'existe pas
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        if (updatedAbsence.getUser() != null) {
            // Récupérer l'utilisateur à partir de la base de données
            Long userId = updatedAbsence.getUser().getIdUser();
            User user = userService.getUserById(userId);
            if (user != null) {
                existingAbsence.setUser(user);
            } else {
                // Gérer le cas où l'utilisateur n'existe pas
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }

        // Sauvegarder l'absence mise à jour dans la base de données
        Absence updatedAbsenceEntity = absenceService.updateAbsence(existingAbsence);
        return ResponseEntity.ok(updatedAbsenceEntity);
    } else {
        // Gérer le cas où l'absence avec l'ID donné n'existe pas
        return ResponseEntity.notFound().build();
    }
}

    @GetMapping("/get/{numAbsence}")
    public Absence getAbsence(@PathVariable Long numAbsence) {
        return absenceService.getAbsenceById(numAbsence);
    }

    @DeleteMapping("/delete/{numAbsence}")
    public void removeAbsence(@PathVariable Long numAbsence) {
        absenceService.deleteAbsence(numAbsence);
    }

    @GetMapping("/all")
    public List<Absence> getAll() {
        return absenceService.getAllAbsence();
    }


}
