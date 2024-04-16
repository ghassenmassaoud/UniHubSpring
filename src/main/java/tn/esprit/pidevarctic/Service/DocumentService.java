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

    public Document uploadFile(MultipartFile file) throws IOException {
        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setUrl("uploads/" + document.getName());
        document.setData(file.getBytes());
        return documentRepository.save(document);
    }
}

