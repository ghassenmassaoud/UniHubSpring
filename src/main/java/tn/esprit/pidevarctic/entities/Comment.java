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
@Builder
@JsonIgnoreProperties(value = { "post" }, allowSetters = true)


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
    String content;
    LocalDate commentDate;
    int likes;
    @OneToMany(mappedBy = "comment")
    Set<CommentLike> commentLikes = new HashSet<>();

    @ManyToOne

    Post post;

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
