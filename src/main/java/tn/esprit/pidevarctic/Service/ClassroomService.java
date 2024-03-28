package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

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

//    @Override
//    public Classroom affectertoTeacher(Long idClassroom, Long idTeacher) {
//        User teacher = userRepository.findById(idTeacher).orElse(null);
//    }


}
