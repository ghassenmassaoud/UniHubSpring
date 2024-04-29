package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Service.ClassroomService;
import tn.esprit.pidevarctic.Service.LessonService;
import tn.esprit.pidevarctic.Service.TaskService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.*;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/task")
@AllArgsConstructor
@RestController
public class TaskRestController {
    private TaskService taskService;
    private LessonService lessonService;
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Task> addTask(@RequestPart("task") Task task,
                                        @RequestParam(required = false) Long lessonId,
                                        @RequestParam(required = false) MultipartFile file) throws IOException {
        Lesson lesson1= lessonService.getLessonById(lessonId);

        task.setLesson(lesson1);
        Task newTask = taskService.addTask(task, lessonId, file);
        return ResponseEntity.ok().body(newTask);
    }



    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@RequestBody Task updatedTask, @PathVariable Long taskId) {
        Task existingTask = taskService.getTaskById(taskId);
        if (existingTask != null) {
            Task updatedTaskEntity = taskService.updateTask(updatedTask, existingTask);
            return ResponseEntity.ok(updatedTaskEntity);
        } else {
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

        @GetMapping("/search/{status}")
        public ResponseEntity<?> searchTaskByStatus(@PathVariable TaskState status) {
            List<Task> tasks = taskService.searchByStatus(status);
        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Absence Not Found");
        }
    }
    @GetMapping("/search/{date}")
    public ResponseEntity<?> searchTaskByDate(@PathVariable LocalDate date) {
        List<Task> tasks = taskService.searchByDate(date);
        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Absence Not Found");
        }
    }
@PostMapping("/replyTask")
    public  ResponseEntity<Task> replyTaskStudent(
    @RequestParam(required = false) Long taskId,
    @RequestParam(required = false) MultipartFile file) throws IOException {

        Task newTask = taskService.replyTask(taskId,file);
        return ResponseEntity.ok().body(newTask);
    }
    @PostMapping("/evaluteTask/{taskId}")
    public ResponseEntity<Task> evaluteTaskTeacher(@PathVariable Long taskId, @RequestBody Map<String, Integer> requestBody) {
       // @RequestBody Map<String, Integer> requestBody, récupère les données
        // de la requête HTTP sous forme d'une carte (map) où les clés
        // sont des chaînes de caractères et les valeurs sont des entiers.
        // Cette carte contient les données JSON envoyées dans le corps de la requête.
        Integer mark = requestBody.get("mark");
        Task newTask = taskService.evaluateTask(taskId, mark);
        return ResponseEntity.ok().body(newTask);
    }



}
