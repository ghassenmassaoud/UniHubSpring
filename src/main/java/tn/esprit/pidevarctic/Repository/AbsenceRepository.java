package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.StatusAbsence;
import tn.esprit.pidevarctic.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    //@Query("SELECT a FROM Absence a WHERE a.statusAbsence = :status OR a.dateAbsence = :date")
    List<Absence> findAbsenceByStatusAbsence(StatusAbsence status);

    List<Absence> findAbsenceByDateAbsence(LocalDate date);
    List<Absence> findByClassroom_IdClassroom(Long idClassroom);
    @Query("SELECT a FROM Absence a JOIN a.classroom.students s WHERE s.idUser = :id")
    List<Absence> findAbsenceByStudentId(@Param("id") Long studentId);

    @Query("SELECT DISTINCT u FROM User u JOIN u.absences a WHERE a.classroom.idClassroom = :classroomId")
    List<User> findStudentsWithAbsenceInClassroom(@Param("classroomId") Long classroomId);




}
