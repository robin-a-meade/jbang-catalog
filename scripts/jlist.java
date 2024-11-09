import java.io.File;

public class jlist {
    public static void main(String[] args) {
        System.out.println("jlist 0.1.2 - Listing of /");
        // Get the current working directory
        String pwd = System.getProperty("user.dir");
        System.out.println("Current working directory: " + pwd);

        // List the files in the current working directory
        //File dir = new File(pwd);
        File dir = new File("/");
        File[] filesList = dir.listFiles();

        if (filesList != null) {
            System.out.println("Files in the current working directory:");
            for (File file : filesList) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("The directory is empty or an error occurred.");
        }
    }
}

