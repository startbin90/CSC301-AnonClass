import com.mysql.cj.xdevapi.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class SessionStorage {

    private static Hashtable<String, SessionInfo> hashtable = new Hashtable<String, SessionInfo>();

    public static void addSession(String course_id, SessionInfo sessionInfo) {
        hashtable.put(course_id, sessionInfo);
    }

    public static void removeCourse(String course_id) {
        hashtable.remove(course_id);
    }

    public static SessionInfo getSessionInfo(String course_id) {
        if (!hashtable.containsKey(course_id)) {
            return null;
        }
        return hashtable.get(course_id);
    }
    public static void addSessionQuestion(String num, JSONObject q) {
        SessionInfo si = hashtable.get(num);
        si.addQuestions(q);
        hashtable.put(num, si);
    }

    public static List<JSONObject> getQuestions(String num) {
        return hashtable.get(num).getQuestions();
    }

    public static void StudentJoin(String num, String email, Tuple location) {
        SessionInfo si = SessionStorage.getSessionInfo(num);
        si.joinSession(email, location);
        hashtable.put(num, si);
    }

    public static List<String> getFiles(String course_id) {
        return getSessionInfo(course_id).getFiles();
    }

    public static JSONArray getFileNames(String course_id) {
        JSONArray jsonArray = new JSONArray();
        for (String s :getFiles(course_id)) {
            jsonArray.put(s);
        }
        return jsonArray;
    }

    public static void addFile(String course_id, String filename) {
        SessionInfo si = getSessionInfo(course_id);
        si.addFile(filename);
        hashtable.put(course_id, si);
    }

}
