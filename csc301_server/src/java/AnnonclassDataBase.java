
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class AnnonclassDataBase {
    private Connection connection;

    public AnnonclassDataBase() {
    }

    public void connectDB(String url) throws SQLException{
        //write your code here.
        connection = DriverManager.getConnection(
                    url, "postgres", "wrdhb123");
    }

    public void disconnectDB() throws SQLException{

        connection.close();

    }

    public int signup(JSONObject info) throws SQLException{

        String email = info.getString("email");
        String psw_hash = info.getString("password");
        String utorid = info.getString("UTORid");
        String firstName = info.getString("firstName");
        String lastName = info.getString("lastName");
        Boolean isStudent = info.getBoolean("isStudent");
        PreparedStatement execStat = connection.prepareStatement("select email from dbschema.client where email = ?;");
        execStat.setString(1, email);
        ResultSet result = execStat.executeQuery();
        if (!result.next()) {
            PreparedStatement insert = connection.prepareStatement("insert " +
                    "into dbschema.client values(?, ?, ?, ?, ?, ?);");
            insert.setString(1, email);
            insert.setString(2,psw_hash);
            insert.setString(3, utorid);
            insert.setString(4, firstName);
            insert.setString(5, lastName);
            insert.setBoolean(6, isStudent);
            insert.executeUpdate();
            return 0;
        } else {
            return 1;
        }

    }

    public JSONArray login(JSONObject info) throws SQLException{

        String email = info.getString("email");
        String password = info.getString("password");
        JSONArray ar = new JSONArray();
        PreparedStatement execStat = connection.prepareStatement("select email from dbschema.client " +
                        "where email = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
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
                PreparedStatement courses = connection.prepareStatement("select dbschema.course_user.course_name, " +
                            "dbschema.course_user.section_number, dbschema.course_section.instructor_name from dbschema.client, " +
                            "dbschema.course_user, dbschema.course_section where dbschema.client.email = ? and dbschema.client.email = dbschema.course_user.user_email " +
                            "and dbschema.course_user.course_name = dbschema.course_section.course_name and dbschema.course_user.section_number " +
                            "= dbschema.course_section.section_number;", ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                courses.setString(1, email);
                ResultSet re = courses.executeQuery();
                while(re.next()) {
                    JSONObject course = new JSONObject();
                    course.put("courseCode", re.getString(1));
                    course.put("sectionCode", re.getInt(2));
                    ar.put(course);
                }
                return ar;

            }
        }
    }
    
    public int addCourse(JSONObject info) {
    	try {
    		//get instructor email, instructor name, location, section number
    		String instr_email = info.getString("instructor_email");
    		String instr_name = info.getString("instructor_name");
    		String course_name = info.getString("course_name");
    		int sct_number = info.getInt("section_number");
    		String location = info.getString("location");
    		
    		//check the whether the user is a teacher 
    		PreparedStatement execStat = connection.prepareStatement("select studentFlag from user " +
                     "where user.email = ?");
    		 execStat.setString(1, instr_email);
    		 ResultSet result = execStat.executeQuery();
    		 if(isResultSetEmpty(result)) {
    			 return -1;
    		 }else {
//    			 boolean is_std = result.getObject(1);
                 boolean is_std = result.getBoolean(1);
    			 if(is_std) {// the user is a student, not permitted
    				 return -1;
    			 }else {//the user is a teacher
    				 //add the course to the table'course_section'
    				 PreparedStatement insert = connection.prepareStatement("insert " +
    	                        "into course_section values(sct_number, instr_email, instr_name, location)");
    			 }
    		 }
    	}catch(SQLException e){
			 e.printStackTrace();
			 return -1;
		}
    	//add success
    	return 1;
    }

    public int enroll_course(JSONObject info) throws SQLException{
        String email = info.getString("email");
        String course_name = info.getString("course_name");
        String section_number = info.getString("section_number");
        Boolean studentflag;
        PreparedStatement exec0 = connection.prepareStatement("select studentFlag from " +
                        "dbschema.client where email = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        exec0.setString(1, email);
        ResultSet re0 = exec0.executeQuery();
        while (re0.next()) {
            studentflag = re0.getBoolean(1);
            if (!studentflag) {
                return 1;
            }
        }

        PreparedStatement exec1 = connection.prepareStatement("select section_number, course_name " +
                "from dbschema.course_section where section_number = ? and course_name = ?;",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        exec1.setString(1, section_number);
        exec1.setString(2, course_name);
        ResultSet resultSet = exec1.executeQuery();
        if (isResultSetEmpty(resultSet)) {
            // no such course created yet.
            return 1;
        } else {
            PreparedStatement exec2 = connection.prepareStatement("select * from dbschema.course_user " +
                    "where email = ? and course_name = ? and section_number = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            exec2.setString(1, email);
            exec2.setString(2, course_name);
            exec2.setString(3, section_number);
            ResultSet resultSet2 = exec2.executeQuery();
            if (isResultSetEmpty(resultSet2)) {
                // client has not enrolled this course yet.
                PreparedStatement exec3 = connection.prepareStatement("insert into dnschma.course_user values(?, ?, ?);");
                exec3.setString(1, course_name);
                exec3.setString(2, section_number);
                exec3.setString(3, email);
                return 0;

            } else {
                // client has already enrolled
                return 1;
            }
        }
    }

    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();

    }
}
