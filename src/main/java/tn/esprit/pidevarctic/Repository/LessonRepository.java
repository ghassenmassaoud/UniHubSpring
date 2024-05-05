package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
   // List<Lesson> findByClassroom(Classroom classroomId);
    List<Lesson> findByClassroom_IdClassroom(Long classroomId);
    @Query("SELECT l FROM Lesson l WHERE l.classroom.idClassroom = :classroomId")
    List<Lesson> findLessonsByClassroomId(Long classroomId);
    @Modifying
    @Query("DELETE FROM Document d WHERE d.lesson.idLesson = :lessonId")
    void deleteDocumentsByLessonId(@Param("lessonId") Long lessonId);

}