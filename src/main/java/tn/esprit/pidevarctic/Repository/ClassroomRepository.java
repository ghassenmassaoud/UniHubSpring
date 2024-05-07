package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    @Query("SELECT c FROM Classroom c WHERE c.classroomName = :name OR c.teacher.firstName = :name ")
    List<Classroom> findByClassroomNameOrTeacherName(@Param("name") String name);
    List<Classroom> findByStudentsIn(List<User> students);

}