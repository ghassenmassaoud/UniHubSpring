package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidevarctic.Service.ITaskService;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.StatusAbsence;
import tn.esprit.pidevarctic.entities.Task;
import tn.esprit.pidevarctic.entities.TaskState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    //    @Query("SELECT t FROM Task t WHERE  t.taskState = : status  OR t.deadline=: date")
//    List<Task> findTaskByDeadlineOrAndTaskState(@Param("status") TaskState status, @Param("date") LocalDate date);
    List<Task> findTaskByTaskState(TaskState status);

    List<Task> findTaskByDeadline(LocalDate date);

    List<Task> findByClassroom_IdClassroom(Long classroomId);
//    @Query("SELECT t FROM Task t WHERE t.deadline <= :deadline AND t.replyTasks IS EMPTY")
//    List<Task> findTasksByDeadlineBeforeAndNoReply(@Param("deadline") LocalDateTime deadline);
@Query("SELECT DISTINCT t FROM Task t JOIN FETCH t.classroom c JOIN FETCH c.students s WHERE t.deadline <= :deadline AND NOT EXISTS (SELECT 1 FROM ReplyTask rt WHERE t.idTask = rt.task.idTask)")
List<Task> findTasksWithEnrolledStudentsByDeadlineBeforeAndNoReply(@Param("deadline") LocalDateTime deadline);

    @Modifying
    @Query("DELETE FROM Document d WHERE d.task.idTask = :taskId")
    void deleteDocumentsBytaskId(@Param("taskId") Long taskId);
}
