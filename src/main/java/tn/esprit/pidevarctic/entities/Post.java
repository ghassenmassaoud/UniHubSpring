package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder

public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long postId;
    String title;
    String content;
    @ElementCollection
    List<String> tags ;
    LocalDate datePost;



    int likes;
    int views;
    @Enumerated(EnumType.STRING)
    Status status;
    @ManyToOne
    //@JsonBackReference
    @JsonIgnore
    User student;
    @OneToMany(mappedBy = "post")
 //   @JsonManagedReference
    @JsonIgnore
    Set<Comment> comments;
    boolean report;


    @OneToMany(mappedBy = "post")
    @JsonIgnore
    //@JsonManagedReference
    Set<PostLike> postLikes = new HashSet<>();

    private float sentimentScore;
    private String emoji;
}
