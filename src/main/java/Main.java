import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import solver.Maybe;
import solver.Solver;
import solver.SolverFactory;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Solver solver;

    @Override
    public void start(Stage primaryStage) {
        VBox button_box = new VBox();
        button_box.setSpacing(20);
        button_box.setPadding(new Insets(10));

        Button load = new Button("Load");
        load.setTooltip(new Tooltip("Load text file for participant data"));
        load.setPrefSize(200, 50);
        button_box.getChildren().add(load);

        Button compute = new Button("Compute");
        compute.setTooltip(new Tooltip("Compute a white elephant gift cycle"));
        compute.setDisable(true);
        compute.setPrefSize(200, 50);
        button_box.getChildren().add(compute);

        Button send = new Button("Send");
        send.setTooltip(new Tooltip("Send emails!"));
        send.setDisable(true);
        send.setPrefSize(200, 50);
        button_box.getChildren().add(send);

        load.setOnAction(event -> {
            compute.setDisable(true);
            send.setDisable(true);

            Maybe<Solver> ms = SolverFactory.parseSolver("data.txt");
            if (ms.hadError) {
                new Alert(Alert.AlertType.ERROR, ms.errorMessage).showAndWait();
                solver = null;
            } else {
                solver = ms.result;
                compute.setDisable(false);
                compute.setText("Compute");
            }
        });
        compute.setOnAction(event -> {
            send.setDisable(false);
            compute.setText("Recompute");
            solver.generateSolution();
            if (!solver.validSolution()) {
                new Alert(Alert.AlertType.WARNING, "Someone in the gift exchange\n" +
                        "will give to a person in their family!\n" +
                        "\n" +
                        "Recompute a solution or\n" +
                        "change the groupings in data.txt").showAndWait();
            }
        });





        primaryStage.setScene(new Scene(button_box));
        primaryStage.setTitle("White Elephant");
        primaryStage.show();
    }
}