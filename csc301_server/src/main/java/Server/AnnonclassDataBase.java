package Server;


import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

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

    public boolean signup(JSONObject info) {
        try {
            String email = info.getString("email");
            String psw_hash = info.getString("password");
            String utorid = info.getString("UTORid");
            String firstName = info.getString("firstName");
            String lastName = info.getString("lastName");
            Boolean isStudent = info.getBoolean("isStudent");
            PreparedStatement execStat = connection.prepareStatement("select email from users " +
                    "where user.email = ?;");
            execStat.setString(1, email);
            ResultSet result = execStat.executeQuery();
            if (isResultSetEmpty(result)) {
                PreparedStatement insert = connection.prepareStatement("insert " +
                        "into users values(?, ?, ?, ?, ?, ?)");
                insert.setString(1, email);
                insert.setString(2,psw_hash);
                insert.setString(3, utorid);
                insert.setString(4, firstName);
                insert.setString(5, lastName);
                insert.setBoolean(6, isStudent);
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

    public JSONArray login(JSONObject info) {
        try {
            String email = info.getString("email");
            String password = info.getString("password");
            JSONArray ar = new JSONArray();
            PreparedStatement execStat = connection.prepareStatement("select pwdHash from users " +
                    "where user.email = ?;");
            execStat.setString(1, email);
            ResultSet result = execStat.executeQuery();
            if (isResultSetEmpty(result)) {
                return null;
            } else {
                result.first();
                String psw_hash = result.getString(1);
                if (psw_hash.equals(password)) {
                    return null;
                } else {
                    PreparedStatement courses = connection.prepareStatement("select ");
                    return ar;
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();

    }
}
