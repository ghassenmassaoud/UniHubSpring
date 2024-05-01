package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
   // List<Lesson> findByClassroom(Classroom classroomId);
    List<Lesson> findByClassroom_IdClassroom(Long classroomId);

}