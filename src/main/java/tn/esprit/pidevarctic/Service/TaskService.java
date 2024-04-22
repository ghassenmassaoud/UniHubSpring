package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.TaskRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private TaskRepository taskRepository;
    private DocumentService documentService;
    private LessonService lessonService;
    private UserRepository userRepository;

    @Override
    public Task addTask(Task task, Long lessonId, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForTask(file, task);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
            task.setDocuments(documents);
        }

        task.setMark(0);
        task.setTaskState(TaskState.ASSIGNED);
        return taskRepository.save(task);
    }


    @Override
    public Task updateTask(Task updatedTask, Task existingTask) {
        existingTask.setTaskDescription(updatedTask.getTaskDescription());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setMark(updatedTask.getMark());
        existingTask.setTaskState(updatedTask.getTaskState());
        if (updatedTask.getLesson() != null) {
            Lesson lesson = lessonService.getLessonById(updatedTask.getLesson().getIdLesson());
            if (lesson != null) {
                existingTask.setLesson(lesson);
            } else {
                throw new IllegalArgumentException("Lesson not found");
            }
        }
        return taskRepository.save(existingTask);
    }


    @Override
    public void deleteTask(Long idTask) {
        taskRepository.deleteById(idTask);
    }

    @Override
    public Task getTaskById(Long idTask) {
        return taskRepository.findById(idTask).orElse(null);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> searchByStatus(TaskState status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        return taskRepository.findTaskByTaskState(status);
    }

    @Override
    public List<Task> searchByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Deadline cannot be null.");
        }
        return taskRepository.findTaskByDeadline(date);
    }

    @Override
    public Task replyTask(Long taskId, MultipartFile file) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        if (task.getTaskState() != TaskState.ASSIGNED) {
            throw new IllegalStateException("Task must be in assigned state to reply.");
        }
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime deadline = task.getDeadline();

        if (currentDate.isBefore(deadline)) {
            task.setTaskState(TaskState.TURNED_IN);
        } else {
            task.setTaskState(TaskState.TURNED_LATE);
        }

        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForTask(file, task);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
            task.setDocuments(documents);
        }

        return taskRepository.save(task);
    }
    @Override
    public Task evaluateTask(Long taskId,int mark) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        if (task.getTaskState() != TaskState.TURNED_IN) {
            throw new IllegalStateException("Task must be in TURNED_IN state to be evaluated.");
        }


        task.setMark(mark);

        return taskRepository.save(task);
    }
}
