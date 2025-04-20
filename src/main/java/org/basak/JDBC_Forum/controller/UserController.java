package org.basak.JDBC_Forum.controller;
import org.basak.JDBC_Forum.constants.LoginResult;
import org.basak.JDBC_Forum.constants.RegisterResult;
import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.service.UserService;

public class UserController extends BaseController {
    private final UserService userService;
    public UserController() {
        this.userService = new UserService();
    }

    public void handleRegister() {
        System.out.println("=== Register Ekranı ===");

        String ad = readString("Ad:");
        String soyad = readString("Soyad:");

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

        String password;
        while (true) {
            password = readString("Password:");
            if (userService.isValidPassword(password)) {
                break;
            } else {
                System.out.println("Geçerli bir password giriniz. En az 6 karakter ,En az 1 Büyük, 1 küçük, 1 rakam içermeli");
            }
        }

        RegisterResult result = userService.register(ad, soyad, username, password);

        switch (result) {
            case SUCCESS -> System.out.println("Kayıt başarılı.");
            case USERNAME_EXISTS -> System.out.println("Bu kullanıcı adı zaten alınmış.");
            case INVALID_INPUT -> System.out.println("Lütfen tüm alanları eksiksiz ve geçerli doldurun.");
            case ERROR -> System.out.println("Kayıt sırasında bir hata oluştu.");
        }
    }


    public User handleLogin() {
        System.out.println("=== Login Ekranı ===");
        String username = readString("Kullanıcı Adı: ");
        String password = readString("Şifre: ");

        LoginResult result = userService.login(username, password);

        if (result.isSuccess() && result.getUser().isPresent()) {
            User user = result.getUser().get();
            System.out.println("Hoş geldiniz, " + user.getAd() + " " + user.getSoyad());
            return user;
        } else {
            System.out.println(result.getMessage());
            return null;
        }
    }

}
