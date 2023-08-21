public class Task {
    public String title;
    public String description;
    public String endDate;
    public String priority;
    public String status;

    public Task(
            String title,
            String description,
            String endDate,
            String priority,
            String status) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.priority = priority;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.title + " [" + this.priority + "]" + " [" + this.status + "]\n- " + this.description + "\n- "
                + this.endDate;
    }
}
