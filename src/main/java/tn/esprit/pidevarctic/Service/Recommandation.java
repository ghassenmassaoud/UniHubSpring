package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.PostRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.Repository.postLikeRepository;
import tn.esprit.pidevarctic.entities.LikeAction;
import tn.esprit.pidevarctic.entities.Post;
import tn.esprit.pidevarctic.entities.PostLike;
import tn.esprit.pidevarctic.entities.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class Recommandation {

//
//    private PostRepository postRepository;
//    private UserRepository userRepository;
//    private postLikeRepository postLikeRepository;
//    private static final double seuilSimilarite = 0.5; // Exemple de seuil de similarité
//
//    private double calculateSimilarity(Post post1, Post post2) {
//        // Collectez les données pertinentes pour le calcul de similarité
//        int likesPost1 = post1.getLikes();
//        List<String> tagsPost1 = post1.getTags();
//
//        int likesPost2 = post2.getLikes();
//        List<String> tagsPost2 = post2.getTags();
//
//        // Normalisez les valeurs numériques (likes) pour les rendre comparables
//        double normLikesPost1 = (double) likesPost1 / (likesPost1 + 1); // +1 pour éviter la division par zéro
//        double normLikesPost2 = (double) likesPost2 / (likesPost2 + 1); // +1 pour éviter la division par zéro
//
//        // Calcul de la similarité cosinus pour les tags
//        double dotProduct = 0.0;
//        double normPost1 = 0.0;
//        double normPost2 = 0.0;
//
//        for (String tag : tagsPost1) {
//            if (tagsPost2.contains(tag)) {
//                dotProduct++;
//            }
//            normPost1++;
//        }
//
//        for (String tag : tagsPost2) {
//            normPost2++;
//        }
//
//        double similarity = dotProduct / (Math.sqrt(normPost1) * Math.sqrt(normPost2));
//
//        return similarity;
//    }
//    public List<Post> generateRecommendations(User user) {
//        List<Post> allPosts = postRepository.findAll();
//        List<Post> recommendations = new ArrayList<>();
//        for (Post post : allPosts) {
//            // Vérifier si l'utilisateur a interagi avec le post actuel
//            System.out.println("heloooo"+ hasUserLikedPost(user, post));
//            if (hasUserLikedPost(user, post)) {
//                // Calculer la similarité entre le post actuel et les posts de la liste
//                double similarity = calculateSimilarity(user.getFavoritePost(), post);
//                // Ajouter le post à la liste des recommandations si la similarité est élevée
//                if (similarity >= seuilSimilarite) {
//                    recommendations.add(post);
//                }
//            }
//        }
//        return recommendations;
//    }
//
//    private boolean hasUserLikedPost(User user, Post post) {
//        for (PostLike postLike : post.getPostLikes()) {
//            if (postLike.getUser().equals(user) && postLike.getAction() == LikeAction.like) {
//                return true;
//            }
//        }
//        return false;
//    }


    private PostRepository postRepository;
    private UserRepository userRepository;
    private UserService userService;
    private postLikeRepository postLikeRepository; // Correction du nom du repository
    private static final double seuilSimilarite = 0.5; // Exemple de seuil de similarité

    private double calculateSimilarity(Post post1, Post post2) {
        // Collectez les données pertinentes pour le calcul de similarité
        int likesPost1 = post1.getLikes();
        List<String> tagsPost1 = post1.getTags();

        int likesPost2 = post2.getLikes();
        List<String> tagsPost2 = post2.getTags();

        // Normalisez les valeurs numériques (likes) pour les rendre comparables
        double normLikesPost1 = (double) likesPost1 / (likesPost1 + 1); // +1 pour éviter la division par zéro
        double normLikesPost2 = (double) likesPost2 / (likesPost2 + 1); // +1 pour éviter la division par zéro

        // Calcul de la similarité cosinus pour les tags
        double dotProduct = 0.0;
        double normPost1 = 0.0;
        double normPost2 = 0.0;

        for (String tag : tagsPost1) {
            if (tagsPost2.contains(tag)) {
                dotProduct++;
            }
            normPost1++;
        }

        for (String tag : tagsPost2) {
            normPost2++;
        }

        double similarity = dotProduct / (Math.sqrt(normPost1) * Math.sqrt(normPost2));

        return similarity;
    }

    public List<Post> generateRecommendations(User user) {
        List<Post> allPosts = postRepository.findAll();
        List<Post> recommendations = new ArrayList<>();
        List<Post> favoritePosts = userService.getUserFavoritePosts(user);
        List<String> userLikedTags = new ArrayList<>();

        for (Post favoritePost : favoritePosts) {
            List<String> favoritePostTags = favoritePost.getTags();
            userLikedTags.addAll(favoritePostTags);

            for (Post post : allPosts) {
                if (hasUserLikedPost(user, post) || hasSimilarTag(post.getTags(), userLikedTags)) {
                    double similarity = calculateSimilarity(favoritePost, post);
                    if (similarity >= seuilSimilarite) { // Utilisation du seuil de similarité
                        recommendations.add(post);
                    }
                }
            }
        }

        Set<Post> uniqueRecommendations = new HashSet<>(recommendations);
        recommendations.clear();
        recommendations.addAll(uniqueRecommendations);

        return recommendations;
    }

    private boolean hasSimilarTag(List<String> postTags, List<String> userLikedTags) {
        for (String tag : postTags) {
            if (userLikedTags.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasUserLikedPost(User user, Post post) {
        for (PostLike postLike : post.getPostLikes()) {
            if (postLike.getUser().equals(user) && postLike.getAction() == LikeAction.like) {
                return true;
            }
        }
        return false;
    }
}

