package tn.esprit.pidevarctic.Service;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Post;

import java.io.IOException;
import java.util.List;

public interface IPostService {

    //Post addPost(Post post, Long studentId);
    Post addPost(Post post, Long studentId) throws IOException;
   // ResponseEntity<String> deletePost(Long postId);
   ResponseEntity<String> deletePost(Long postId);

    List<Post> filterTags(List<Post> posts, String tags);
    Post getByPostId(Long postId);
    void markPostAsReported(Long postId);
    void unmarkPostAsReported(Long postId);
    void setPostPending(Long postId);
    void publishPost(Long postId);
    List<Post> getAll();
    Post updatepost(Post post, Long postId, Long studentId)throws IOException;
   // ResponseEntity<Resource> downloadAttachment(Long postId);
}
