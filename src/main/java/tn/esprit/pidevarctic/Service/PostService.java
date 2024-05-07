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
public class PostService implements IPostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private postLikeRepository postLikeRepository;

    private WordChecker wordChecker;

    private SentimentAnalysisService sentimentAnalysisService;


    @Override
    @Transactional
    //rollback : effectur plusieurs operations pour qu il s assuse qu il a fait tout le commit
    public Post addPost(Post post, Long studentId) throws IOException {
        User student = userRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        post.setStudent(student);
        float sentimentScore = sentimentAnalysisService.analyzeSentiment(post.getContent());
        String emoji = sentimentAnalysisService.getEmoji(sentimentScore);
        post.setEmoji(emoji);
        post.setSentimentScore(sentimentScore);
        List<String> processedTags = new ArrayList<>();
        for (String tag : post.getTags()) {
            if (!tag.matches("^#\\w+$")) {
                throw new IllegalArgumentException("Invalid tag format: " + tag);
            } else {
                processedTags.add(tag);
            }
        }
        post.setTags(processedTags);
        if (wordChecker.containsBadWords(post.getContent())) {
            post.setStatus(Status.pending);
            post.setReport(true);
        }
        if (wordChecker.containsBadWords(post.getTitle())) {
            post.setStatus(Status.pending);
            post.setReport(true);

        }


        Post savedPost = postRepository.save(post);

        //  uploadAttachment(savedPost, files);
        return savedPost;
    }

    public void addFavoritePost(Long userId, Long postId) {
        // Récupérer l'utilisateur et le post à partir de leurs IDs
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Ajouter le post en tant que favori pour l'utilisateur
        user.getFavoritePosts().add(post);
        userRepository.save(user);
    }

    public List<Post> filterTags(List<Post> posts, String tags) {
        List<Post> filteredPosts = new ArrayList<>();
        for (Post post : posts) {
            for (String tag : post.getTags()) {
                if (tag.contains(tags)) {
                    filteredPosts.add(post);
                    break; // Sortir de la boucle dès qu'un tag est trouvé
                }
            }
        }
        return filteredPosts;
    }


    public Post getByPostId(Long postId) {
        Post optionalPost = postRepository.findById(postId).orElse(null);

        return optionalPost;
    }


    @Override
    public List<Post> getAll() {
        return postRepository.findAll();

    }

    @Override
    public Post updatepost(Post post, Long postId, Long studentId) throws IOException {
        User existuser = userRepository.findById(studentId).orElse(null);
        Post existpost = postRepository.findById(postId).orElse(null);
        if (existpost != null) {
            existpost.setDatePost(post.getDatePost());
            existpost.setLikes(post.getLikes());
            existpost.setTitle(post.getTitle());
            existpost.setContent(post.getContent());
            existpost.setReport(post.isReport());
            existpost.setViews(post.getViews());
            existpost.setStatus(post.getStatus());
            existpost.setTags(post.getTags());

            //  uploadAttachment(existpost,file);

            existpost.setComments(post.getComments());
            existpost.setStudent(existuser);

        }


        return postRepository.save(existpost);
    }


    @Override
    public ResponseEntity<String> deletePost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            post.getPostLikes().forEach(postLikeRepository::delete);
//
//            if (!post.getAttachment().isEmpty()) {
//                Attachement attachment = post.getAttachment().iterator().next();
//                attachementRepository.deleteById(attachment.getIdAttachment());
//            }


            postRepository.deleteById(postId);

            return ResponseEntity.ok("Comment, attachments, and associated likes deleted successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found.");
    }
    public void removeFromFavorite(Long userId, Long postId) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (user.getFavoritePosts().contains(post)) {
            user.getFavoritePosts().remove(post);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Post is not in favorites");
        }
    }

    //untested
    public void markPostAsReported(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setReport(true);
        postRepository.save(post);
    }

    public void unmarkPostAsReported(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setReport(false);
        postRepository.save(post);
    }

    public void setPostPending(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        if (post.getStatus() == Status.draft || post.getStatus() == Status.published) {
            post.setStatus(Status.pending);
            postRepository.save(post);
        } else {
            throw new IllegalStateException("Cannot set post to pending. Current status is not draft or published.");
        }
    }

    public void publishPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        if (post.getStatus() == Status.draft) {
            post.setStatus(Status.published);
            postRepository.save(post);
        } else {
            throw new IllegalStateException("Cannot publish post. Current status is not draft.");
        }
    }


    public Post addPostAction(Long postId, Long userId, LikeAction action) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostLike existingLike = null;
        for (PostLike like : post.getPostLikes()) {
            if (like.getUser().equals(user)) {
                existingLike = like;
                break;
            }
        }

        if (existingLike != null) {
            if (existingLike.getAction() != action) {
                existingLike.setAction(action);
                postLikeRepository.save(existingLike); // Enregistrer les modifications du like dans la base de données
            }
        } else {
            PostLike newLike = new PostLike();
            newLike.setUser(user);
            newLike.setPost(post);
            newLike.setAction(action);
            postLikeRepository.save(newLike); // Enregistrer le nouveau like dans la base de données
            post.getPostLikes().add(newLike); // Ajouter le like au post
            if (action != LikeAction.dislike) {
                post.setLikes(post.getLikes() + 1); // Incrémenter le nombre de likes sauf pour "dislike"
            } else {
                post.setLikes(post.getLikes() - 1); // Décrémenter le nombre de likes pour "dislike"
            }
        }

        postRepository.save(post); // Enregistrer les modifications du post dans la base de données

        return post;
    }
}