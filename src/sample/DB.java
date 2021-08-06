package sample;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "reg_auth_proj1";
    private final String LOGIN = "mysql";
    private final String PASS = "mysql";

    private Connection dbConn = null;

    private Connection getDbConn() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConn();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean regUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES (?, ?, ?)";

        // Проверим если уже имеется ранее зарегистрированный пользователь с таким логином
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1");
        if(res.next()){
            return false;
        }

        PreparedStatement prst = getDbConn().prepareStatement(sql);
        prst.setString(1, login);
        prst.setString(2, email);
        prst.setString(3, password);
        prst.executeUpdate();
        return true;
    }

    public boolean authUser(String login, String password) throws SQLException, ClassNotFoundException {

        Statement statement = getDbConn().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "'AND `password` = '" + password + "' LIMIT 1";

        ResultSet res = statement.executeQuery(sql);
            return res.next();
    }

    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
        String sql = "SELECT `title`, `intro` FROM `articles`";
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

    public void addArticle(String title, String intro, String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES (?, ?, ?, ?)";
        PreparedStatement prst = getDbConn().prepareStatement(sql);
        prst.setString(1, title);
        prst.setString(2, intro);
        prst.setString(3, text);
        prst.setInt(4, 15);
        prst.executeUpdate();

    }
}
