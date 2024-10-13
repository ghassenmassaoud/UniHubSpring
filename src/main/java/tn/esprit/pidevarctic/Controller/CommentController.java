package tn.esprit.pidevarctic.Controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Repository.CommentRepository;
import tn.esprit.pidevarctic.Service.CommentService;
import tn.esprit.pidevarctic.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;
    @PostMapping("/addcomment")

    public Comment addComment(@ModelAttribute Comment comment,
                              @RequestParam Long postId,
                              @RequestParam Long userId,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "repliesCount") Long repliesCount,
                              @RequestParam(value = "commentDate") LocalDate commentDate,
                              @RequestParam(value = "likes") Integer likes,
                             // @RequestParam(value = "react") React react,
                              @RequestParam(value = "report") boolean report) throws IOException
    { return commentService.addCommment(comment,postId,userId);}



    @PostMapping("/addreply")
    public Comment addReplyToComment(@RequestParam("parentId") Long parentId,
                                     @ModelAttribute Comment replyComment,
                                     @RequestParam Long postId,
                                     @RequestParam Long userId,
                                     @RequestParam("content") String content,
                                     @RequestParam("repliesCount") Long repliesCount,
                                     @RequestParam("commentDate") LocalDate commentDate,
                                     @RequestParam("likes") int likes,
                                     @RequestParam("report") boolean report) throws IOException {
        Comment parentComment = commentService.getById(parentId);
        return commentService.addReplyToComment(parentComment, replyComment,postId,userId);
    }

    @DeleteMapping("/deletecomment")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) { return commentService.deleteComment(commentId);}
    @GetMapping("/getcomment")
    public Comment getById(@RequestParam Long commentId) { return commentService.getById(commentId);}
    @GetMapping("/getallcomments")
    public List<Comment> getAll() { return commentService.getAll();}

    @PutMapping("/updatecomment")
    public Comment updateComment(@ModelAttribute Comment comment,
                                 @RequestParam Long commentId,
                                 @RequestParam Long postId,
                                 @RequestParam(value = "content") String content,
                                 @RequestParam(value = "repliesCount",required = false) Long repliesCount,
                                 @RequestParam(value = "commentDate") LocalDate commentDate,
                                 @RequestParam(value = "likes") int likes,
                                 //@RequestParam(value = "react") React react,
                                 @RequestParam(value = "report") boolean report) throws IOException
    { return commentService.updateComment(comment,commentId,postId);}

//    @GetMapping("/download/{commentId}")
//    public ResponseEntity<Resource> download(@PathVariable Long commentId){
//        return commentService.downloadAttachment(commentId);
//    }


    @GetMapping("/comments")
    public List<Comment> getCommentsForPost(@RequestParam Long postId) {
        return commentService.getCommentsForPost(postId);
    }

    @PostMapping("/unmark-reported")
    public void unmarkCommentAsReported(@RequestParam Long commentId){
        commentService.unmarkCommentAsReported(commentId);
    }
    @PostMapping("/mark-reported")
    public void markCommentAsReported(@RequestParam Long commentId){
        commentService.markCommentAsReported(commentId);
    }
@PostMapping("/{commentId}/{userId}")
public Comment addCommentAction(
        @PathVariable Long commentId,
        @PathVariable Long userId,
        @RequestParam LikeAction action
) {
    return commentService.addCommenttAction(commentId, userId, action);
}
}
