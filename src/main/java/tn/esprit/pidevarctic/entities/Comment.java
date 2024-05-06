package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
//@JsonIgnoreProperties(value = { "post" }, allowSetters = true)


public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;
    String content;
    LocalDate commentDate;
    @ManyToOne
    @JsonIgnore
    Post post;
    Long repliesCount;


    @OneToMany(mappedBy = "parentComment",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> replies = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    Comment parentComment;

    int likes;
    @OneToMany(mappedBy = "comment")
    Set<CommentLike> commentLikes = new HashSet<>();





    boolean report;


    public void addReply(Comment reply) {
        replies.add(reply);
        reply.setParentComment(this);
        repliesCount++;
    }

    public void removeReply(Comment reply) {
        replies.remove(reply);
        reply.setParentComment(null);
        repliesCount--;
    }
}
