package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClassroomService implements IClassroomService {
    private ClassroomRepository classroomRepository;
    private UserRepository userRepository;
    @Override
    public Classroom addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom updateClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }




    @Override
    public void deleteClassroom(Long idClassroom) {
        classroomRepository.deleteById(idClassroom);
    }

    @Override
    public Classroom getClassroomById(Long idClassroom) {
        return classroomRepository.findById(idClassroom).orElse(null);
    }

    @Override
    public List<Classroom> getAllClassroom() {
        return classroomRepository.findAll();
    }

    @Override
    public List<Classroom> SearchClassroom(String name) {
        return classroomRepository.findByClassroomNameOrTeacherName(name);
    }

    @Override
    public void affectStudentToClassroom(Long studentId, Long classroomId) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        if (student != null && classroom != null) {
            Set<Classroom> classrooms = student.getClassrooms();
            classrooms.add(classroom);
            student.setClassrooms(classrooms);
            userRepository.save(student);
        }
    }

}
