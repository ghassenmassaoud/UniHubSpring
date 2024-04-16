package tn.esprit.pidevarctic.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInteractionRequest {

        private Long userId;
        private String actionType;
        private Long resourceId;
        private LocalDateTime date;



}
