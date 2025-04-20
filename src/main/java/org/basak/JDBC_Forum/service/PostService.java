package org.basak.JDBC_Forum.service;
import org.basak.JDBC_Forum.entity.Post;
import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.repository.PostRepository;
import org.basak.JDBC_Forum.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService() {
        this.postRepository = new PostRepository();
        this.userRepository = new UserRepository();
    }
    public void showByUser(String username){
        Optional<User> user =userRepository.findByUsername(username);
        List<Post> posts = postRepository.findAllByUserId(user.get().getId());
        for (Post post : posts) {
            System.out.println("Title: "+post.getTitle()+" Content: "+ post.getContent());
        }
    }
    public void showAllPosts() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            Optional<User> user= userRepository.findById(post.getUserId());
            System.out.println("Username: "+user.get().getUsername()+" Title: "+post.getTitle()+" Content: "+ post.getContent());
        }
    }
    public void sharePosts(Post post) {
        postRepository.save(post);
    }
    public void showYourPosts(Long id) {
        List<Post> posts = postRepository.findAllByUserId(id);
        for (Post post : posts) {
            System.out.println("Title: "+post.getTitle()+" Content: "+ post.getContent());
        }
    }
    public String updatePost(Post post, String oldTitle) {
        List<Post> posts = postRepository.findPostbyUserIdAndTitle(post.getUserId(),oldTitle);
        if(posts.size()==0){
            Post firstPost = posts.get(0);
            firstPost.setTitle(post.getTitle());
            firstPost.setContent(post.getContent());
            postRepository.update(firstPost);
            return "Güncelleme işlemi başarılı";
        }
        else{
            return "Güncellenecek kayıt bulunamadı";
        }
    }

    public String deletePost(String title, Long userId) {
        List<Post> posts = postRepository.findPostbyUserIdAndTitle(userId, title);
        if (posts.size() > 0) {
            Post post = posts.get(0);
            postRepository.deleteById(post.getId());
            return "Silme işlemi başarılı";
        } else {
            return "Post bilgisi bulunamadı";
        }
    }

    public void listUsers() {
        List<Post> posts = postRepository.findAll();
        List<Long> uniqueUserIds = posts.stream()
                .map(Post::getUserId)
                .distinct()
                .collect(Collectors.toList());
        for (Long userId : uniqueUserIds) {
            System.out.println(userRepository.findById(userId).get().getUsername());
        }
    }
}
