package org.basak.JDBC_Forum.controller;
import org.basak.JDBC_Forum.entity.Post;
import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.service.PostService;
import org.basak.JDBC_Forum.service.UserService;

public class PostController extends BaseController {
    private final PostService postService;
    private final UserService userService;
    static MainController main = new MainController();
    public PostController() {
        this.postService=new PostService();
        this.userService = new UserService();
    }

    public void showPostMenu(User loggedInUser) {
        while(true) {
            System.out.println("1.  \"Postları Görüntüle\"\n" +
                    "2.\t\"Post Paylaş\"\n" +
                    "3.\t\"Kendi Postlarını Görüntüle\"\n" +
                    "4.\t\"Post Düzenle\"\n" +
                    "5.  \"Post Sil\"\n" +
                    "6.  \"Kullanıcıları Listele\"\n" +
                    "0.\t\"Üst Menüye Geç\"");
            int secim = readInt("Seçiminiz:");
            switch (secim) {
                case 1 -> showPosts();
                case 2 -> sharePosts(loggedInUser);
                case 3 -> showYourPosts(loggedInUser);
                case 4 -> updatePost(loggedInUser);
                case 5 -> deletePost(loggedInUser);
                case 6 -> listUsers();
                case 0 -> {
                    return;
                }
                default -> printErrorMessage("Geçersiz giriş! 1-6 arası değer giriniz.");
            }
        }
    }

    private void listUsers() {
        postService.listUsers();
    }

    private void deletePost(User loggedInUser) {
        String title = readString("Silinecek olan title: ");
        String msg = postService.deletePost(title,loggedInUser.getId());
        System.out.println(msg);
    }

    private void updatePost(User loggedInUser) {
        Post post = new Post();
        String oldtitle = readString("Değişecek olan title: ");
        String newtitle = readString("Yeni title: ");
        String newcontent= readString("Yeni content: ");
        post.setUserId(loggedInUser.getId());
        post.setTitle(newtitle);
        post.setContent(newcontent);
        String msg= postService.updatePost(post,oldtitle);
        System.out.println(msg);

    }

    private void showYourPosts(User loggedInUser) {
        postService.showYourPosts(loggedInUser.getId());
    }

    private void sharePosts(User loggedInUser) {
        Post post = new Post();
        String title = readString("Title: ");
        String content= readString("Content: ");
        post.setUserId(loggedInUser.getId());
        post.setTitle(title);
        post.setContent(content);
        postService.sharePosts(post);
    }

    private void showPosts() {
        System.out.println("1. Başka bir user'ın(id isteyerek)\n" +
                "2. Tüm Postlar");
        int secim2 = readInt("Seçiminiz:");
        switch (secim2) {
            case 1-> showByUser();
            case 2-> showAllPosts();
            default -> printErrorMessage("Geçersiz giriş! 1-2 arası değer giriniz.");
        }
    }

    private void showAllPosts() {
        postService.showAllPosts();
    }

    private void showByUser() {
        String username;
        while (true) {
            username = readString("Username:");
            if (userService.isValidUsername(username)) {
                break;
            } else {
                System.out.println("Geçerli bir username giriniz. Username en az 3 karakter en fazla 20 karakter olmalı. " +
                        "İçinde sadece harfler ve sayılar olabilir.");
            }
        }
        postService.showByUser(username);
    }
}