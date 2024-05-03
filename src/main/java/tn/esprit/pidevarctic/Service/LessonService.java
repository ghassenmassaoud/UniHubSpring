package tn.esprit.pidevarctic.Service;

import jakarta.persistence.EntityNotFoundException;
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
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Document;
import tn.esprit.pidevarctic.entities.Lesson;
import tn.esprit.pidevarctic.entities.Visibility;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return lessonRepository.save(lesson);
    }
//    @Override
//    public ResponseEntity<?> updateLesson(Lesson lesson , Long lessonId) {
//  Lesson lesson1 =lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
//        if (lesson1 == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found");
//        }
//
//        lesson1.setLessonName(lesson.getLessonName());
//        lesson1.setVisibility(lesson.getVisibility());
//
//        if (lesson.getClassroom() != null && lesson.getClassroom().getIdClassroom() != null) {
//            Classroom classroom = classroomService.getClassroomById(lesson.getClassroom().getIdClassroom());
//            if (classroom != null) {
//
//                lesson1.setClassroom(classroom);
//            } else {
//
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom with provided ID not found");
//            }
//        }
//
//        lesson1.setTasks(lesson.getTasks());
//        Lesson updatedLesson = lessonRepository.save(lesson1);
//        return ResponseEntity.ok(updatedLesson);
//    }
    @Override
public ResponseEntity<?> updateLesson(String lessonName ,  Long lessonId, MultipartFile file) throws IOException {
    Lesson existingLesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

    existingLesson.setLessonName(lessonName);
//    existingLesson.setVisibility(lesson.getVisibility());

    if (existingLesson.getClassroom() != null && existingLesson.getClassroom().getIdClassroom() != null) {
        Classroom classroom = classroomService.getClassroomById(existingLesson.getClassroom().getIdClassroom());
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

    Lesson updatedLesson = lessonRepository.save(existingLesson);
    return ResponseEntity.ok(updatedLesson);
}


    @Override
    public void deleteLesson(Long idLesson) {
        lessonRepository.deleteById(idLesson);
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
