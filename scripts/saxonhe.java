// This class uses "The Saxon XSLT and XQuery Processor from Saxonica Limited"
// Homepage: https://www.saxonica.com

// We're sticking with the 11.x line for now because the Gizmo utility in 
// the 12.x line requires Saxon-PE (due to pulling in XQuery 4.0).
//DEPS net.sf.saxon:Saxon-HE:11.6

// Other dependencies
//DEPS jline:jline:2.14.6
//DEPS org.ccil.cowan.tagsoup:tagsoup:1.2.1
//DEPS nu.validator:htmlparser:1.4.16

import java.util.Arrays;

import net.sf.saxon.Transform;
import net.sf.saxon.Query;
import net.sf.saxon.Gizmo;

public class saxonhe {

    static String usage = "saxonhe (transform|query|gizmo) OPTIONS";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Missing command");
            System.err.println(usage);
            System.exit(1);
        }

        String command = args[0];
        String[] remainingArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (command) {
            case "transform":
                // https://www.saxonica.com/html/documentation12/javadoc/net/sf/saxon/Transform.html
                // https://www.saxonica.com/documentation12/index.html#!using-xsl/commandline
                new Transform().doTransform(remainingArgs);
                break;
            case "query":
                // https://www.saxonica.com/html/documentation12/javadoc/net/sf/saxon/Query.html
                // https://www.saxonica.com/documentation12/index.html#!using-xquery/commandline
                new Query().doQuery(remainingArgs);
                break;
            case "gizmo":
                // https://www.saxonica.com/html/documentation12/javadoc/net/sf/saxon/Gizmo.html
                // https://www.saxonica.com/documentation12/index.html#!gizmo
                new Gizmo().main(remainingArgs);
                break;
            default:
                System.err.println("Invalid command: " + command);
                System.err.println(usage);
                System.exit(1);
        }
    }
}