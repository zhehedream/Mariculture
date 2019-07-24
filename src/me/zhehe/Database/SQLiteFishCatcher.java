/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.Database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import me.zhehe.Util.Constant;

/**
 *
 * @author zhiqiang.hao
 */
public class SQLiteFishCatcher {
    final String PATH = "./plugins/Mariculture/";
    public static String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS " + Constant.FISH_CATCHER_DBNAME + " (" +
            "`location` varchar(32) NOT NULL," +
            "`world` varchar(32) NOT NULL," +
            "`time` BIGINT NOT NULL," +
            "PRIMARY KEY (`location`)" +
            ");";
    
    Connection connection;
    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            //Bukkit.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
        }
    }
    
    public void initialize(){
        connection = getSQLConnection();
    }
    
    public Connection getSQLConnection() {
        File dataFolder = new File(PATH, Constant.FISH_CATCHER_DBNAME+".db");
        
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                //Bukkit.getLogger().log(Level.SEVERE, "File write error: " + Constant.FISH_CATCHER_DBNAME + ".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            //Bukkit.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
            System.out.println("SQLite exception on initialize:" + ex.toString());
        } catch (ClassNotFoundException ex) {
            //Bukkit.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
            System.out.println("You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }
    
    public void load() {
        connection = getSQLConnection();     
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTokensTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
    
    public void deleteLocation(String world, int x, int y, int z) {
        Connection conn = null;
        PreparedStatement ps = null;
        String loc = Integer.toString(x) + "#" + Integer.toString(y) + "#" + Integer.toString(z);
        try {
            conn = getSQLConnection();
            String sql = "DELETE FROM " + Constant.FISH_CATCHER_DBNAME + " WHERE `location`='" + loc + "' AND `world`='" + world + "';";
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addLocation(String world, int x, int y, int z) {
        Connection conn = null;
        PreparedStatement ps = null;
        String loc = Integer.toString(x) + "#" + Integer.toString(y) + "#" + Integer.toString(z);
        try {
            conn = getSQLConnection();
            String sql = "DELETE FROM " + Constant.FISH_CATCHER_DBNAME + " WHERE `location`='" + loc + "' AND `world`='" + world + "';";
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn = getSQLConnection();
            String sql = "INSERT INTO " + Constant.FISH_CATCHER_DBNAME + " (`location`,`world`,`time`) VALUES ('" + loc + "','"+world+"'," + Long.toString(System.currentTimeMillis() / 1000L) +");";
            System.out.println(sql);
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public long isLocation(String world, int x, int y, int z) {
        Connection conn = null;
        PreparedStatement ps = null;
        String loc = Integer.toString(x) + "#" + Integer.toString(y) + "#" + Integer.toString(z);
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + Constant.FISH_CATCHER_DBNAME + " WHERE location = '"+loc+"' AND world = '" + world + "';");
   
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getLong("time");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        System.out.println(SQLiteCreateTokensTable);
        SQLiteFishCatcher sql = new SQLiteFishCatcher();
        sql.load();
        sql.addLocation("test", 1, 19, 110);
        System.out.println(sql.isLocation("test", 1, 19, 110));
        System.out.println(sql.isLocation("test1", 1, 19, 110));
    }
}
