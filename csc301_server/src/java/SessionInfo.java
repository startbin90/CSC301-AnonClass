
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SessionInfo {
    private Tuple coordinate;
    private List<JSONObject> questions = new ArrayList<JSONObject>();
    private List<String> students = new ArrayList<String>();
    private List<String> filenames = new ArrayList<String>();
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

    public void joinSession(String email, Tuple location) {
        this.students.add(email);
    }

    public List<String> getFiles() {
        return filenames;
    }

    public void addFile(String path) {
        this.filenames.add(path);
    }
}
