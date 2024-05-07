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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/task")
@AllArgsConstructor
@RestController
public class TaskRestController {
    private TaskService taskService;
    private LessonService lessonService;
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Task> addTask(@RequestParam("TaskDescription") String TaskDescription,
                                        @RequestParam("Deadline")LocalDateTime deadline,
                                        @RequestParam("classroomId") Long classroomId,
                                        @RequestParam(required = false) MultipartFile file) throws IOException {

        Task newTask = taskService.addTask(TaskDescription,deadline,classroomId, file);
        return ResponseEntity.ok().body(newTask);
    }

//    @PutMapping("/update/{taskId}")
//    public ResponseEntity<Task> updateTask(@RequestBody Task updatedTask, @PathVariable Long taskId) {
//        try {
//            Task updatedTaskEntity = taskService.updateTask(taskId, updatedTask.getTaskDescription(), updatedTask.getDeadline(), updatedTask.getDocuments());
//            return ResponseEntity.ok(updatedTaskEntity);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    @PutMapping(value="/update" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateTask(@RequestParam("taskId") Long taskId,
                                        @RequestParam("TaskDescription") String taskDescription,
                                        @RequestParam("Deadline") LocalDateTime deadline,
                                        @RequestParam(required = false) MultipartFile file) throws IOException {

        try {
            Task updatedTask = taskService.updateTask(taskId, taskDescription, deadline, file);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task: " + e.getMessage());
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
    public  ResponseEntity<ReplyTask> replyTaskStudent(@RequestParam(required = false) Long taskId,
                                                  @RequestParam(required = false)Long student,
                                                  @RequestParam(required = false) MultipartFile file) throws IOException {

        ReplyTask newTask = taskService.replyTask(taskId,file,student);
        return ResponseEntity.ok().body(newTask);
    }
    @PostMapping("/evaluteTask/{taskId}")
    public ResponseEntity<ReplyTask> evaluteTaskTeacher(@PathVariable Long taskId,
                                                        @RequestBody Map<String, Integer> requestBody) {
       // @RequestBody Map<String, Integer> requestBody, récupère les donnée de la requête HTTP sous forme d'une carte (map) où les cléssont des chaînes de caractères et les valeurs sont des entiers.Cette carte contient les données JSON envoyées dans le corps de la requête.
        Integer mark = requestBody.get("mark");
        ReplyTask newTask = taskService.evaluateTask(taskId, mark);
        return ResponseEntity.ok().body(newTask);
    }
    @GetMapping("/getTaskByCLassroom/{classroomId}")
    public List<Task> getTasksByClassroom(@PathVariable("classroomId") Long classroomId){
        return taskService.getTaskByClassroom(classroomId);
    }
    @GetMapping("/getreplytask/{taskId}")
    public List<ReplyTask> getReplyTasksByTaskId(@PathVariable Long taskId){
        return taskService.getReplyTasksByTaskId(taskId);
    }

}
