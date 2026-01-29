package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {
    private String passwd = "XXX!";


























    private String url = "jdbc:postgresql://database-etudiants:5432/jkaku";
    private String user = "jkaku";

    /**
     * Objet Connection
     */
    private static Connection connect;
    /**
     * Constructeur privé
     * @throws ClassNotFoundException
     */
    private ConnectionDB() throws ClassNotFoundException{
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Methode qui va nous retourner notre instance
     * et la creer si elle n'existe pas...
     * @return
     * @throws ClassNotFoundException
     */
    public static Connection getInstance() throws ClassNotFoundException {
        try {
            if (connect == null || connect.isClosed()) {
                new ConnectionDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
