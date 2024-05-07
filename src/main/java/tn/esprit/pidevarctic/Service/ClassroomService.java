package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public User affectStudentToClassroom(Long studentId, Long classroomId) {

                User student = userRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        if (classroom.getStudents().contains(student)) {
            throw new StudentAlreadyEnrolledException("Student is already enrolled in this classroom");
        }

        classroom.getStudents().add(student);
        classroomRepository.save(classroom);


        return student;
//
    }
    public List<User> getEnrolledStudents(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
        Set<User> enrolledStudentsSet = classroom.getStudents();
        return enrolledStudentsSet.stream().collect(Collectors.toList());
    }
    @Override
    public boolean removeStudentFromClassroom(Long classroomId, Long studentId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if (!classroom.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is not enrolled in this classroom");
        }

        classroom.getStudents().remove(student);
        classroomRepository.save(classroom);

        return true;
    }
    @Override
    public List<Classroom> getClassroomsForStudent(Long studentId) {
        List<User> enrolledStudents = getEnrolledStudents(studentId);
        List<Classroom> classrooms = classroomRepository.findByStudentsIn(enrolledStudents);
        return classrooms;
    }


}
