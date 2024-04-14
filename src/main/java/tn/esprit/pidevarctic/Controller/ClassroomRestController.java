package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.DocumentService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;
import tn.esprit.pidevarctic.message.ResponseMessage;

import java.io.IOException;
import java.util.List;

@RequestMapping("/classroom")
@AllArgsConstructor
@RestController
public class ClassroomRestController {
    private ClassroomService classroomService;
    private UserService userService;
    private DocumentService documentService;



//    @PostMapping("/add")
//    public Classroom addClassroom(@RequestBody Classroom classroom) {
//        return classroomService.addClassroom(classroom);
//    }
@PostMapping("/add")
public ResponseEntity<Classroom> addClassroom(@RequestBody Classroom classroom, @RequestParam Long teacherId) {
    User teacher = userService.getUserById(teacherId);
    if (teacher == null) {
        return ResponseEntity.notFound().build(); // Handle teacher not found
    }
    classroom.setTeacher(teacher);
    Classroom savedClassroom = classroomService.addClassroom(classroom);
    return ResponseEntity.ok(savedClassroom);
}
    @PutMapping("/update/{classroomId}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long classroomId, @RequestBody Classroom updatedClassroom) {
        Classroom existingClassroom = classroomService.getClassroomById(classroomId);
        if (existingClassroom == null) {
            return ResponseEntity.notFound().build();
        }
        existingClassroom.setClassroomName(updatedClassroom.getClassroomName());

        Classroom updatedClassroomEntity = classroomService.updateClassroom(existingClassroom);

        return ResponseEntity.ok(updatedClassroomEntity);
    }


    @GetMapping("/get/{numClassroom}")
    public Classroom getClassroom(@PathVariable Long numClassroom) {
        return classroomService.getClassroomById(numClassroom);
    }

    @DeleteMapping("/delete/{numClassroom}")
    public ResponseEntity<String> removeClassroom(@PathVariable Long numClassroom) {
        classroomService.deleteClassroom(numClassroom);
        // Retourner un message de suppression réussie
        String message = "Delete successful";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public List<Classroom> getAll() {
        return classroomService.getAllClassroom();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchClassroomByNameOrTeacherName(@PathVariable String name) {
        List<Classroom> classrooms = classroomService.SearchClassroom(name);
        if (!classrooms.isEmpty()) {
            return ResponseEntity.ok(classrooms);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Classroom Not Found");
        }
    }
    @PostMapping("/affectStudentToClassroom/{classroomId}/{studentId}")
        public User affectStudentToClassroom(@PathVariable Long studentId, @PathVariable Long classroomId) {
                return classroomService.affectStudentToClassroom(studentId, classroomId);

        }
    @GetMapping("/students/{classroomId}")
    public ResponseEntity<List<User>> getEnrolledStudents(@PathVariable Long classroomId) {
        List<User> enrolledStudents = classroomService.getEnrolledStudents(classroomId);
        return ResponseEntity.ok(enrolledStudents);
    }
    //upload
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            documentService.uploadFile(file);
            return "File uploaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
//        String message = "";
//        try {
//            documentService.save(file);
//
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }
    }


