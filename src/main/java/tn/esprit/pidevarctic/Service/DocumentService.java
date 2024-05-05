package tn.esprit.pidevarctic.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.pidevarctic.Repository.DocumentRepository;
import tn.esprit.pidevarctic.Repository.LessonRepository;
import tn.esprit.pidevarctic.Repository.ReplayTaskRepository;
import tn.esprit.pidevarctic.Repository.TaskRepository;
import tn.esprit.pidevarctic.entities.Document;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.ReplyTask;
import tn.esprit.pidevarctic.entities.Task;

@AllArgsConstructor
@Service
public class DocumentService implements IDocumentService {

    private DocumentRepository documentRepository;
    private TaskRepository taskRepository;
    private LessonRepository lessonRepository;
    private ReplayTaskRepository replytaskRepository;
    private final String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads";
    private final String uploadUrlPrefix = "/uploads/";

    public String constructFileUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(uploadUrlPrefix)
                .path(fileName)
                .toUriString();
    }

    public Document uploadFileForTask(MultipartFile file, Task task) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou n'existe pas.");
        }

        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Task savedTask = taskRepository.save(task);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = uploadDirectory + "/" + fileName;

        Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

        Document document = new Document();
        document.setName(fileName);
        document.setUrl(constructFileUrl(fileName));
        document.setTask(savedTask);

        return documentRepository.save(document);
    }
    public Document uploadFileForLesson(MultipartFile file, Lesson lesson) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou n'existe pas.");
        }

        // Récupérer le nom du fichier sans le chemin complet
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Lesson savedLesson = lessonRepository.save(lesson);

        String filePath = uploadDirectory + "/" + fileName;
        Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

        Document document = new Document();
        document.setName(fileName);
        document.setUrl(constructFileUrl(fileName));
        document.setLesson(savedLesson);

        return documentRepository.save(document);
    }



    public FileSystemResource downloadFile(String fileName) {
        String filePath = uploadDirectory + "/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            throw new IllegalArgumentException("Le fichier demandé n'existe pas : " + fileName);
        }
    }

    public Document uploadFileForReplyTask(MultipartFile file, ReplyTask replyTask) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou n'existe pas.");
        }

        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Assurez-vous de sauvegarder la réponse de la tâche avant d'ajouter le document
        ReplyTask savedTask = replytaskRepository.save(replyTask);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = uploadDirectory + "/" + fileName;

        Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

        Document document = new Document();
        document.setName(fileName);
        document.setUrl(constructFileUrl(fileName));
        document.setReplyTask(savedTask); // Assurez-vous que la réponse de la tâche est correctement associée

        return documentRepository.save(document);
    }

}