package org.basak.JDBC_Forum.controller;
import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.init.DatabaseInitializer;

public class MainController extends BaseController {
    private UserController userController;
    private User loggedInUser;
    private PostController postController= new PostController();

    public MainController() {
        DatabaseInitializer.init();
        this.userController = new UserController();
    }

    public MainController(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public static void main(String[] args) {
        new MainController().run();
    }

    public void run(){
        while(true){
            if(loggedInUser == null){
                showMainMenu();
            }else{
                showUserMenu();
            }
        }
    }

    public void showUserMenu() {
        //Java Text Blocks
        System.out.println("""
				                   ********************************
				                     Hoşgeldin, %s %s! .
				                     1. "Post İşlemleri"
				                     2. "Profil İşlemleri"
				                     3. "Arama İşlemleri"
				                     4. "Çıkış"
				                   ********************************
				                   """.formatted(loggedInUser.getAd(),loggedInUser.getSoyad()));
        int secim = readInt("Seçiminiz:");
        switch (secim) {
            case 1-> postController.showPostMenu(loggedInUser);
            case 2-> ProfileController.showProfileMenu();
            case 3-> SearchController.showSearchMenu();
            case 4-> logout();
            default -> printErrorMessage("Geçersiz giriş! 1-4 arası değer giriniz.");
        }
    }

    private void logout() {
        loggedInUser = null;
        System.out.println("Çıkış Yapıldı.");
    }

    private void showMainMenu() {
        System.out.println("""
				                   *****************
				                     Java17 Forum
				                     1. "Kayıt Ol"
				                     2. "Giriş Yap"
				                     3. "Çıkış"
				                   *****************
				                   """);
        int secim = readInt("Seçiminiz:");
        switch (secim) {
            case 1-> userController.handleRegister();
            case 2-> loggedInUser = userController.handleLogin();
            case 3-> System.exit(0);
            default -> printErrorMessage("Geçersiz giriş! 1-3 arası değer giriniz.");
        }
    }


}
