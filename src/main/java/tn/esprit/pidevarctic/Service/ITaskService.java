package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Task;

import java.util.List;

public interface ITaskService {
    Task addTask(Task task);
    Task updateTask(Task task);
    void deleteTask(Long idTask);
    Task getTaskById(Long idTask);
    List<Task> getAllTask();


}
