package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Service.IRessourceService;
import tn.esprit.pidevarctic.entities.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/ressource")
@AllArgsConstructor
@RestController
public class RessourceRestController {



    private IRessourceService ressourceService;
    @PostMapping("/add")
    public Ressource addRessource(@RequestBody Ressource ressource){
        return ressourceService.addRess(ressource);
    }

    @PutMapping("/update")
    public Ressource updateRessource(@RequestBody Ressource ressource){
        return ressourceService.updateRess(ressource);
    }
    @GetMapping("/get/{ressourceId}")
    public Ressource getById (@PathVariable Long ressourceId){
        return ressourceService.getRessourceById(ressourceId);
    }

    @DeleteMapping("/Delete/{ressourceId}")
    public void removeRessource(@PathVariable Long ressourceId){

        ressourceService.deleteRessource(ressourceId);
    }

    @GetMapping("/All")
    public List<Ressource> getAll(){
        return ressourceService.getAllRessources();
    }
    @GetMapping("/showByType/{type}")
    public List<Ressource> getRessourceByType(@PathVariable String type){
        RessourceType ressourceType = RessourceType.valueOf(type.toUpperCase());
        return ressourceService.getByType(ressourceType);
    }
    @GetMapping("/showBySpace/{spaceName}")
    public List<Ressource> getBySpace(@PathVariable String spaceName){
        Speciality spaceNamee = Speciality.valueOf(spaceName.toUpperCase());
        return ressourceService.getBySpace(spaceNamee);
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // Process the file
        // Example: fileService.storeFile(file);
        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    @PostMapping("/upload")
    public ResponseEntity<Ressource> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String resourceName,
            @RequestParam("type") RessourceType resourceType,
            @RequestParam("spaceId") Long spaceId
    ) {
        try {
            Ressource savedResource = ressourceService.uploadResource(file, resourceName, resourceType, spaceId);
            return ResponseEntity.ok(savedResource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/download/{resId}")
    public ResponseEntity<byte[]> downloadResource(@PathVariable Long resId) {
        try {
            logUserInteraction("download", resId);
            byte[] fileData = ressourceService.downloadResource(resId);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"resource-" + resId + ".bin\"")
                    .body(fileData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
    }
    private void logUserInteraction(String actionType, Long resourceId) {
        try {

            Long userId = null;


            RestTemplate restTemplate = new RestTemplate();

            UserInteractionRequest request = new UserInteractionRequest();
            request.setUserId(userId);
            request.setActionType(actionType);
            request.setResourceId(resourceId);
            request.setDate(LocalDateTime.now());
            String baseUrl = "http://localhost:8081/api/api/user-interactions"; // Replace with your API base URL



            // Call the logUserInteraction endpoint to log the user interaction
            ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, request, Void.class);
            if (response.getStatusCode() != HttpStatus.CREATED) {
                throw new RuntimeException("Failed to log user interaction");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error while logging user interaction (e.g., log error, return error response)
        }


}
}
