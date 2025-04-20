package org.basak.JDBC_Forum.config;

public class DatabaseConnectionConfigConstants {
    public static final String JDBCDRIVER="jdbc:postgresql";
    public static final String SERVER="localhost";
    public static final String PORT="5432";
    public static final String DATABASE_NAME="forumdb";
    public static final String USERNAME="postgres";
    public static final String PASSWORD="root";

    public static final String URL_BASE=JDBCDRIVER+"://"+SERVER+":"+PORT+"/?user="+USERNAME+"&password="+PASSWORD;
    public static final String URL=JDBCDRIVER+"://"+SERVER+":"+PORT+"/"+DATABASE_NAME+"?user="+USERNAME+"&password="+PASSWORD;
}
