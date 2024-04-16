package tn.esprit.pidevarctic.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Lesson;

import java.io.IOException;
import java.util.List;

public interface ILessonService {
    Lesson addLesson(Lesson lesson , Long classroom, MultipartFile file) throws IOException;
    ResponseEntity<?> updateLesson(Lesson lesson, Long lessonId);
    void deleteLesson(Long idLesson);
    Lesson getLessonById(Long idLesson);
    List<Lesson> getAllLesson();


}
