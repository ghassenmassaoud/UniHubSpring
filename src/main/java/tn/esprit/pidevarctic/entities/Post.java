package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;
    String content;
    LocalDate datePost;
    @ManyToOne
    @JsonIgnore
    User student;
    @OneToMany(mappedBy = "post")
    Set<Comment> comments;
}
