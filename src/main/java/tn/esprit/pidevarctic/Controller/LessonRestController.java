package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.LessonService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.User;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/lesson")
@AllArgsConstructor
@RestController
public class LessonRestController {
    private LessonService lessonService;
    private ClassroomService classroomService;

    @PostMapping(value = "/add")
    public Lesson addLesson(@RequestPart("lesson") Lesson lesson,
                            @RequestParam(required = false) Long classroom,
                            @RequestParam(required = false) MultipartFile file) throws IOException {

        return lessonService.addLesson(lesson, classroom, file);
    }

    @PutMapping("/update/{lessonId}")
    public Lesson updateLesson(@RequestBody Lesson lesson, @PathVariable Long lessonId) {
        ResponseEntity<?> responseEntity = lessonService.updateLesson(lesson, lessonId);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return (Lesson) responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to update lesson: " + responseEntity.getBody());
        }
    }

    @GetMapping("/get/{numLesson}")
    public Lesson getLesson(@PathVariable Long numLesson) {

        return lessonService.getLessonById(numLesson);
    }

    @DeleteMapping("/delete/{numLesson}")
    public ResponseEntity<String> removeLesson(@PathVariable Long numLesson) {
        lessonService.deleteLesson(numLesson);
        String message = "Delete successful";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public List<Lesson> getAll() {

        return lessonService.getAllLesson();
    }
}
