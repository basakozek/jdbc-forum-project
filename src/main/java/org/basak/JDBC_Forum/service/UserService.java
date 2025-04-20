package org.basak.JDBC_Forum.service;
import org.basak.JDBC_Forum.constants.LoginResult;
import org.basak.JDBC_Forum.constants.RegisterResult;
import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.repository.UserRepository;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public boolean isValidUsername(String username) {
        return username != null &&
                username.trim().length() >= 3 &&
                username.trim().length() <= 20 &&
                username.trim().matches("^[a-zA-Z0-9]+$");
    }

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,50}$";
        return password.matches(regex);

		/*if (password.length() < 6 || password.length() > 100) {
			return false;
		}
		boolean hasUppercase = password.matches(".*[A-Z].*");
		boolean hasLowercase = password.matches(".*[a-z].*");
		boolean hasDigit = password.matches(".*[0-9].*");

		return hasUppercase && hasLowercase && hasDigit;*/
    }

    public RegisterResult register(String name, String surname, String username, String password) {
        if (isInvalidInput(name, surname, username, password)) {
            return RegisterResult.INVALID_INPUT;
        }
        try {
            if (userRepository.existsByUserName(username)) {
                return RegisterResult.USERNAME_EXISTS;
            }
            User user = new User(name.trim(), surname.trim(), username.trim(), password.trim());
            userRepository.save(user);
            return RegisterResult.SUCCESS;
        } catch (Exception e) {
            return RegisterResult.ERROR;
        }
    }
    private boolean isInvalidInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.isBlank()) {
                return true;
            }
        }
        return false;
    }

    public LoginResult login(String username, String password) {
        if(isInvalidInput(username, password)){
            return LoginResult.INVALID_INPUT;
        }
        Optional<User> user = null;
        try {
            user = userRepository.doLogin(username, password);
        }
        catch (SQLException e) {
            return LoginResult.DATABASE_ERROR;
        }
        //return user.map(LoginResult.SUCCESS::setUser).orElse(LoginResult.INVALID_CREDENTIALS);
        if (user.isPresent()) {
            return LoginResult.SUCCESS.setUser(user.get());
        }else {
            return LoginResult.INVALID_CREDENTIALS;
        }
    }
}