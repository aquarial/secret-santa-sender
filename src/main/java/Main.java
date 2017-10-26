import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import solver.Maybe;
import solver.Solver;
import solver.SolverFactory;

import java.util.HashMap;

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

        Button load = new Button("Load text data");
        load.setTooltip(new Tooltip("Load text file for participant data"));
        load.setPrefSize(200, 50);
        button_box.getChildren().add(load);

        Button compute = new Button("Compute Secret Santas");
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


        send.setOnAction(event -> {
            HashMap<String, String> login = gmailLoginPopupWindow(primaryStage);
            login.get("username");
            login.get("password");

        });


        primaryStage.setScene(new Scene(button_box));
        primaryStage.setTitle("White Elephant");
        primaryStage.show();
    }

    private HashMap<String, String> gmailLoginPopupWindow(Stage primaryStage) {
        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(5);

        layout.getChildren().add(new Label("Your GMail Login"));

        TextField username = new TextField("username");
        username.setPromptText("username");
        layout.getChildren().add(username);

        PasswordField password = new PasswordField();
        password.setPromptText("password");
        layout.getChildren().add(password);

        Button login = new Button("Login");
        layout.getChildren().add(login);

        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setScene(new Scene(layout));

        login.setOnAction(e -> popupStage.close());
        username.positionCaret("username".length());
        username.selectHome();

        popupStage.showAndWait();


        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", username.getText());
        credentials.put("password", password.getText());
        return credentials;
    }

}