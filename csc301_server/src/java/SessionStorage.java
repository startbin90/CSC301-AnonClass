import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class SessionStorage {

    private Hashtable<String, SessionInfo> hashtable;

    public SessionStorage() {
        this.hashtable = new Hashtable<String, SessionInfo>();
    }
    public void addSession(String course_id, SessionInfo sessionInfo) {
        hashtable.put(course_id, sessionInfo);
    }

    public void removeCourse(String course_id) {
        hashtable.remove(course_id);
    }

    public SessionInfo getSessionInfo(String course_id) {
        if (!hashtable.containsKey(course_id)) {
            return null;
        }
        return hashtable.get(course_id);
    }

    public Hashtable<String, SessionInfo> getHashtable() {
        return hashtable;
    }
}
