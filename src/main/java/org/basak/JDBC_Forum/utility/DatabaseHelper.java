package org.basak.JDBC_Forum.utility;
import org.basak.JDBC_Forum.config.DatabaseConnectionConfigConstants;
import java.sql.*;
import java.util.Optional;

public class DatabaseHelper implements AutoCloseable {
    private Connection conn;
    private PreparedStatement ps;

    public DatabaseHelper() {
        openConnection();
    }

    private boolean openConnection(){
        try {
            conn= DriverManager.getConnection(DatabaseConnectionConfigConstants.URL);
            return true;
        }
        catch (SQLException e) {
            System.out.println("Bağlantı açılamadı..."+e.getMessage());
            return false;
        }
    }

    @Override
    public void close()  {
        try {
            if(conn!=null || !conn.isClosed()){
                conn.close();
            }
            if(ps!=null || !ps.isClosed()){
                ps.close();
            }
        }
        catch (SQLException e) {
            System.out.println("Connection close hatası..."+e.getMessage());
        }
    }

    public boolean executeUpdate(String sql, Object... parameters){
        try {
            ps = conn.prepareStatement(sql);
            for(int i=0; i<parameters.length; i++){
                ps.setObject(i+1, parameters[i]);
            }
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.out.println("executeUpdate işleminde hata..."+e.getMessage());
            return false;
        }
    }
    public Optional<ResultSet> executeQuery(String sql, Object... parameters){
        try {
            ps = conn.prepareStatement(sql);
            for(int i=0; i<parameters.length; i++){
                ps.setObject(i+1, parameters[i]);
            }
            return Optional.ofNullable(ps.executeQuery());
        }
        catch (SQLException e) {
            System.out.println("executeQuery(SELECT) işleminde hata..."+e.getMessage());
            return Optional.empty();
        }

    }
}
