package tn.esprit.pidevarctic.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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


@Service
public class DocumentService implements IDocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private LessonRepository lessonRepository;



    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads";


    public Document uploadFileForTask(MultipartFile file, Task task) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou n'existe pas.");
        }

        // Assurez-vous que la tâche est sauvegardée en premier
        Task savedTask = taskRepository.save(task);

        Document document = new Document();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        document.setName(fileName);
        document.setUrl(uploadDirectory + "/" + fileName);

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

        // Assurez-vous que la tâche est sauvegardée en premier
       Lesson savedLesson = lessonRepository.save(lesson);

        Document document = new Document();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        document.setName(fileName);
        document.setUrl(uploadDirectory + "/" + fileName);

        // Associez la tâche au document
        document.setLesson(savedLesson);

        // Enregistrez le document
        Document savedDocument = documentRepository.save(document);

        return savedDocument;
    }
}