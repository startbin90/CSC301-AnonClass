import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class AnnonclassDataBase {
    private Connection connection;

    public AnnonclassDataBase(Connection connection) {
        this.connection = connection;
    }

    public void connectDB(String url) throws SQLException {
        //write your code here.
        try {
            connection = DriverManager.getConnection(
                    url, "anonadmin", "anonpassword");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        }
    }

    public void disconnectDB() throws SQLException{
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
                    PreparedStatement courses = connection.prepareStatement("select course_user.course_name, " +
                            "course_user.section_number, course_section.instructor_name, course_section.location from user, " +
                            "course_user, course_section where user.email = ? and user.email = course_user.user_email " +
                            "and course_user.course_name = course_section.course_name and course_user.section_number " +
                            "= course_section.section_number;");
                    courses.setString(1, email);
                    ResultSet re = courses.executeQuery();
                    while(re.next()) {
                        JSONObject course = new JSONObject();
                        course.put("courseCode", re.getString(1));
                        course.put("sectionCode", re.getString(2));
                        course.put("instructor", re.getString(3));
                        course.put("location", re.getString(4));
                        ar.put(course);

                    }
                    return ar;

                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int addCourse(JSONObject info) {
    	try {
    		//get instructor email, instructor name, location, section number
    		String instr_email = info.getString("instructor_email");
    		String instr_name = info.getString("instructor_name");
    		String course_name = info.getString("course_name");
    		int sct_number = info.getString("section_number");
    		String location = info.getString("location");
    		
    		//check the whether the user is a teacher 
    		PreparedStatement execStat = connection.prepareStatement("select studentFlag from user " +
                     "where user.email = ?";
    		 execStat.setString(1, instr_email);
    		 ResultSet result = execStat.executeQuery();
    		 if(isResultSetEmpty(result)) {
    			 return -1;
    		 }else {
    			 boolean is_std = result.getObject(1);
    			 if(flag == true) {// the user is a student, not permitted
    				 return -1;
    			 }else {//the user is a teacher
    				 //add the course to the table'course_section'
    				 PreparedStatement insert = connection.prepareStatement("insert " +
    	                        "into course_section values(sct_number, instr_email, instr_name, location)");
    			 }
    		 }catch(SQLException e){
    			 e.printStackTrace();
    			 return -1;
    		 }
    	}  
    	//add success
    	return 1;
    }

    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();

    }
}
