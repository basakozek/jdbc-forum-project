package org.basak.JDBC_Forum.repository;
import org.basak.JDBC_Forum.entity.User;
import org.basak.JDBC_Forum.utility.DatabaseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements ICrud<User> {


    @Override
    public void save(User user) {
        String sql = "INSERT INTO tbl_user(name,surname,username,password) VALUES(?,?,?,?)";
        try (DatabaseHelper db = new DatabaseHelper()) {
            db.executeUpdate(sql,
                    user.getAd(),
                    user.getSoyad(),
                    user.getUsername(),
                    user.getPassword()
            );
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE tbl_user SET name=?, surname=?, username=?, password=? WHERE id=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            db.executeUpdate(sql,
                    user.getAd(),
                    user.getSoyad(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getId()
            );
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tbl_user WHERE id=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            db.executeUpdate(sql, id);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM tbl_user";
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> rs = db.executeQuery(sql);
            if(rs.isPresent()) {
                while(rs.get().next()){
                    users.add(resultSetToUser(rs.get()));
                }
            }
        }
        catch (SQLException e) {
            System.err.println("findAll: " + e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM tbl_user WHERE id=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> rs = db.executeQuery(sql,id);
            if(rs.isPresent() && rs.get().next()) {
                return Optional.of((resultSetToUser(rs.get())));
            }
        }
        catch (SQLException e) {
            System.err.println("findById: " + e.getMessage());
        }
        return Optional.empty();
    }
    //yardımcı metod:
    private User resultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setAd(rs.getString(2));
        user.setSoyad(rs.getString(3));
        user.setUsername(rs.getString(4));
        user.setPassword(rs.getString(5));
        return user;
    }
    public boolean existsByUserName(String username){
        String sql = "SELECT * FROM tbl_user WHERE username=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> resultSet = db.executeQuery(sql, username);
            if(resultSet.isPresent() && resultSet.get().next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.err.println("existsByUserName: " + e.getMessage());
        }
        return false;
    }

    public Optional<User> doLogin(String username,String password) throws SQLException {
        String sql = "SELECT * FROM tbl_user WHERE username=? AND password=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> rs = db.executeQuery(sql, username, password);
            if(rs.isPresent() && rs.get().next()) {
                return Optional.of(resultSetToUser(rs.get()));
            }
        }

        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM tbl_user WHERE username=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> rs = db.executeQuery(sql, username);
            if (rs.isPresent() && rs.get().next()) {
                return Optional.of(resultSetToUser(rs.get()));
            }
        } catch (SQLException e) {
            System.err.println("findByUsername: " + e.getMessage());
        }
        return Optional.empty();
    }
}
