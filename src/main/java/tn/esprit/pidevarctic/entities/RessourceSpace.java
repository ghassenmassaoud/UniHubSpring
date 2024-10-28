
package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class RessourceSpace implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long SpaceId;
    @Enumerated(EnumType.STRING)
    Speciality spaceType;
    @OneToMany(mappedBy = "ressourceSpace")
    @JsonIgnore
    Set<Ressource> ressources;

}
