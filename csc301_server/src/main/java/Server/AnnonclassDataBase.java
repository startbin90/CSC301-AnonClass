package Server;


import java.sql.*;

public class AnnonclassDataBase {
    private Connection connection;

    public AnnonclassDataBase(Connection connection) {
        this.connection = connection;
    }

    public void connectDB(String url) {
        //write your code here.
        try {
            connection = DriverManager.getConnection(
                    url);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void disconnectDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean signup(String email, String password) {
        try {
            PreparedStatement execStat = connection.prepareStatement("select userid from users " +
                    "where user.email = ?;");
            execStat.setString(1, email);
            ResultSet result = execStat.executeQuery();
            if (isResultSetEmpty(result)) {
                PreparedStatement insert = connection.prepareStatement("insert into users values(?, ?)");
                insert.setString(1, email);
                insert.setString(2,hash(password));
                insert.executeUpdate();
                return true;
            } else {
                return false;
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            PreparedStatement execStat = connection.prepareStatement("select psw_hash from users " +
                    "where user.email = ?;");
            execStat.setString(1, email);
            ResultSet result = execStat.executeQuery();
            if (isResultSetEmpty(result)) {
                return false;
            } else {
                result.first();
                String psw_hash = result.getString(1);
                return psw_hash.equals(hash(password));
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hash(String password) {
        return password;
    }

    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();

    }
}
