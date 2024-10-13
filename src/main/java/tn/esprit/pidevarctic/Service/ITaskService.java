package tn.esprit.pidevarctic.Service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.ReplyTask;
import tn.esprit.pidevarctic.entities.Task;
import tn.esprit.pidevarctic.entities.TaskState;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ITaskService {

    Task addTask(String TaskDescription, LocalDateTime deadline, Long classroomId, MultipartFile file) throws IOException;

    Task updateTask(Long taskId, String taskDescription, LocalDateTime deadline, MultipartFile file) throws IOException ;

    void deleteTask(Long idTask);

    Task getTaskById(Long idTask);

    List<Task> getAllTask();

    public List<Task> searchByStatus(TaskState status);

    List<Task> searchByDate(LocalDate date);

    ReplyTask replyTask(Long taskId, MultipartFile file, Long student) throws IOException;

    ReplyTask evaluateTask(Long taskId,int mark);
    List<Task> getTaskByClassroom(Long classroomId);
    void cancelReplyTask(Long replyTaskId);
    List<ReplyTask> getReplyTasksByTaskId(Long taskId);
}
