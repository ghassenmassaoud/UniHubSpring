package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.AbsenceRepository;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.RoleRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.StatusAbsence;
import tn.esprit.pidevarctic.entities.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AbsenceService implements IAbsenceService {
    private AbsenceRepository absenceRepository;
    private ClassroomRepository classroomRepository;
    private UserRepository userRepository;

    @Override
    public Absence addAbsence(Absence absence , Long classroom,Long student) {
        Classroom classroom1= classroomRepository.findById(classroom).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        User student1 =userRepository.findById(student).orElseThrow(()-> new IllegalArgumentException("Student not found"));
        absence.setUser(student1);
        absence.setClassroom(classroom1);
        classroom1.getAbsences().add(absence);

        return absenceRepository.save(absence);
    }

    @Override
    public Absence updateAbsence(Absence absence, Long absenceId) {
    Absence existingAbsence = absenceRepository.findById(absenceId).orElse(null);
    if (existingAbsence != null) {
        // Mettre à jour les champs de l'absence existante avec les valeurs de la nouvelle absence
        existingAbsence.setStatusAbsence(absence.getStatusAbsence());
        existingAbsence.setDateAbsence(absence.getDateAbsence());

    }
    return absenceRepository.save(existingAbsence);
    }



    @Override
    public void deleteAbsence(Long idAbsence) {
        absenceRepository.deleteById(idAbsence);
    }

    @Override
    public Absence getAbsenceById(Long idAbsence) {
        return absenceRepository.findById(idAbsence).orElse(null);
    }

    @Override
    public List<Absence> getAllAbsence() {
        return absenceRepository.findAll();
    }

    @Override
    public List<Absence> SearchAbsence(StatusAbsence status, LocalDate date) {
        return absenceRepository.findAbsenceByStatusAbsenceOrDateAbsence(status, date);
    }

}
