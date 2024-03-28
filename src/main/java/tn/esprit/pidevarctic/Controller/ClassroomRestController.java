package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
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
    public void removeClassroom(@PathVariable Long numClassroom) {
        classroomService.deleteClassroom(numClassroom);
    }

    @GetMapping("/all")
    public List<Classroom> getAll() {
        return classroomService.getAllClassroom();
    }

//    @PutMapping("/assign/{numTeacher}")
//    public ResponseEntity<Classroom> assignClassToTeacher(@RequestBody Classroom classroom, @PathVariable Long numTeacher) {
//        User teacher = userService.getUserById(numTeacher);
//        classroom.setTeacher(teacher);
//        Classroom assignedClassroom = classroomService.addClassroom(classroom);
//        return ResponseEntity.ok(assignedClassroom);
//    }
}
