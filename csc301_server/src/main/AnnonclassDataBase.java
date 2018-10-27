package main;


import java.sql.*;

public class AnnonclassDataBase {
    private Connection connection;

    public AnnonclassDataBase(Connection connection) {
        this.connection = connection;
    }

    public boolean connectDB(String url) {
        //write your code here.
        try {
            connection = DriverManager.getConnection(
                    url);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return true;
    }

    public boolean disconnectDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean signup(String email, String password) {
        try {
            PreparedStatement execStat = connection.prepareStatement("select userid from users " +
                    "where user.email = ?;");
            execStat.setString(1, email);
            ResultSet result = execStat.executeQuery();
            if (isResultSetEmpty(result)) {
                PreparedStatement insert = connection.prepareStatement("insert into users values()");
                return true;
            } else {
                return false;
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hash(String password) {
        return password;
    }

    public static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();

    }
}
