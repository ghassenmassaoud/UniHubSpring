//package tn.esprit.pidevarctic.Controller;
//
//import lombok.AllArgsConstructor;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
////import tn.esprit.pidevarctic.Service.PostService;
//import tn.esprit.pidevarctic.entities.LikeAction;
//import tn.esprit.pidevarctic.entities.Post;
//import tn.esprit.pidevarctic.entities.Status;
//
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//@CrossOrigin(origins = "http://localhost:4200")
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/post")
//public class PostController {
//
//  //  private PostService postService;
// //   @Autowired est utilisé pour injecter PostService dans le contrôleur
//// private SentimentAnalysisService sentimentAnalysisService;
//
//
//    @DeleteMapping("/delete/{postId}")
//    public void deletePosts(@PathVariable Long postId) {
//        postService.deletePost(postId);
//    }
//
//
//    @PostMapping("/unmark-reported")
//    public void unmarkPostAsReported(@RequestParam Long postId){
//        postService.unmarkPostAsReported(postId);
//    }
//    @PostMapping("/mark-reported")
//    public void markPostAsReported(@RequestParam Long postId){
//        postService.markPostAsReported(postId);
//    }
//
//    @GetMapping("/getpost/{postId}")
//
//    public Post getByPostId(@PathVariable Long postId) {
//        return postService.getByPostId(postId);
//    }
//
//    @GetMapping("/getallposts")
//
//    public List<Post> getAll() {
//        return postService.getAll();
//    }
//    @PutMapping("/updatepost")
//    public Post updatePost(@ModelAttribute Post post,
//                           @RequestParam Long userId,
//                           @RequestParam Long postId,
//                           @RequestParam(value = "title") String title,
//                           @RequestParam(value = "content") String content,
//                           @RequestParam(value = "tags") List<String> tags,
//                           @RequestParam(value = "datePost") LocalDate datePost,
//                           @RequestParam(value = "likes") int likes,
//                           @RequestParam(value = "views") int views,
//                           @RequestParam(value = "status") Status status,
//                           @RequestParam(value = "report") boolean report
//                           ) throws IOException
//    {return  postService.updatepost(post, postId,userId);}
//
//
//    // correct addddd
//    @PostMapping("/addpost")
//    public ResponseEntity<Post> addPost(
//            @ModelAttribute Post post,
//            @RequestParam Long userId,
//            @RequestParam(value = "title" , required = false) String title,
//            @RequestParam(value = "content" , required = false) String content,
//            @RequestParam(value = "tags" , required = false) List<String> tags,
//            @RequestParam(value = "datePost" , required = false) LocalDate datePost,
//            @RequestParam(value = "likes" , required = false) Integer likes,
//            @RequestParam(value = "views" , required = false) Integer views,
//            @RequestParam(value = "status", required = false) Status status,
//            @RequestParam(value = "report" , required = false) boolean report) throws IOException {
//
//
//        Post savedPost = postService.addPost(post, userId);
//        return ResponseEntity.ok(savedPost);
//
//    }
//
//
//
////    @PostMapping("/upload/{postId}")
////    public ResponseEntity<Void> uploadAttachment(@ModelAttribute Post post, @RequestParam("file") List<MultipartFile> file) throws IOException {
////        postService.uploadAttachment(post, file);
////        return ResponseEntity.ok().build();
////    }
//
////
////    @GetMapping("/download/{postId}")
////    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long postId) {
////        return postService.downloadAttachment(postId);
////    }
//
//    @GetMapping("/filterposts")
//    public List<Post> filterPostsByTags(@RequestParam(name="tags") String tags){
//        List<Post> allPosts = postService.getAll();
//        return postService.filterTags(allPosts, tags);
//    }
//
//
////    @PostMapping("/sentimentpost/{studentId}")
////    public ResponseEntity<Post> addPosts(
////            @ModelAttribute Post post,
////            @PathVariable Long studentId,
////            @RequestParam(value = "title" , required = false) String title,
////            @RequestParam(value = "content" , required = false) String content,
////            @RequestParam(value = "tags" , required = false) List<String> tags,
////            @RequestParam(value = "datePost" , required = false) LocalDate datePost,
////            @RequestParam(value = "likes" , required = false) int likes,
////            @RequestParam(value = "views" , required = false) int views,
////            @RequestParam(value = "status" , required = false) Status status,
////            @RequestParam(value = "report" , required = false) boolean report) throws IOException {
////
////
////        Post savedPost = postService.addPost(post, studentId);
////        return ResponseEntity.ok(savedPost);
////
////    }
//    @PostMapping("/{postId}/{userId}")
//    public Post addPostAction(
//            @PathVariable Long postId,
//            @PathVariable Long userId,
//            @RequestParam LikeAction action
//    ) {
//        return postService.addPostAction(postId, userId, action);
//    }
//    @PostMapping("/add")
//    public ResponseEntity<String> addFavoritePost(@RequestParam Long userId, @RequestParam Long postId) {
//        try {
//            postService.addFavoritePost(userId, postId);
//            return ResponseEntity.ok("Post added to favorites successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @PostMapping("/remove")
//    public ResponseEntity<?> removeFromFavorites(@RequestParam Long userId, @RequestParam Long postId) {
//        try {
//            postService.removeFromFavorite(userId, postId);
//            return ResponseEntity.ok().body("{\"message\": \"Post removed to favorites successfully\"}");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//}
//
//
