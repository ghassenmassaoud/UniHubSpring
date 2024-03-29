package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.LessonRepository;
import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {
    private LessonRepository lessonRepository;
    private ClassroomService classroomService;
    //private UserRepository userRepository;
    @Override

    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
    @Override
    public Lesson updateLesson(Lesson lesson) {

        return lessonRepository.save(lesson);
    }


    @Override
    public void deleteLesson(Long idLesson) {
        lessonRepository.deleteById(idLesson);
    }

    @Override
    public Lesson getLessonById(Long idLesson) {
        return lessonRepository.findById(idLesson).orElse(null);
    }

    @Override
    public List<Lesson> getAllLesson() {
        return lessonRepository.findAll();
    }


}
