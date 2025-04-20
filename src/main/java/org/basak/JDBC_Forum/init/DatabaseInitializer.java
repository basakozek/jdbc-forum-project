package org.basak.JDBC_Forum.init;
import java.sql.*;
import static org.basak.JDBC_Forum.config.DatabaseConnectionConfigConstants.*;

public class DatabaseInitializer {
    //CREATE DATABASE forumdb
    private static final String CREATE_DATABASE = "CREATE DATABASE " + DATABASE_NAME;
    private static final String CREATE_TBLUSER = """
			CREATE TABLE IF NOT EXISTS tbl_user (
			                id SERIAL PRIMARY KEY,
			                name VARCHAR(100) NOT NULL,
			                surname VARCHAR(100) NOT NULL,
			                username VARCHAR(100) UNIQUE NOT NULL,
			                password VARCHAR(100) NOT NULL);
			""";
    private static final String CREATE_TBLPOST = """
			CREATE TABLE IF NOT EXISTS tbl_post (
			                id SERIAL PRIMARY KEY,
			                user_id INT NOT NULL,
			                title VARCHAR(255) NOT NULL,
			                content TEXT NOT NULL,
			                posted_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
			                FOREIGN KEY (user_id) REFERENCES tbl_user(id));
						
			""";
    public static void init() {
        createDatabase();
        createTablesIfNotExist();
    }

    //ÖNCE DATABASE OLUŞTURALIM.
    private static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(URL_BASE);
             Statement statement = connection.createStatement()) {
            //Daha öncesinde bu database var mı yok mu kontrol edelim?
            //JDBC de catalog database adı anlamındadır.
            //postgres database içinde pg_catalog schemede pg_database tablosunda serverdaki tüm database nameleri
            // görebilirsiniz.
            try (ResultSet catalogs = connection.getMetaData().getCatalogs()) {
                while (catalogs.next()) {
                    if (DATABASE_NAME.equalsIgnoreCase(catalogs.getString(1))) {
                        System.out.println(catalogs.getString(1)+"=(forumdb) Database hazır...");
                        return;
                    }
                }
            }
            statement.executeUpdate(CREATE_DATABASE); //DDL
            System.out.println("Veritabanı oluşturuldu...");
        }
        catch (SQLException e) {
            System.err.println("Veritabanı oluştururken hata meydana geldi." + e.getMessage());
        }
    }

    //Ardından TABLO OLUŞTURALIM:
    private static void createTablesIfNotExist() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            //tablo varsa:
            //pg_catalog scheme içindeki tables views'ini geziyoruz aslında. Burada ilgili db'deki tabloların
            // tamamının adı bulunuyor.
			/*try (ResultSet tables =
					     connection.getMetaData().getTables(DATABASE_NAME, null, "tbl_user", null)) {
					if (tables.next()) {
						System.out.println(tables.getString(1)+"=(tbl_user) tablosu hazır.");
						return;
					}
			}*/
            //önce tbl_user oluşturulmalı. FK için.
            statement.executeUpdate(CREATE_TBLUSER);
            System.out.println("tbl_user Tablosu oluşturuldu... ");
            //tbl_post daha sonra oluşturulmalı.
            statement.executeUpdate(CREATE_TBLPOST);
            System.out.println("tbl_post Tablosu oluşturuldu... ");
        }
        catch (SQLException e) {
            System.out.println("tbl_user tablo oluşturma hatası..." + e.getMessage());
        }
    }
}
