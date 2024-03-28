package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.TaskRepository;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Task;
import tn.esprit.pidevarctic.entities.TaskState;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private TaskRepository taskRepository;
    private ClassroomService classroomService;
    //private UserRepository userRepository;
    @Override
    //public Task addTask(Task task) {
       /// return taskRepository.save(task);
    //}
    public Task addTask(Task task) {
        task.setMark(0);
        task.setTaskState(TaskState.ASSIGNED);

        return taskRepository.save(task);
    }
    @Override
    public Task updateTask(Task task) {

        return taskRepository.save(task);
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


}
