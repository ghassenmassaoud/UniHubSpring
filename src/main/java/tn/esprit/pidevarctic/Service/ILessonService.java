package tn.esprit.pidevarctic.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.Visibility;

import java.io.IOException;
import java.util.List;

public interface ILessonService {
    Lesson addLesson(String lessonName ,Visibility visibility, Long classroom, MultipartFile file) throws IOException;
    ResponseEntity<?> updateLesson(String lessonName , Visibility visibility, Long lessonId, MultipartFile file)throws IOException;
    void deleteLesson(Long idLesson);
    Lesson getLessonById(Long idLesson);
    List<Lesson> getAllLesson();
    List<Lesson> getLessonsByClassroom(Long classroomId);

    // Lesson downloadLesson(Long lessonId,  MultipartFile file );


}
