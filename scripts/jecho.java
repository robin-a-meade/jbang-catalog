import java.util.Arrays;
import java.util.stream.Collectors;
import static java.lang.System.out;

public class jecho {

  public static void main(String... args) {
    out.println(Arrays.stream(args).collect(Collectors.joining(" ")));

    // Much simpler alternative:
    //     out.println(String.join(" ", args));
  }

}
