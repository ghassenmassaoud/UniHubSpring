package tn.esprit.pidevarctic.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.*;
import tn.esprit.pidevarctic.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private TaskRepository taskRepository;
  private ClassroomService classroomService;
    private ReplayTaskRepository replaytaskRepository;
    private LessonRepository lessonRepository;
    private DocumentService documentService;
    private LessonService lessonService;
    private UserRepository userRepository;
    private ClassroomRepository classroomRepository;
    private UserService userService;
    private EmailService emailService;
    private RessourceRepository ressourceRepository;

    //
    @Override
    public Task addTask(String TaskDescription, LocalDateTime deadline, Long classroomId, MultipartFile file) throws IOException {
        Classroom classroom1 = classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        Task task= new Task();
        task.setTaskDescription(TaskDescription);
        task.setDeadline(deadline);
        task.setClassroom(classroom1);
        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForTask(file, task);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
            task.setDocuments(documents);
        }

        task.setMark(0);
        task.setTaskState(TaskState.ASSIGNED);
        List<User> students = new ArrayList<>(classroom1.getStudents());

        // Envoyer un e-mail à chaque étudiant
        for (User student : students) {
            String message = "Dear " + student.getFirstName() + ",\n\nA new task has been assigned to your " + classroom1.getClassroomName() + ".";
            emailService.sendEmail(student.getEmail(), "New Task Assigned", message);
        }

        return taskRepository.save(task);
    }


//    @Override
//    public Task updateTask(Task updatedTask, Task existingTask) {
//        existingTask.setTaskDescription(updatedTask.getTaskDescription());
//        existingTask.setDeadline(updatedTask.getDeadline());
//        existingTask.setMark(updatedTask.getMark());
//        existingTask.setTaskState(updatedTask.getTaskState());
//        if (updatedTask.getLesson() != null) {
//            Lesson lesson = lessonService.getLessonById(updatedTask.getLesson().getIdLesson());
//            if (lesson != null) {
//                existingTask.setLesson(lesson);
//            } else {
//                throw new IllegalArgumentException("Lesson not found");
//            }
//        }
//List<User> students = new ArrayList<>(classroom1.getStudents());
//    // Envoyer un e-mail à chaque étudiant
//        for (User student : students) {
//        String message = "Dear " + student.getFirstName() + ",\n\nA new task has been assigned to your " + classroom1.getClassroomName() + ".";
//        emailService.sendEmail(student.getEmail(), "New Task Assigned", message);
//    }
//        return taskRepository.save(existingTask);
//    }


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
    public ReplyTask replyTask(Long taskId, MultipartFile file, Long student) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
        User student1 = userService.getUserById(student);


        if (task.getTaskState() != TaskState.ASSIGNED) {
            throw new IllegalStateException("Task must be in assigned state to reply.");
        }

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime deadline = task.getDeadline();



        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForTask(file, task);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
            task.setDocuments(documents);
        }
        // Create a new set and add the student to it
        Set<User> students = new HashSet<>();
        students.add(student1);
        task.getClassroom().setStudents(students);
        ReplyTask replayTask = new ReplyTask();
        if (currentDate.isBefore(deadline)) {
            replayTask.setTaskState(TaskState.TURNED_IN);
        } else {
            replayTask.setTaskState(TaskState.TURNED_LATE);
        }// or set to TURNED_LATE based on your logic
        replayTask.setTask(task);
        replayTask.setStudent(student1);

        return replaytaskRepository.save(replayTask);
    }

@Override
public ReplyTask evaluateTask(Long taskId, int mark) {
    ReplyTask task = replaytaskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

    if (task.getTaskState() != TaskState.TURNED_IN && task.getTaskState() != TaskState.TURNED_LATE) {
        throw new IllegalStateException("Task must be in TURNED_IN or TURNED_LATE state to be evaluated.");
    }

    task.setMark(mark);

    if (mark < 10) {
        Task originalTask = task.getTask();
        Classroom classroom = originalTask.getClassroom();
        String courseName = classroom.getClassroomName();

        Set<User> studentsInClassroom = classroom.getStudents(); // Étudiants dans la classe uniquement

        for (User student : studentsInClassroom) {
            if (student.getSpeciality() != null) {
                List<Ressource> recommendedResources = recommendResources(courseName, student.getSpeciality());
                String message = "Dear " + student.getFirstName() + ",\n\n";
                message += "We recommend the following resources to improve your grade:\n";
                for (Ressource resource1 : recommendedResources) {
                    message += "- " + resource1.getRessourceName() + "\n";
                }
                message += "\nBest regards,\nYour academic team";

                // Code pour envoyer l'e-mail
                emailService.sendEmail(student.getEmail(), "Resources Recommended to Improve Your Grade", message);
            }
        }
    }

    return replaytaskRepository.save(task);
}

    private List<Ressource> recommendResources(String courseName, Speciality speciality) {
        List<Ressource> recommendedResources = new ArrayList<>();

        // Supposez que vous ayez une méthode dans votre repository ou service pour récupérer les ressources disponibles
        List<Ressource> allResources = ressourceRepository.findAll();

        // Parcourez toutes les ressources disponibles pour trouver celles qui correspondent aux critères
        for (Ressource resource : allResources) {
            if (resource.getRessourceName().equals(courseName) && resource.getRessourceSpace().getSpaceType() == speciality) {
                recommendedResources.add(resource);
            }
        }

        return recommendedResources;
    }
    @Override
    public List<Task> getTaskByClassroom(Long classroomId) {
        return taskRepository.findByClassroom_IdClassroom(classroomId);
    }
//    @Scheduled(cron = "0 * 1 * * ?") // Exécute la méthode chaque heure
//    public void sendTaskReminderEmail() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime twentyFourHoursBefore = now.minusHours(24);
//
//        List<Task> tasksToRemind = taskRepository.findTasksByDeadlineAndNoReply(twentyFourHoursBefore);
//
//        for (Task task : tasksToRemind) {
//
//            Long classroomId = task.getClassroom().getIdClassroom();
//            Classroom classroom1 = classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
//            List<User> students = new ArrayList<>(classroom1.getStudents());
//
//            for (User student : students) {
//                String message = "Dear " + student.getFirstName() + ",\n\nA reminder that the deadline for the task '" + task.getTaskDescription() + "' in your " + classroom1.getClassroomName() + " class is approaching.";
//                emailService.sendEmail(student.getEmail(), "Task Reminder", message);
//            }
//        }
//    }
@Scheduled(cron = "0 0 * * * ?") // Exécute la méthode chaque heure
public void sendTaskReminderEmail() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime twentyFourHoursBefore = now.plusHours(24);

    List<Task> tasksToRemind = taskRepository.findTasksByDeadlineBeforeAndNoReply(twentyFourHoursBefore);

    sendTaskRemindersToEnrolledStudents(tasksToRemind);
}
    public void sendTaskRemindersToEnrolledStudents(List<Task> tasks) {
        for (Task task : tasks) {
            List<User> enrolledStudents = classroomService.getEnrolledStudents(task.getClassroom().getIdClassroom());
            for (User student : enrolledStudents) {
                String message = "Dear " + student.getFirstName() + ",\n\nA reminder that the deadline for the task '" + task.getTaskDescription() + "' in your " + task.getClassroom().getClassroomName() + " class is approaching.";
                emailService.sendEmail(student.getEmail(), "Task Reminder", message);
            }
        }
    }


        }

