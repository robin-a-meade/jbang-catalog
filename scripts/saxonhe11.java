// This class uses "The Saxon XSLT and XQuery Processor from Saxonica Limited"
// Homepage: https://www.saxonica.com

// This class, saxonhe11, uses latest release in the Saxon-HE 11.x line. It for
// comparing results to the 12.x line.

// For the 12.x line, use saxonhe.

//DEPS net.sf.saxon:Saxon-HE:11.6
//DEPS jline:jline:2.14.6
//DEPS org.ccil.cowan.tagsoup:tagsoup:1.2.1
//DEPS nu.validator:htmlparser:1.4.16

import java.util.Arrays;

public class saxonhe11 {

    static String usage = "usage: saxonhe11 (transform|query|gizmo) OPTIONS";

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
                System.err.println("error: invalid subcommand: " + command);
                System.err.println(usage);
                System.exit(1);
        }
    }
}
