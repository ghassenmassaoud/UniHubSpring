package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.RessourceRepository;
import tn.esprit.pidevarctic.Repository.RessourceSpaceRepository;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;
import tn.esprit.pidevarctic.entities.Speciality;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ResourceService implements IRessourceService {

    private RessourceRepository ressourceRepository;
    private RessourceSpaceRepository ressourceSpaceRepository;

    @Override
    public Ressource addRess(Ressource ressource,Long RessourceSpaceid) {
        RessourceSpace ressourceSpace = ressourceSpaceRepository.findById(RessourceSpaceid).orElse(null);
        if (ressourceSpace != null) {
            ressource.setRessourceSpace(ressourceSpace);
            return ressourceRepository.save(ressource);
        } else {
            // Handle case where the provided RessourceSpace identifier is invalid
            throw new IllegalArgumentException("Invalid RessourceSpace identifier");
        }
    }
    @Override
    public Ressource updateRess(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }
    @Override
    public void deleteRessource(Long resId) {
        ressourceRepository.deleteById(resId);
    }
    @Override
    public Ressource getRessourceById(Long resId) {
        return ressourceRepository.findById(resId).orElse(null);
    }
    @Override
    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    public List<Ressource> getByType(RessourceType resourceType){
        return ressourceRepository.getRessourceByRessourceType(resourceType);
    }
    @Override
    public List<Ressource> getBySpace(RessourceSpace spaceId) {
        return ressourceRepository.getRessourceByRessourceSpace(spaceId);
    }
    public Ressource uploadResource(MultipartFile file, String resourceName, RessourceType resourceType, Long resourceSpaceId) throws IOException {
    RessourceSpace resourceSpace = ressourceSpaceRepository.findById(resourceSpaceId).orElse(null);
    if (resourceSpace != null) {
        // Generate a unique file name
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Save the file locally
        Path filePath = Paths.get(System.getProperty("user.dir") + "/src/main/Files", uniqueFileName); // Specify your upload directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Create a new resource
        Ressource resource = new Ressource();
        resource.setRessourceName(resourceName);
        resource.setRessourceType(resourceType);
        resource.setRessourceSpace(resourceSpace);
        resource.setFileName(uniqueFileName);

        return ressourceRepository.save(resource);
    } else {
        throw new IllegalArgumentException("Invalid ResourceSpace identifier");
    }
    }
    public FileSystemResource downloadFile(String fileName) {
        String filePath = System.getProperty("user.dir") + "/src/main/Files/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            throw new IllegalArgumentException("Le fichier demandé n'existe pas : " + fileName);
        }
    }


    public byte[] getFileContent(String fileName) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir"), "src/main/Files", fileName);
        return Files.readAllBytes(filePath);
    }





}
