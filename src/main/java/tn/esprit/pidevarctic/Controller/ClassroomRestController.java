package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@RequestMapping("/classroom")
@AllArgsConstructor
@RestController
public class ClassroomRestController {
    private ClassroomService classroomService;
    private UserService userService;



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
        public ResponseEntity<?> affectStudentToClassroom(@PathVariable Long studentId, @PathVariable Long classroomId) {
            try {
                classroomService.affectStudentToClassroom(studentId, classroomId);
                return ResponseEntity.ok("Student successfully assigned to classroom");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Classroom or Student Not Found");
            }
        }
    }


