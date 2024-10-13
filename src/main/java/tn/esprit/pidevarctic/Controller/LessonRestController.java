package tn.esprit.pidevarctic.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
import tn.esprit.pidevarctic.entities.Visibility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequestMapping("/lesson")
@AllArgsConstructor
@RestController
public class LessonRestController {
    private LessonService lessonService;
    private ClassroomService classroomService;


    @PostMapping(value = "/add")

        public Lesson addLesson(@RequestParam("lesson") String lessonName,
                                @RequestParam("Visibility") Visibility visibility,
                                @RequestParam(required = false) Long classroom,
                                @RequestParam(required = false) MultipartFile file) throws IOException {

            return lessonService.addLesson(lessonName,visibility,classroom, file);
        }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<?> updateLesson(@RequestParam("lesson") String lessonName,
                                          @RequestParam("Visibility") Visibility visibility,
                                          @PathVariable Long lessonId,
                                          @RequestParam(required = false) MultipartFile file) {
        try {
            ResponseEntity<?> responseEntity = lessonService.updateLesson(lessonName, visibility,lessonId, file);
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update lesson: " + e.getMessage());
        }
    }


//    @PutMapping("/update/{lessonId}")
//    public Lesson updateLesson(@RequestBody Lesson lesson, @PathVariable Long lessonId) {
//        ResponseEntity<?> responseEntity = lessonService.updateLesson(lesson, lessonId);
//
//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            return (Lesson) responseEntity.getBody();
//        } else {
//            throw new RuntimeException("Failed to update lesson: " + responseEntity.getBody());
//        }
//    }

    @GetMapping("/get/{numLesson}")
    public Lesson getLesson(@PathVariable Long numLesson) {

        return lessonService.getLessonById(numLesson);
    }

    @DeleteMapping("/delete/{numLesson}")
    public ResponseEntity<?> removeLesson(@PathVariable Long numLesson) {
        lessonService.deleteLesson(numLesson);
        String message = "Delete successful";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public List<Lesson> getAll() {

        return lessonService.getAllLesson();
    }
    @GetMapping("/getLessonByCLassroom/{classroomId}")
    public List<Lesson> getLessonsByClassroom(@PathVariable("classroomId") Long classroomId){
        return lessonService.getLessonsByClassroom(classroomId);
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String fileName) {
        try {
            FileSystemResource fileSystemResource = lessonService.downloadLessonDocument(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileSystemResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}



