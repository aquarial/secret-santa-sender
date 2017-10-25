import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private static Maybe<List<Node>> loadFileData() {

        List<String> data;

        try {
            data = Files.readAllLines(Paths.get("data.txt"));

        } catch (NoSuchFileException e) {
            return new Maybe<>("error", "File: " + Paths.get("data.txt").toAbsolutePath().toString() + " does not exist!");
        } catch (IOException e) {
            return new Maybe<>("error", e.getLocalizedMessage());
        }

        List<Node> people = new ArrayList<>();
        boolean parseFailure = false;
        List<String> failedParses = new ArrayList<>();

        for (String d : data) {
            if (d.length() == 0 || d.startsWith("#"))
                continue;

            try {
                String[] info = d.split(", +");

                Node parsed = new Node();
                parsed.groupID = Integer.parseInt(info[0]);
                parsed.name = info[1];
                parsed.email = info[2];
                people.add(parsed);
            } catch (Exception ignored) {
                parseFailure = true;
                failedParses.add(d);
            }
        }

        if (parseFailure) {
            StringBuilder error = new StringBuilder();
            for (String e : failedParses)
                error.append("Failed to parse: \"").append(e).append("\"\n");

            return new Maybe<>("error", error.toString());
        }

        return new Maybe<>(people);
    }

    public static void main(String[] args) {
        System.out.println(loadFileData());
    }
}
