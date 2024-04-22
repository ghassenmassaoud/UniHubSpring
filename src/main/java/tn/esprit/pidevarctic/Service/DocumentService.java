package tn.esprit.pidevarctic.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.DocumentRepository;
import tn.esprit.pidevarctic.Repository.LessonRepository;
import tn.esprit.pidevarctic.Repository.TaskRepository;
import tn.esprit.pidevarctic.entities.Document;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.Task;

@AllArgsConstructor
@Service
public class DocumentService implements IDocumentService {

    private DocumentRepository documentRepository;
    private TaskRepository taskRepository;
    private LessonRepository lessonRepository;



    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads";
    public Document uploadFileForTask(MultipartFile file, Task task) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou n'existe pas.");
        }

        // Assurez-vous que le dossier de téléversement existe
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Créez les répertoires, y compris tous les parent nécessaires
        }

        // Assurez-vous que la tâche est sauvegardée en premier
        Task savedTask = taskRepository.save(task);

        Document document = new Document();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = uploadDirectory + "/" + fileName;

        // Écrivez le fichier sur le disque
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        document.setName(fileName);
        document.setUrl(filePath);

        // Associez la tâche au document
        document.setTask(savedTask);

        // Enregistrez le document
        Document savedDocument = documentRepository.save(document);

        return savedDocument;
    }


    public Document uploadFileForLesson(MultipartFile file, Lesson lesson) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou n'existe pas.");
        }

        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
       Lesson savedLesson = lessonRepository.save(lesson);

        Document document = new Document();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        document.setName(fileName);
        document.setUrl(uploadDirectory + "/" + fileName);

        document.setLesson(savedLesson);

        Document savedDocument = documentRepository.save(document);

        return savedDocument;
    }
}