import java.util.Arrays;
import java.util.List;
import static java.lang.System.out;

public class jargs {

  static void printArg(String arg) { out.print(" <" + arg + ">"); }

  public static void main(String... args) {
    out.printf("%d args%s", args.length, args.length > 0 ? ":" : "");
    Arrays.stream(args).forEach(jargs::printArg);
    out.println();
  }
}
