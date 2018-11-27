
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String psw_hash = info.getString("pwdHash");
        String utorid = info.getString("utorid");
        String firstName = info.getString("firstName");
        String lastName = info.getString("lastName");
        Boolean isStudent = info.getBoolean("studentFlag");
        PreparedStatement execStat = connection.prepareStatement("select email from dbschema.client where email " +
                "= ? or utorid = ?;");
        execStat.setString(1, email);
        execStat.setString(2, utorid);
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

    public JSONObject login(JSONObject info) throws SQLException{

        String email = info.getString("email");
        String password = info.getString("pwdHash");
        JSONArray ar = new JSONArray();
        PreparedStatement execStat = connection.prepareStatement("select pwdHash, " +
                        "utorid, firstName, lastName, " +
                        "studentFlag from dbschema.client " +
                        "where email = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        execStat.setString(1, email);
        ResultSet result = execStat.executeQuery();
        if (isResultSetEmpty(result)) {
            return null;
        } else {
            result.first();
            String psw_hash = result.getString(1);
            String utorid = result.getString(2);
            String firstName = result.getString(3);
            String lastName = result.getString(4);
            Boolean studentFlag = result.getBoolean(5);
            if (!psw_hash.equals(password)) {
                return null;
            } else {
                PreparedStatement courses = connection.prepareStatement("select course_name, course_code, " +
                                "section_number, instructor_email, instructor_name, time_created, locations " +
                                "from dbschema.course_user, " +
                                "dbschema.course_section " +
                                "where dbschema.course_user.selected_course = dbschema.course_section.id " +
                                "and dbschema.course_user.user_email = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                courses.setString(1, email);
                ResultSet re = courses.executeQuery();
                while(re.next()) {
                    JSONObject course = new JSONObject();
                    course.put("course_name", re.getString(1));
                    course.put("course_code", re.getString(2));
                    course.put("section_number", re.getString(3));
                    course.put("instructor_email", re.getString(4));
                    course.put("instructor_name", re.getString(5));
                    course.put("time_created", re.getDate(6));
                    course.put("location", re.getString(7));
                    ar.put(course);
                }
                JSONObject user = new JSONObject();
                user.put("email", email);
                user.put("utorid", utorid);
                user.put("firstName", firstName);
                user.put("lastName", lastName);
                user.put("studentFlag", studentFlag);
                user.put("courses", ar);
                return user;

            }
        }
    }
    
    public int addCourse(JSONObject info) throws SQLException, ParseException {
        //get instructor email, instructor name, location, section number
        String instructor_email = info.getString("instructor_email");
        String instructor_name = info.getString("instructor_name");
        String course_name = info.getString("course_name");
        String section_number = info.getString("section_number");
        String locations = info.getString("locations");
        String time_created = info.getString("time_created");
        String course_code = info.getString("course_code");
    		//check the whether the user is a teacher
        PreparedStatement execStat = connection.prepareStatement("select studentFlag from dbschema.client " +
                     "where email = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        execStat.setString(1, instructor_email);
        ResultSet result = execStat.executeQuery();
        if(isResultSetEmpty(result)) {
            return 1;
        } else {
            if (result.getBoolean(1)) {
                // not a instructor
                return 1;
            } else {
                PreparedStatement execStat2 = connection.prepareStatement("select * from dbschema.course_section " +
                                "where course_name = ? and section_number = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                execStat2.setString(1, course_name);
                execStat2.setString(2, section_number);
                ResultSet result2 = execStat2.executeQuery();
                if (!isResultSetEmpty(result2)) {
                    // such course already created
                    return 1;
                } else {
                    PreparedStatement execStat3 = connection.prepareStatement("insert into dbschema.course_section " +
                            "(course_code, course_name, section_number, instructor_email, instructor_name, " +
                            "time_created, locations, geo_location)values(?, ?, ?, ?, ?, ?, ?, '(0,0)')");

                    java.sql.Date sd = java.sql.Date.valueOf(time_created);
                    execStat3.setString(1, course_code);
                    execStat3.setString(2, course_name);
                    execStat3.setString(3, section_number);
                    execStat3.setString(4, instructor_email);
                    execStat3.setString(5, instructor_name);
                    execStat3.setDate(6, sd);
                    execStat3.setString(7, locations);
                    execStat3.executeUpdate();
                    return 0;
                }
            }
        }

    }

    public JSONArray display(JSONObject info) throws SQLException {
        String course_code = info.getString("course_code");
        JSONArray ar = new JSONArray();
        PreparedStatement exec = connection.prepareStatement("select * from dbschema." +
                "course_section where course_code = ?;");
        exec.setString(1, course_code);
        ResultSet resultSet = exec.executeQuery();
        while (resultSet.next()) {
            JSONObject course = new JSONObject();
            course.put("id", resultSet.getInt(1));
            course.put("course_code", course_code);
            course.put("course_name", resultSet.getString(3));
            course.put("section_number", resultSet.getString(4));
            course.put("instructor_email", resultSet.getString(5));
            course.put("instructor_name", resultSet.getString(6));
            course.put("time_created", resultSet.getDate(7));
            course.put("locations", resultSet.getString(8));
            ar.put(course);
        }
        return ar;
    }

    public int enroll_course(JSONObject info) throws SQLException {
        String email = info.getString("email");
        int id = info.getInt("course_id");
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
        PreparedStatement exec1 = connection.prepareStatement("select * " +
                "from dbschema.course_section where id = ?;",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        exec1.setInt(1, id);
        ResultSet resultSet = exec1.executeQuery();
        if (isResultSetEmpty(resultSet)) {
            // no such course created yet.
            return 1;
        } else {
            PreparedStatement exec2 = connection.prepareStatement("select * from dbschema.course_user " +
                            "where selected_course = ? and user_email = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            exec2.setInt(1, id);
            exec2.setString(2, email);
            ResultSet resultSet2 = exec2.executeQuery();
            if (isResultSetEmpty(resultSet2)) {
                // client has not enrolled this course yet.
                PreparedStatement exec3 = connection.prepareStatement("insert into dbschema.course_user" +
                        "(selected_course, user_email)values(?, ?);");
                exec3.setInt(1, id);
                exec3.setString(2, email);
                exec3.executeUpdate();
                return 0;
            } else {
                // client has already enrolled
                return 1;
            }
        }
    }

    public JSONObject display_enrolled_courses(JSONObject info) throws SQLException{
        String email = info.getString("email");
        JSONObject user = new JSONObject();
        JSONArray courses = new JSONArray();
        PreparedStatement exec1 = connection.prepareStatement("select email, utorid, firstname, lastname, " +
                "studentFlag from dbschema.client where email = ?");
        exec1.setString(1, email);
        ResultSet res1 = exec1.executeQuery();
        while (res1.next()) {
            user.put("email", res1.getString(1));
            user.put("utorid", res1.getString(2));
            user.put("firstname", res1.getString(3));
            user.put("lastname", res1.getString(4));
            user.put("studentFlag", res1.getBoolean(5));
        }
        PreparedStatement exec2 = connection.prepareStatement("select course_name, course_code, " +
                        "section_number, instructor_email, instructor_name, time_created, locations " +
                        "from dbschema.course_user, " +
                        "dbschema.course_section " +
                        "where dbschema.course_user.selected_course = dbschema.course_section.id " +
                        "and dbschema.course_user.user_email = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        exec2.setString(1, email);
        ResultSet res2 = exec2.executeQuery();
        while (res2.next()) {
            JSONObject course = new JSONObject();
            course.put("course_name", res2.getString(1));
            course.put("course_code", res2.getString(2));
            course.put("section_number", res2.getString(3));
            course.put("instructor_email", res2.getString(4));
            course.put("instructor_name", res2.getString(5));
            course.put("time_created", res2.getDate(6));
            course.put("location", res2.getString(7));
            courses.put(course);
        }
        user.put("courses", courses);
        return user;
    }


    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();

    }





}
