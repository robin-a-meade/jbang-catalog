import java.io.File;

public class jlist {
    public static void main(String[] args) {
        System.out.println("jlist 0.1.3 - Take an arg");
        // Get the current working directory
        String userDir = System.getProperty("user.dir");

        // Use the provided directory argument if available, otherwise use the current working directory
        String directory = (args.length > 0) ? args[0] : userDir;
        System.out.println("Directory: " + directory);

        // List the files in the specified directory
        File dir = new File(directory);
        File[] filesList = dir.listFiles();

        if (filesList != null) {
            System.out.println("Files in the specified directory:");
            for (File file : filesList) {
                if (file.isDirectory()) {
                    System.out.println(file.getName() + "/");
                } else {
                    System.out.println(file.getName());
                }
            }
        } else {
            System.out.println("The directory is empty or an error occurred.");
        }
    }
}

