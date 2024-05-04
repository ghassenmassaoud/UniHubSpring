package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
   // List<Lesson> findByClassroom(Classroom classroomId);
    List<Lesson> findByClassroom_IdClassroom(Long classroomId);
    @Query("SELECT l FROM Lesson l WHERE l.classroom.idClassroom = :classroomId")
    List<Lesson> findLessonsByClassroomId(Long classroomId);

}