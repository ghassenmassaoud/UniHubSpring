package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Lesson;

import java.util.List;

public interface ILessonService {
    Lesson addLesson(Lesson lesson);
    Lesson updateLesson(Lesson lesson);
    void deleteLesson(Long idLesson);
    Lesson getLessonById(Long idLesson);
    List<Lesson> getAllLesson();


}
