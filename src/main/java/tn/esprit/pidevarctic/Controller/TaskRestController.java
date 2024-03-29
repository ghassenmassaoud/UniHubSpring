package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.LessonService;
import tn.esprit.pidevarctic.Service.TaskService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.Task;
import tn.esprit.pidevarctic.entities.User;


import java.util.List;

@RequestMapping("/task")
@AllArgsConstructor
@RestController
public class TaskRestController {
    private TaskService taskService;
    private LessonService lessonService;



    @PostMapping("/add")
    public Task addTask(@RequestBody Task task, @RequestParam Long lesson) {
        Lesson lesson1= lessonService.getLessonById(lesson);

        task.setLesson(lesson1);
        return taskService.addTask(task);
    }
//    @PutMapping("/update/{taskId}")
//    public Task updateTask(@RequestBody Task task, @PathVariable Long taskId) {
//        Task task1 = taskService.getTaskById(taskId);
//
//        return taskService.updateTask(task);
//    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateUser(@RequestBody Task updatedTask, @PathVariable Long taskId) {
        Task existingTask = taskService.getTaskById(taskId);
        if (existingTask != null) {
            // Mettre à jour les champs de la tâche existante avec les valeurs de la nouvelle tâche
            existingTask.setTaskDescription(updatedTask.getTaskDescription());
            existingTask.setDeadline(updatedTask.getDeadline());
            existingTask.setMark(updatedTask.getMark());
            existingTask.setTaskState(updatedTask.getTaskState());

            // Vérifier si la salle de classe est spécifiée dans la requête
            if (updatedTask.getLesson() != null) {
                // Récupérer la salle de classe à partir de la base de données
                Long LessonId= updatedTask.getLesson().getIdLesson();
               Lesson lesson = lessonService.getLessonById(LessonId);
                if (lesson != null) {
                    existingTask.setLesson(lesson);
                } else {
                    // Gérer le cas où la salle de classe n'existe pas
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }

            // Sauvegarder la tâche mise à jour dans la base de données
            Task updatedTaskEntity = taskService.updateTask(existingTask);
            return ResponseEntity.ok(updatedTaskEntity);
        } else {
            // Gérer le cas où la tâche avec l'ID donné n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/{numTask}")
    public Task getTask(@PathVariable Long numTask) {
        return taskService.getTaskById(numTask);
    }

    @DeleteMapping("/delete/{numTask}")
    public ResponseEntity<String> removeTask(@PathVariable Long numTask) {

        taskService.deleteTask(numTask);
        //Retourner un message de suppression réussie
        String message = "Delete successful";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public List<Task> getAll() {
        return taskService.getAllTask();
    }


}
