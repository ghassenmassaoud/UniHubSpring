package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    boolean status = false;
    String description;
    LocalDateTime creationDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    DemandType demandType;
    @ManyToOne
    @JsonIgnore
    User student;

    }


