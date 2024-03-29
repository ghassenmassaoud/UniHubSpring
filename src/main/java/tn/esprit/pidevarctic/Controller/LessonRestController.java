package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.LessonService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@RequestMapping("/lesson")
@AllArgsConstructor
@RestController
public class LessonRestController {
    private LessonService lessonService;
    private ClassroomService classroomService;
    @PostMapping("/add")
    public Lesson addLesson(@RequestBody Lesson lesson,@RequestParam Long classroom ){
        Classroom classroom1 = classroomService.getClassroomById(classroom);
        lesson.setClassroom(classroom1);
        return lessonService.addLesson(lesson);
    }
    @PutMapping("/update/{lessonId}")
    public ResponseEntity<?> updateLesson(@RequestBody Lesson lesson, @PathVariable Long lessonId) {
        Lesson existingLesson = lessonService.getLessonById(lessonId);

        if (existingLesson == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found");
        }

        existingLesson.setLessonName(lesson.getLessonName());
        existingLesson.setVisibility(lesson.getVisibility());

        if (lesson.getClassroom() != null && lesson.getClassroom().getIdClassroom() != null) {
            Classroom classroom = classroomService.getClassroomById(lesson.getClassroom().getIdClassroom());
            if (classroom != null) {

                existingLesson.setClassroom(classroom);
            } else {
             
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom with provided ID not found");
            }
        }

        existingLesson.setTasks(lesson.getTasks());

        Lesson updatedLessonEntity = lessonService.updateLesson(existingLesson);

        return ResponseEntity.ok(updatedLessonEntity);
    }

    @GetMapping("/get/{numLesson}")
    public Lesson getLesson(@PathVariable Long numLesson){
        return lessonService.getLessonById(numLesson);
    }
    @DeleteMapping("/delete/{numLesson}")
    public ResponseEntity<String> removeLesson(@PathVariable Long numLesson){
        lessonService.deleteLesson(numLesson);
        String message = "Delete successful";
        return ResponseEntity.ok(message);
    }
    @GetMapping("/all")
    public List<Lesson> getAll(){
        return lessonService.getAllLesson();
    }
}
