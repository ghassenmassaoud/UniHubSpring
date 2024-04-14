package tn.esprit.pidevarctic.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.DocumentRepository;
import tn.esprit.pidevarctic.entities.Document;
import org.springframework.beans.factory.annotation.Autowired;


@Service
//@AllArgsConstructor
public class DocumentService implements IDocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public void uploadFile(MultipartFile file) throws IOException {
        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setUrl("uploads/" + document.getName());

        // Sauvegarder le document dans la base de données
        document = documentRepository.save(document);

        // Créer le répertoire uploads s'il n'existe pas
        try {
            Files.createDirectories(Paths.get("uploads"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create uploads directory", e);
        }

        // Sauvegarder le fichier sur le système de fichiers
        Path filePath = Paths.get("uploads/" + document.getName());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }
}

