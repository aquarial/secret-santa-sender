package solver;

import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SolverFactory {


    public static Maybe<Solver> parseSolver(String fileName) {
        Maybe<List<String>> fileLines = loadFileData(fileName);
        if (fileLines.hadError)
            return new Maybe<>("error", fileLines.errorMessage);

        Maybe<List<Node>> nodes = parseNodes(fileLines.result);
        if (nodes.hadError)
            return new Maybe<>("error", nodes.errorMessage);

        return new Maybe<>(new Solver(nodes.result));
    }

    private static Maybe<List<Node>> parseNodes(List<String> nodeData) {
        List<Node> people = new ArrayList<>();
        boolean parseFailure = false;
        List<String> failedParses = new ArrayList<>();

        for (String line : nodeData) {
            if (line.length() == 0 || line.startsWith("#"))
                continue;

            try {
                String[] info = line.split(", +");

                Node parsed = new Node();
                parsed.groupID = Integer.parseInt(info[0]);
                parsed.name = info[1];
                parsed.email = info[2];


                if (EmailAddressValidator.isValid(parsed.email)) {
                    people.add(parsed);
                } else {
                    parseFailure = true;
                    failedParses.add(line + " invalid email!");
                }

            } catch (Exception ignored) {
                parseFailure = true;
                failedParses.add(line);
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

    private static Maybe<List<String>> loadFileData(String fileName) {

        List<String> data;

        try {
            data = Files.readAllLines(Paths.get(fileName));

        } catch (NoSuchFileException e) {
            return new Maybe<>("error", "File: " + Paths.get("data.txt").toAbsolutePath().toString() + " does not exist!");
        } catch (IOException e) {
            return new Maybe<>("error", e.getLocalizedMessage());
        }

        return new Maybe<>(data);
    }


}
