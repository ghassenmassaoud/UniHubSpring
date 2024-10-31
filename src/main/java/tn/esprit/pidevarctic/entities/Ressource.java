
package tn.esprit.pidevarctic.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Ressource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ressourceId;
    String ressourceName;
    @Column(length = 255)
    String fileName;
    @Enumerated(EnumType.STRING)
    RessourceType ressourceType;
    @ManyToOne
    RessourceSpace ressourceSpace;
    String description;
    @ManyToOne
    User students;

}
