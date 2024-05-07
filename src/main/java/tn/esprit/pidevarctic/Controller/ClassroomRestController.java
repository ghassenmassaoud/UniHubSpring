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

    @GetMapping("/ClassStudents/{studentId}")
    public ResponseEntity<List<Classroom>> getClassroomsForStudent(@PathVariable Long studentId) {
        List<Classroom> classrooms = classroomService.getClassroomsForStudent(studentId);
        return ResponseEntity.ok().body(classrooms);
    }
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
    public ResponseEntity<?> removeClassroom(@PathVariable Long numClassroom) {
        classroomService.deleteClassroom(numClassroom);
        // Retourner un message de suppression réussie
        //String message = "Delete successful";
        return ResponseEntity.ok().body("{\"message\":\"Delete successful\"");
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
    @DeleteMapping("/removeStudentFromClassroom/{classroomId}/{studentId}")
    public ResponseEntity<?> removeStudentFromClassroom(@PathVariable Long classroomId, @PathVariable Long studentId) {
        boolean removed = classroomService.removeStudentFromClassroom(classroomId, studentId);
        if (removed) {
            return ResponseEntity.ok("Étudiant supprimé de la classe avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Étudiant non trouvé dans la classe.");
        }
    }



    }


