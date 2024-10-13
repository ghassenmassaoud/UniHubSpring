package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.ReplyTask;
import tn.esprit.pidevarctic.entities.Task;

import java.util.List;

public interface ReplayTaskRepository extends JpaRepository<ReplyTask, Long> {
    List<ReplyTask> findByTask(Task task);
    List<ReplyTask> findByTask_IdTask(Long taskId);
}
