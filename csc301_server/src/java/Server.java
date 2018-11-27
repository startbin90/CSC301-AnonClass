import org.json.JSONObject;
public class Server {
    public static void main(String[] args) throws Exception{
        SocketServer sc = new SocketServer();
        sc.runServer();

//        AnnonclassDataBase db = new AnnonclassDataBase();
//        db.connectDB("jdbc:postgresql://localhost:5432/anonclass");
//        db.connectDB("jdbc:postgresql://anonclass.cszu4qtoxymw.us-east-1.rds.amazonaws.com:5432/mydb");
//        JSONObject info = new JSONObject();
//        info.put("email", "123@123.com");
//        info.put("pwdHash", "1123");
//        info.put("utorid", "chuchun9");
//        info.put("firstName", "ChunTung");
//        info.put("lastName", "Chu");
//        info.put("studentFlag", true);
//        db.signup(info);
//        System.out.println(info);
//        JSONObject info2 = new JSONObject();
//        info2.put("email", "123@123.com");
//        info2.put("password", "1123");
//        System.out.println(db.login(info2));
//
//        JSONObject info3 = new JSONObject();
//        info3.put("email", "123@123.com");
//        info3.put("course_name", "course1");
//        info3.put("section_number", "section1");
//        System.out.println(db.enroll_course(info3));
    }


}
