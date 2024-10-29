package tn.esprit.pidevarctic.Controller;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.patterns.IVerificationRequired;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.pidevarctic.Service.IRessourceService;
import tn.esprit.pidevarctic.Service.IRessourceSpace;
import tn.esprit.pidevarctic.Service.IUserService;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;
import tn.esprit.pidevarctic.entities.Speciality;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class RessourceRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IRessourceService ressourceService;
    @Autowired
    private IRessourceSpace ressourceSpace;
    @Autowired
    private RessourceRestController ressourceRestController;
    @Autowired
    private IUserService userService;
    private Ressource ressource;


    @BeforeEach
    public void setup() {
        //Create resourceSpace
        RessourceSpace ressourceSpace1 = RessourceSpace.builder()
                .SpaceId(1L)
                .spaceType(Speciality.ARCTIC).build();
        RessourceSpace saveSpace = ressourceSpace.addSpace(ressourceSpace1);
        //Create resource
         ressource = Ressource.builder()
                .ressourceName("TD4")
                .ressourceType(RessourceType.SUBJECT)
                .description("Un TD de l'année 2020 de matiere DevOps")
                .ressourceSpace(ressourceSpace1)
                .fileName("5c241b41-888c-4950-89dc-4ee075021408_1.png")
                .build();
    }
    @Test

    void testAddRessource() {
        //Act
        Ressource savedResource = ressourceService.addRess(ressource,1L);
        //Assert
        assertNotNull(savedResource.getRessourceId());



    }

    @Test

    void updateRessource() {
        //Arrange
        Ressource ressource1 =Ressource.builder()
                .ressourceId(53L)
                .description("Not a TD anymore ")
                .build();
        //Act
        Ressource updated =ressourceService.updateRess(ressource1);
        assertNotNull(updated.getRessourceId());
    }

    @Test
    public void testUploadResource() throws Exception {
        // Act
        MockMultipartFile file = new MockMultipartFile(
                "file", // The name of the file parameter
                "testFile.txt", // The original file name
                "text/plain", // The content type
                "This is the content of the file.".getBytes() // The content of the file
        );
        // Here you should simulate an actual file upload if necessary
        Ressource ressource = new Ressource();
        ressource.setRessourceName("Uploaded Resource");

        ResponseEntity<Ressource> response = ressourceRestController.uploadResource(file, "testName", RessourceType.EXAMS, 1L, "description", 1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDownloadFile_Success() {
        // Arrange
        String fileName = "75809f2d-a93c-41bd-9ef8-af3c70af491f_1.png";

        // Act
        ResponseEntity<FileSystemResource> response = ressourceRestController.download(fileName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDownloadFile_Failed() {
        // Arrange
        String fileName = "nonexistent.txt";

        // Act
        ResponseEntity<FileSystemResource> response = ressourceRestController.download(fileName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testOpenFile() throws IOException {


        // Act
        ResponseEntity<ByteArrayResource> response = ressourceRestController.openFile(ressource.getFileName());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    
}