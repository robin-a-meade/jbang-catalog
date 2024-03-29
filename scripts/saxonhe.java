// This class uses "The Saxon XSLT and XQuery Processor from Saxonica Limited"
// Homepage: https://www.saxonica.com

// This class, `saxonhe`, uses the latest in the Saxon-HE 12.x line.

// For Saxon-HE 11.x, use the `saxonhe11` class.

//DEPS net.sf.saxon:Saxon-HE:12.4
//DEPS jline:jline:2.14.6
//DEPS org.ccil.cowan.tagsoup:tagsoup:1.2.1
//DEPS nu.validator:htmlparser:1.4.16

import java.util.Arrays;

public class saxonhe {

    static String usage = "usage: saxonhe (transform|query|gizmo) OPTIONS";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("error: a subcommand is required");
            System.err.println(usage);
            System.exit(1);
        }

        String command = args[0];
        String[] remainingArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (command) {
            case "transform":
                // https://www.saxonica.com/html/documentation12/javadoc/net/sf/saxon/Transform.html
                // https://www.saxonica.com/documentation12/index.html#!using-xsl/commandline
                net.sf.saxon.Transform.main(remainingArgs);
                break;
            case "query":
                // https://www.saxonica.com/html/documentation12/javadoc/net/sf/saxon/Query.html
                // https://www.saxonica.com/documentation12/index.html#!using-xquery/commandline
                net.sf.saxon.Query.main(remainingArgs);
                break;
            case "gizmo":
                // https://www.saxonica.com/html/documentation12/javadoc/net/sf/saxon/Gizmo.html
                // https://www.saxonica.com/documentation12/index.html#!gizmo
                net.sf.saxon.Gizmo.main(remainingArgs);
                break;
            default:
                System.err.println("error: invalid command: " + command);
                System.err.println(usage);
                System.exit(1);
        }
    }
}
