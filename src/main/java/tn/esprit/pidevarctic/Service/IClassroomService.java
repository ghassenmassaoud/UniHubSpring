package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface IClassroomService {
    Classroom addClassroom(Classroom classroom);
    Classroom updateClassroom(Classroom classroom);
    void deleteClassroom(Long idClassroom);
    Classroom getClassroomById(Long idClassroom);
    List<Classroom> getAllClassroom();
    //Classroom affectertoTeacher(Long idClassroom, Long idTeacher);
    List<Classroom> SearchClassroom(String name);
   User affectStudentToClassroom(Long studentId, Long classroomId);
    List<User> getEnrolledStudents(Long classroomId);
    boolean removeStudentFromClassroom(Long classroomId, Long studentId);
    List<Classroom> getClassroomsForStudent(Long studentId);

}
