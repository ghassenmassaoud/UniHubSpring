package tn.esprit.pidevarctic.Service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Comment;

import java.io.IOException;
import java.util.List;

public interface ICommentService {
    Comment addCommment(Comment comment,Long postId, Long studentId) throws IOException;
   // ResponseEntity<String> deleteComment(Long commentId,Long attachId);
    Comment getById(Long commentId);
    ResponseEntity<String> deleteComment(Long commentId);
    List<Comment> getAll();
   // ResponseEntity<Resource> downloadAttachment(Long commentId);
    Comment updateComment(Comment comment, Long commentId,Long postId) throws IOException;
  //  void updateReactionToComment(Long commentId, React newReaction);
  void markCommentAsReported(Long commentId);
    void unmarkCommentAsReported(Long commentId);
    Comment addReplyToComment(Comment parentComment, Comment replyComment,Long postId, Long studentId) throws IOException;
}
