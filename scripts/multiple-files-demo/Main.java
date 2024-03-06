//SOURCES model/Person.java
import model.Person;

public class Main {

  public static void main(String... args) {
    String name;
    if (args.length > 0) {
      name = args[0];
    } else {
      name = "World";
    }
    Person p = new Person(name);
    System.out.println("Hello " + p.getName());
  }
}
