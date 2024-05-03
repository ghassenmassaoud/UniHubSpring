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
public class Demand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long demandId;
    String description;
    @Enumerated(EnumType.STRING)
    DemandType demandType;
    @ManyToOne
    @JsonIgnore
    User student;
}
