import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class Questions {

    private Hashtable<String, ArrayList<String>> hashtable;

    public Questions() {
        this.hashtable = new Hashtable<String, ArrayList<String>>();
    }
    public void addSession(String course_id) {
        hashtable.put(course_id, new ArrayList<String>());
    }

    public void removeCourse(String course_id) {
        hashtable.remove(course_id);
    }

    public void addQuestion(String course_id, String question) {
        ArrayList<String> ar = hashtable.get(course_id);
        ar.add(question);
        hashtable.put(course_id, ar);
    }

    public Hashtable<String, ArrayList<String>> getHashtable() {
        return hashtable;
    }
}
