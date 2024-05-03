package tn.esprit.pidevarctic.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

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
    @ManyToMany
    Set<User> students;
    @OneToMany(mappedBy = "ressourceSpace")
    Set<Ressource> ressources;

}
