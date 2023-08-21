import java.util.ArrayList;
import java.util.List;

public class Team {
    public String name;
    public List<Person> persons;
    public List<Task> tasks;

    public Team(String name) {
        this.name = name;
        this.persons = new ArrayList<Person>();
        this.tasks = new ArrayList<Task>();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
