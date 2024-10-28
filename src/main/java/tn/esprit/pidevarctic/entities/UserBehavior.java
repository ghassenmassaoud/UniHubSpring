
package tn.esprit.pidevarctic.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserBehavior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "timestamp")
    LocalDateTime timestamp;
    @Column(name = "resource_id")
    Long resourceId;
    String section;
    int visited;

}
