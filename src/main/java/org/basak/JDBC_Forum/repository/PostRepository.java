package org.basak.JDBC_Forum.repository;
import org.basak.JDBC_Forum.entity.Post;
import org.basak.JDBC_Forum.utility.DatabaseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository implements ICrud<Post> {

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO tbl_post(user_id, title, content) VALUES(?, ?, ?)";
        try (DatabaseHelper db = new DatabaseHelper()) {
            db.executeUpdate(sql,
                    post.getUserId(),
                    post.getTitle(),
                    post.getContent()
            );
        }
    }

    //createAt, updateAt, status
    @Override
    public void update(Post post) {
        String sql = "UPDATE tbl_post SET title=?, content=? WHERE id=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            db.executeUpdate(sql,
                    post.getTitle(),
                    post.getContent(),
                    post.getId()
            );
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tbl_post WHERE id=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            db.executeUpdate(sql, id);
        }
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT * FROM tbl_post ORDER BY posted_date DESC";
        String error = "findAll (Post): ";
        return findPosts(sql, error);
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM tbl_post WHERE id=?";
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> rs = db.executeQuery(sql, id);
            if (rs.isPresent() && rs.get().next()) {
                return Optional.of((resultSetToPost(rs.get())));
            }
        }
        catch (SQLException e) {
            System.err.println("findById: " + e.getMessage());
        }
        return Optional.empty();
    }

    //extra metodlar
    public List<Post> findAllByUserId(Long userId){
        String sql = "SELECT * FROM tbl_post WHERE user_id=? ORDER BY posted_date DESC";
        String error = "findAllByUserId (Post): ";
        return findPosts(sql, error,userId);
    }

    public List<Post> findAllOrderByPostedDateDesc(){
        String sql = "SELECT * FROM tbl_post ORDER BY posted_date DESC";
        String error = "findAllOrderByPostedDateDesc (Post): ";
        return findPosts(sql, error);
    }

    //yardımcı metod:
    private Post resultSetToPost(ResultSet rs) throws SQLException {
        return new Post(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("posted_date").toLocalDateTime());
    }

    //yardımcı metod:
    private List<Post> findPosts(String sql, String error, Object... params) {
        List<Post> posts = new ArrayList<>();
        try (DatabaseHelper db = new DatabaseHelper()) {
            Optional<ResultSet> rs = db.executeQuery(sql, params);
            if (rs.isPresent()) {
                while (rs.get().next()) {
                    posts.add(resultSetToPost(rs.get()));
                }
            }
        } catch (SQLException e) {
            System.err.println(error+e.getMessage());
        }
        return posts;
    }
    public List <Post> findPostbyUserIdAndTitle(Long userId,String title){
        String sql = "SELECT * FROM tbl_post WHERE user_id=? AND title=? ORDER BY posted_date DESC";
        String error = "findPostbyUserIdAndTitle(Post): ";
        return findPosts(sql, error,userId,title);
    }
}
