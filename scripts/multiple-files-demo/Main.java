//SOURCES model/Person.java
import model.Person;

public class Main {

    public static void main(String... args) {
        Person p = new Person(args[0]);
        System.out.println("Hello " + p.getName());
    }
}
