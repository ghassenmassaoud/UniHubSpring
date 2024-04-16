package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

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
    @Enumerated(EnumType.STRING)
    RessourceType ressourceType;
    @ManyToOne
    @JsonIgnore
    RessourceSpace ressourceSpace;
}
