package tn.esprit.pidevarctic.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.*;
import tn.esprit.pidevarctic.entities.*;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor

public class CommentService implements ICommentService{

    private CommentRepository commentRepository;


    private PostRepository postRepository;


    private UserRepository userRepository;
   // @Autowired

  //  private AttachCommentRepository attachCommentRepository;

    private CommentLikeRepository commentLikeRepository;

   // private WordChecker wordChecker;




    @Override
    public Comment addCommment(Comment comment, Long postId, Long studentId) throws IOException {
        Post posts = postRepository.findById(postId).orElse(null);
        User student = userRepository.findById(studentId).get();
        comment.setPost(posts);
        student.getComments().add(comment);
//        if (wordChecker.containsBadWords(comment.getContent())) {
//            comment.setReport(true);
//        }

        Comment savedComment = commentRepository.save(comment);
     //   uploadAttachment(comment,file);

        return savedComment;
    }
    @Override
    public Comment addReplyToComment(Comment parentComment, Comment replyComment,Long postId, Long studentId) throws IOException {

        Post posts = postRepository.findById(postId).orElse(null);
        User student = userRepository.findById(studentId).get();
        replyComment.setPost(posts);
        student.getComments().add(replyComment);

        if (parentComment == null || replyComment == null) {
            throw new IllegalArgumentException("Parent comment and reply comment cannot be null");
        }
        parentComment.setRepliesCount(parentComment.getRepliesCount() );

        parentComment.addReply(replyComment);
//        if (wordChecker.containsBadWords(replyComment.getContent())) {
//            replyComment.setReport(true);
//        }

        Comment savedReply = commentRepository.save(replyComment);
   //     uploadAttachment(replyComment, file);

        return savedReply;
    }






    @Override
    public ResponseEntity<String> deleteComment(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            comment.getCommentLikes().forEach(commentLikeRepository::delete);

//            if (!comment.getAttachement().isEmpty()) {
//                AttachComment attachment = comment.getAttachement().iterator().next();
//                attachCommentRepository.deleteById(attachment.getAttach_id());
//            }

            for (User user : userRepository.findByCommentsContains(comment)) {
                user.getComments().remove(comment);
            }
            commentRepository.deleteById(commentId);

            return ResponseEntity.ok("Comment, attachments, and associated likes deleted successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found.");
    }


    @Override
    public Comment getById(Long commentId) {
        Comment optionalComment = commentRepository.findById(commentId).orElse(null);

            return optionalComment;
        }




    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment, Long commentId,Long postId) throws IOException {
        Comment existcomment = commentRepository.findById(commentId).orElse(null) ;
        Post posts = postRepository.findById(postId).orElse(null);

        if (existcomment != null) {

            existcomment.setRepliesCount(comment.getRepliesCount());
            existcomment.setCommentDate(comment.getCommentDate());
            existcomment.setContent(comment.getContent());
            existcomment.setLikes(comment.getLikes());

            existcomment.setPost(posts);
            existcomment.setReport(comment.isReport());
        //    uploadAttachment(existcomment,file);

        }
       return commentRepository.save(existcomment);


    }

    public void markCommentAsReported(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setReport(true);
        commentRepository.save(comment);
    }
    public List<Comment> getCommentsForPost(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    public void unmarkCommentAsReported(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setReport(false);
        commentRepository.save(comment);
    }
    public Comment addCommenttAction(Long commentId, Long userId, LikeAction action) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CommentLike existingLike = null;
        for (CommentLike like : comment.getCommentLikes()) {
            if (like.getUser().equals(user)) {
                existingLike = like;
                break;
            }
        }

        if (existingLike != null) {
            if (existingLike.getAction() != action) {
                existingLike.setAction(action);
                commentLikeRepository.save(existingLike); // Enregistrer les modifications du like dans la base de données
            }
        } else {
            CommentLike newLike = new CommentLike();
            newLike.setUser(user);
            newLike.setComment(comment);
            newLike.setAction(action);
            commentLikeRepository.save(newLike); // Enregistrer le nouveau like dans la base de données
            comment.getCommentLikes().add(newLike); // Ajouter le like au post
            if (action != LikeAction.dislike) {
                comment.setLikes(comment.getLikes() + 1); // Incrémenter le nombre de likes sauf pour "dislike"
            } else {
                comment.setLikes(comment.getLikes() - 1); // Décrémenter le nombre de likes pour "dislike"
            }
        }

        commentRepository.save(comment); // Enregistrer les modifications du post dans la base de données

        return comment;
    }
}