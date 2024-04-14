package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.LessonRepository;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {
    private LessonRepository lessonRepository;
    private ClassroomRepository classroomRepository;
    private ClassroomService classroomService;
    //private UserRepository userRepository;
    @Override

    public Lesson addLesson(Lesson lesson , Long classroom) {
        Classroom classroom1 = classroomRepository.findById(classroom).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        lesson.setClassroom(classroom1);

        return lessonRepository.save(lesson);
    }
    @Override
    public ResponseEntity<?> updateLesson(Lesson lesson , Long lessonId) {
  Lesson lesson1 =lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        if (lesson1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found");
        }

        lesson1.setLessonName(lesson.getLessonName());
        lesson1.setVisibility(lesson.getVisibility());

        if (lesson.getClassroom() != null && lesson.getClassroom().getIdClassroom() != null) {
            Classroom classroom = classroomService.getClassroomById(lesson.getClassroom().getIdClassroom());
            if (classroom != null) {

                lesson1.setClassroom(classroom);
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom with provided ID not found");
            }
        }

        lesson1.setTasks(lesson.getTasks());
        Lesson updatedLesson = lessonRepository.save(lesson1);
        return ResponseEntity.ok(updatedLesson);
    }


    @Override
    public void deleteLesson(Long idLesson) {
        lessonRepository.deleteById(idLesson);
    }

    @Override
    public Lesson getLessonById(Long idLesson) {
        return lessonRepository.findById(idLesson).orElse(null);
    }

    @Override
    public List<Lesson> getAllLesson() {
        return lessonRepository.findAll();
    }


}
