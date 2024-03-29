package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}