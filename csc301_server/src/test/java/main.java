import baseFunctions.Course;
import baseFunctions.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class main {
    public static void main(String[] args) {
        User user = new User("email", "123", "pass",
                "f", "l", true);

        user.addCourse(new Course("csc", "301", "ins", "ba"));
        JSONObject obj = new JSONObject(user);
        System.out.println(obj);

        //
        String s = obj.toString();

        JSONObject obj2 = new JSONObject(s);
        JSONArray ja = obj2.getJSONArray("courses");

        Gson gson = new GsonBuilder().create();


        Course c = gson.fromJson(ja.get(0).toString(), Course.class);

        System.out.println(new JSONObject(c));



    }
}
