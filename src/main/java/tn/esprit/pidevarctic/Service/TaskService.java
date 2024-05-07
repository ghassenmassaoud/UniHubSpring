package tn.esprit.pidevarctic.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
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
    private ReplayTaskRepository replytaskRepository;
    private LessonRepository lessonRepository;
    private DocumentService documentService;
    private LessonService lessonService;
    private UserRepository userRepository;
    private ClassroomRepository classroomRepository;
    private DocumentRepository documentRepository;
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
    @Override
    public Task updateTask(Long taskId, String taskDescription, LocalDateTime deadline, MultipartFile file) throws IOException {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTaskDescription(taskDescription);
        existingTask.setDeadline(deadline);

        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForTask(file, existingTask);
            existingTask.getDocuments().clear(); // Remove existing documents
            existingTask.getDocuments().add(document); // Add new document
        }

        return taskRepository.save(existingTask);
    }



    @Transactional
    //ensures that both the document deletion and lesson deletion are treated as a single transaction. If either operation fails, the entire transaction is rolled back.
    @Override
    public void deleteTask(Long idLesson) {
        // Delete associated documents first
        deleteDocumentsBytaskId(idLesson);
        // Then delete the lesson
        taskRepository.deleteById(idLesson);
    }

    private void deleteDocumentsBytaskId(Long taskId) {
        taskRepository.deleteDocumentsBytaskId(taskId);
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
//@Transactional
    public ReplyTask replyTask(Long taskId, MultipartFile file, Long student) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        // Vérifiez si la tâche est dans l'état correct pour répondre
        if (task.getTaskState() != TaskState.ASSIGNED) {
            throw new IllegalStateException("Task must be in assigned state to reply.");
        }

        // Récupérez l'utilisateur étudiant
        User student1 = userService.getUserById(student);

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime deadline = task.getDeadline();

        ReplyTask replyTask = new ReplyTask();

        // Ajoutez le document à la réponse de la tâche s'il existe
        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForReplyTask(file, replyTask);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
            replyTask.setDocuments(documents);
        }

        // Définissez l'état de la réponse de la tâche en fonction de la date
        if (currentDate.isBefore(deadline)) {
            replyTask.setTaskState(TaskState.TURNED_IN);
        } else {
            replyTask.setTaskState(TaskState.TURNED_LATE);
        }

        // Configurez les autres propriétés de la réponse de la tâche
        replyTask.setTask(task);
        replyTask.setStudent(student1);

        // Sauvegardez la réponse de la tâche
        return replytaskRepository.save(replyTask);
    }
    @Transactional
    @Override
    public void cancelReplyTask(Long replyTaskId) {
        ReplyTask replyTask = replytaskRepository.findById(replyTaskId)
                .orElseThrow(() -> new IllegalArgumentException("ReplyTask not found with id: " + replyTaskId));

        // Supprimez tous les documents associés à cette réponse de tâche
        Set<Document> documents = replyTask.getDocuments();
        if (documents != null && !documents.isEmpty()) {
            for (Document document : documents) {
                documentRepository.delete(document);
            }
        }

        // Supprimez la réponse de la tâche elle-même
        replytaskRepository.delete(replyTask);
    }

@Override
public ReplyTask evaluateTask(Long taskId, int mark) {
    ReplyTask task = replytaskRepository.findById(taskId)
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

    return replytaskRepository.save(task);
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

@Scheduled(cron = "0 0 * * * ?") // Exécute la méthode chaque 15 secondes
public void sendTaskReminderEmail() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime twentyFourHoursBefore = now.plusHours(24);

    List<Task> tasksToRemind = taskRepository.findTasksWithEnrolledStudentsByDeadlineBeforeAndNoReply(twentyFourHoursBefore);

    sendTaskRemindersToEnrolledStudents(tasksToRemind);
}

    public void sendTaskRemindersToEnrolledStudents(List<Task> tasks) {
        for (Task task : tasks) {
            Classroom classroom = task.getClassroom();
            if (classroom != null) {
                List<User> enrolledStudents = new ArrayList<>(classroom.getStudents());
                for (User student : enrolledStudents) {
                    String message = "Dear " + student.getFirstName() + ",\n\nA reminder that the deadline for the task '" + task.getTaskDescription() + "' in your " + classroom.getClassroomName() + " class is approaching.";
                    emailService.sendEmail(student.getEmail(), "Task Reminder", message);
                }
            }
        }
    }


    @Override
    public List<ReplyTask> getReplyTasksByTaskId(Long taskId) {
        return replytaskRepository.findByTask_IdTask(taskId);
    }
        }

