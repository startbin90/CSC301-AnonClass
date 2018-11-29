
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SessionInfo {
    private Tuple coordinate;
    private List<JSONObject> questions = new ArrayList<JSONObject>();
    private List<String> students = new ArrayList<String>();
    public SessionInfo(Tuple coordinate) {
        this.coordinate = coordinate;
    }

    public Tuple getCoordinate() {
        return coordinate;
    }

    public List<JSONObject> getQuestions() {
        return questions;
    }

    public void addQuestions(JSONObject questioninfo) {
        this.questions.add(questioninfo);
    }

    public int joinSession(String email, Tuple location) {
//        if (location.getX() == coordinate.getX() && location.getY() == coordinate.getY()) {
//            this.students.add(email);
//            return 0;
//        } else {
//            return 1;
//        }
        this.students.add(email);
        return 0;
    }
}
