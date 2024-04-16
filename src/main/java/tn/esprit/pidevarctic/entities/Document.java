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
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDoc;
    String name;
    String url;
    @Lob
    @Column(length = 800000000)
    byte[]data;
    @ManyToOne
    @JsonIgnore
    Task task;
    @ManyToOne
    @JsonIgnore
    Lesson lesson;
}
