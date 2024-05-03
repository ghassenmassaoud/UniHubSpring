package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Absence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAbsence;
    @Enumerated(EnumType.STRING)
    StatusAbsence statusAbsence;
    LocalDate dateAbsence;
    @ManyToOne
    @JsonIgnore
    Classroom classroom;
    @ManyToOne
    @JsonIgnore
    Profile profile;
    @ManyToOne
    @JsonIgnore
    User user;

}
