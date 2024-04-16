package tn.esprit.pidevarctic.Service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Task;
import tn.esprit.pidevarctic.entities.TaskState;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ITaskService {
    Task addTask(Task task, Long lessonId, MultipartFile file) throws IOException;
    Task updateTask(Task updatedTask, Task existingTask);
    void deleteTask(Long idTask);
    Task getTaskById(Long idTask);
    List<Task> getAllTask();
    public List<Task> searchByStatus(TaskState status);
    List<Task> searchByDate(LocalDate date);
}
