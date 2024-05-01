package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.pidevarctic.Repository.AbsenceRepository;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.RoleRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.*;

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
    public Absence addAbsence(Long classroomId, Long studentId, Absence absence ) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));

        User student = classroom.getStudents().stream()
                .filter(s -> s.getIdUser().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student not found in this classroom"));

       // Absence absence = new Absence();
        absence.setClassroom(classroom);
        student.getAbsences().add(absence);
       absenceRepository.save(absence);
        userRepository.save(student);
        //absence.setProfile(student.getProfiles().stream().findFirst().orElse(null)); // Assuming a student has one profile
//        absence.setDateAbsence(absence.setDateAbsence());
//        absence.setStatusAbsence(statusAbsence);
    //classroom.getAbsences().add(absence);
        return absence;
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
    public List<Absence> searchByStatus(StatusAbsence status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        return absenceRepository.findAbsenceByStatusAbsence(status);
    }
@Override
    public List<Absence> searchByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        return absenceRepository.findAbsenceByDateAbsence(date);
    }
public List<User> getStudentBySpeciality(Speciality speciality){
        return  userRepository.findBySpeciality(speciality);
}
    public List<Absence> findAbsenceByStudentId(Long studentId) {
        return absenceRepository.findAbsenceByStudentId(studentId);
    }
    public List<User> getAbsenceByClassroom(Long classroomId) {
        return absenceRepository.findStudentsWithAbsenceInClassroom(classroomId);
    }


}
