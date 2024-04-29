package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.ClassroomRepository;
import tn.esprit.pidevarctic.Repository.LessonRepository;
import tn.esprit.pidevarctic.entities.Classroom;
import tn.esprit.pidevarctic.entities.Document;
import tn.esprit.pidevarctic.entities.Lesson;

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

    public Lesson addLesson(Lesson lesson , Long classroom, MultipartFile file) throws IOException {
        Classroom classroom1 = classroomRepository.findById(classroom).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        lesson.setClassroom(classroom1);
        if (file != null && !file.isEmpty()) {
            Document document = documentService.uploadFileForLesson(file,lesson);
            Set<Document> documents = new HashSet<>();
            documents.add(document);
           lesson.setDocuments(documents);

        }
        return lessonRepository.save(lesson);
    }
    @Override
    public ResponseEntity<?> updateLesson(Lesson lesson , Long lessonId) {
  Lesson lesson1 =lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        if (lesson1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found");
        }

        lesson1.setLessonName(lesson.getLessonName());
        lesson1.setVisibility(lesson.getVisibility());

        if (lesson.getClassroom() != null && lesson.getClassroom().getIdClassroom() != null) {
            Classroom classroom = classroomService.getClassroomById(lesson.getClassroom().getIdClassroom());
            if (classroom != null) {

                lesson1.setClassroom(classroom);
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom with provided ID not found");
            }
        }

        lesson1.setTasks(lesson.getTasks());
        Lesson updatedLesson = lessonRepository.save(lesson1);
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

//
//    public InputStream downloadLesson(Long lessonId) {
//        Lesson lesson = lessonRepository.findById(lessonId)
//                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
//
//        if (lesson == null || lesson.getDocument() == null) {
//            throw new IllegalArgumentException("Lesson or document not found");
//        }
//
//        String fileUrl = lesson.getDocument().getUrl();
//
//        try {
//            return downloadFile(fileUrl);
//        } catch (FileNotFoundException e) {
//            throw new IllegalArgumentException("File not found");
//        }
//    }
//
//    private InputStream downloadFile(String fileUrl) throws FileNotFoundException {
//        Path filePath = Paths.get(fileUrl);
//        File file = filePath.toFile();
//        if (!file.exists()) {
//            throw new FileNotFoundException("Le fichier spécifié n'existe pas.");
//        }
//        return new FileInputStream(file);
//    }


}
