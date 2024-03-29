package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Classroom;

import java.util.List;

public interface IClassroomService {
    Classroom addClassroom(Classroom classroom);
    Classroom updateClassroom(Classroom classroom);
    void deleteClassroom(Long idClassroom);
    Classroom getClassroomById(Long idClassroom);
    List<Classroom> getAllClassroom();
    //Classroom affectertoTeacher(Long idClassroom, Long idTeacher);
    List<Classroom> SearchClassroom(String name);
    void affectStudentToClassroom(Long studentId, Long classroomId);

}
