import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class Solver {
    private static List<String> loadFileData() throws IOException {
        return Files.readAllLines(Paths.get("data.txt"));
    }

    @SuppressWarnings({"unused", "UnusedAssignment"})
    public static void main(String[] args) {

        String errorMessage;
        try {

            System.out.println(Solver.loadFileData());

        } catch (NoSuchFileException e) {

            String path;
            try {
                path = Paths.get("data.txt").toAbsolutePath().toString();
            } catch (Exception ignored) {
                path = "data.txt";
            }

            errorMessage = "File: " + path + " does not exist!";

        } catch (IOException e) {
            errorMessage = e.getLocalizedMessage();
        }
    }
}
