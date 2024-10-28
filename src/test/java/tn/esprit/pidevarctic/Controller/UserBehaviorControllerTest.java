package tn.esprit.pidevarctic.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.pidevarctic.Service.IRessourceService;
import tn.esprit.pidevarctic.Service.IUserbehavior;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.Speciality;
import tn.esprit.pidevarctic.entities.UserBehavior;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserBehaviorControllerTest {

    @Mock
    private IRessourceService ressourceService;

    @Mock
    private IUserbehavior userBehaviorService;

    @InjectMocks
    private UserBehaviorController userBehaviorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testGetAll() {
        // Arrange
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setResourceId(1L);
        userBehavior.setVisited(1);
        userBehavior.setTimestamp(LocalDateTime.now());
        userBehavior.setSection("Some Section");

        when(userBehaviorService.getAll()).thenReturn(Collections.singletonList(userBehavior));

        // Act
        List<UserBehavior> result = userBehaviorController.getall();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userBehavior.getResourceId(), result.get(0).getResourceId());
    }

    @Test
    void testSetCookie() {
        // Arrange
        HttpServletResponse response = mock(HttpServletResponse.class);
        Long resourceId = 1L;

        // Act
        ResponseEntity<?> result = userBehaviorController.setCookie(response, resourceId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        Cookie expectedCookie = new Cookie("ResourceId_" + resourceId, String.valueOf(resourceId));
        expectedCookie.setPath("/");
        expectedCookie.setDomain("localhost");
        verify(response, times(1)).addCookie(expectedCookie);
    }

    @Test
    void testUserBehaviour_ValidCookie() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie cookie = new Cookie("ResourceId_1", "1");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        RessourceSpace space=new RessourceSpace();
        space.setSpaceId(1L);
        space.setSpaceType(Speciality.ARCTIC);
        Ressource resource = new Ressource();
        resource.setRessourceSpace(space);
        when(ressourceService.getRessourceById(anyLong())).thenReturn(resource);

        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setResourceId(1L);
        userBehavior.setVisited(1);
        when(userBehaviorService.getByResource(anyLong())).thenReturn(userBehavior);

        // Act
        ResponseEntity<Void> result = userBehaviorController.userBehaviour(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userBehaviorService, times(1)).logUserBehavior(any(UserBehavior.class));
        assertEquals(2, userBehavior.getVisited()); // Verify that the visited count is incremented
    }

    @Test
    void testUserBehaviour_InvalidCookie() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie invalidCookie = new Cookie("ResourceId_1", "invalid");
        when(request.getCookies()).thenReturn(new Cookie[]{invalidCookie});

        // Act
        ResponseEntity<Void> result = userBehaviorController.userBehaviour(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode()); // Should still return OK, just ignoring invalid cookie
        verify(userBehaviorService, never()).logUserBehavior(any());
    }

    @Test
    void testGetPopulaireResource() {
        // Arrange
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setResourceId(1L);
        when(userBehaviorService.findtop()).thenReturn(Collections.singletonList(userBehavior));

        Ressource resource = new Ressource();
        resource.setRessourceId(1L);
        when(ressourceService.getRessourceById(1L)).thenReturn(resource);

        // Act
        List<Ressource> result = userBehaviorController.getPopulaireResource();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(resource.getRessourceId(), result.get(0).getRessourceId());
    }
}
