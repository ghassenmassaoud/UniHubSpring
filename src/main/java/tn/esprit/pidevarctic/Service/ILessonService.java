package tn.esprit.pidevarctic.Service;

import org.springframework.http.ResponseEntity;
import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

public interface ILessonService {
    Lesson addLesson(Lesson lesson,Long classroom);
    ResponseEntity<?> updateLesson(Lesson lesson, Long lessonId);
    void deleteLesson(Long idLesson);
    Lesson getLessonById(Long idLesson);
    List<Lesson> getAllLesson();


}
