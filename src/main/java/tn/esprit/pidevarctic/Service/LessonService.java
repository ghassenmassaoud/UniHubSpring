package tn.esprit.pidevarctic.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.LessonRepository;
import tn.esprit.pidevarctic.entities.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {
    private LessonRepository lessonRepository;
    private ClassroomRepository classroomRepository;
    private ClassroomService classroomService;
    private DocumentService documentService;
    //private UserRepository userRepository;
    private EmailService emailService;
    @Override

    public Lesson addLesson(String lessonName, Visibility visibility,Long classroom, MultipartFile file) throws IOException {
        Classroom classroom1 = classroomRepository.findById(classroom).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonName);
        lesson.setVisibility(visibility);
        lesson.setClassroom(classroom1);
        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForLesson(file,lesson);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
           lesson.setDocuments(documents);

        }
        List<User> students = new ArrayList<>(classroom1.getStudents());

        // Envoyer un e-mail à chaque étudiant
        for (User student : students) {
            String message = "Dear " + student.getFirstName() + ",\n\nA new Lesson has been assigned to your " + classroom1.getClassroomName() + ".";
            emailService.sendEmail(student.getEmail(), "New Lesson Added ", message);
        }
        return lessonRepository.save(lesson);
    }

    @Override
public ResponseEntity<?> updateLesson(String lessonName , Visibility visibility,Long lessonId, MultipartFile file) throws IOException {
    Lesson existingLesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

    existingLesson.setLessonName(lessonName);
   existingLesson.setVisibility(existingLesson.getVisibility());
        Classroom classroom = classroomService.getClassroomById(existingLesson.getClassroom().getIdClassroom());
    if (existingLesson.getClassroom() != null && existingLesson.getClassroom().getIdClassroom() != null) {

        if (classroom != null) {
            existingLesson.setClassroom(classroom);
        } else {
            return ResponseEntity.badRequest().body("Classroom with provided ID not found");
        }
    }

    if (file != null && !file.isEmpty()) {
        Document document = documentService.uploadFileForLesson(file, existingLesson);
        Set<Document> documents = new HashSet<>();
        documents.add(document);
        existingLesson.setDocuments(documents);
    }
        List<User> students = new ArrayList<>(classroom.getStudents());

        // Envoyer un e-mail à chaque étudiant
        for (User student : students) {
            String message = "Dear " + student.getFirstName() + ",\n\nA new Lesson has been assigned to your " + classroom.getClassroomName() + ".";
            emailService.sendEmail(student.getEmail(), "New Lesson Added ", message);
        }

    Lesson updatedLesson = lessonRepository.save(existingLesson);
    return ResponseEntity.ok(updatedLesson);
}


    @Transactional
    //ensures that both the document deletion and lesson deletion are treated as a single transaction. If either operation fails, the entire transaction is rolled back.
    @Override
    public void deleteLesson(Long idLesson) {
        // Delete associated documents first
        deleteDocumentsByLessonId(idLesson);
        // Then delete the lesson
        lessonRepository.deleteById(idLesson);
    }

    private void deleteDocumentsByLessonId(Long lessonId) {
        lessonRepository.deleteDocumentsByLessonId(lessonId);
    }

    @Override
    public Lesson getLessonById(Long idLesson) {
        return lessonRepository.findById(idLesson).orElse(null);
    }

    @Override
    public List<Lesson> getAllLesson() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> getLessonsByClassroom(Long classroomId) {
        return lessonRepository.findByClassroom_IdClassroom(classroomId);
    }

    public FileSystemResource downloadLessonDocument(String fileName) {
        try {
            return documentService.downloadFile(fileName);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found", e);
        }
    }


}
