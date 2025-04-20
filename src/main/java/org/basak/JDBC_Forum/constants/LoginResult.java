package org.basak.JDBC_Forum.constants;

import org.basak.JDBC_Forum.entity.User;

import java.util.Optional;

public enum LoginResult {
    SUCCESS("Giriş başarılı."),
    INVALID_CREDENTIALS("Hatalı kullanıcı adı veya şifre!"),
    INVALID_INPUT("Kullanıcı adı ve şifre boş olamaz!"),
    DATABASE_ERROR("Giriş sırasında bir veritabanı hatası oluştu."),
    ERROR("Beklenmeyen bir hata oluştu.");

    private final String message;
    private User user;

    LoginResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Optional<User> getUser() {
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public LoginResult setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isSuccess() {
        return this == SUCCESS && user != null;
    }
}