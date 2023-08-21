public class Person {
    public String name;
    public String function;

    public Person(String name, String function) {
        this.name = name;
        this.function = function;
    }

    @Override
    public String toString() {
        return this.name + " [" + this.function + "]";
    }
}
