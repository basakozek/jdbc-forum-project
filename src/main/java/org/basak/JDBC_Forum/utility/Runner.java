package org.basak.JDBC_Forum.utility;

import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.init.DatabaseInitializer;
import org.basak.JDBC_Forum.repository.UserRepository;

public class Runner {
    public static void main(String[] args) {
        DatabaseInitializer.init();
        UserRepository ur = new UserRepository();
        ur.save(new User("ad2","soyad2","username2","password2"));
    }
}