import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;
import solver.GiftRelation;
import solver.Maybe;
import solver.Solver;
import solver.SolverFactory;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

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
                Alert err = new Alert(Alert.AlertType.ERROR, ms.errorMessage);
                err.setResizable(true);
                err.setWidth(400);
                err.showAndWait();
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
                Alert warning = new Alert(Alert.AlertType.WARNING, "Someone in the gift exchange\n" +
                        "will give to a person in their family!\n" +
                        "\n" +
                        "Recompute a solution or\n" +
                        "change the groupings in data.txt");

                warning.setResizable(true);
                warning.setWidth(400);
                warning.showAndWait();
            }
        });


        send.setOnAction(event -> {
            send.setDisable(true);
            compute.setDisable(true);
            load.setDisable(true);
            send.setText("Sending...");

            HashMap<String, String> login = gmailLoginPopupWindow(primaryStage);


            final Task<String> task = new Task<String>() {
                @Override
                public String call() {

                    if (login != null) {
                        Sender sender = new Sender(login.get("username"), login.get("password"));
                        for (GiftRelation giftRelation : solver.giftingPairs()) {
                            sender.sendEmail(giftRelation.giver_email, giftRelation.receiver_name);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                return null;
                            }
                        }
                    }
                    return null;
                }
            };

            task.setOnSucceeded(result -> {
                send.setDisable(false);
                compute.setDisable(false);
                load.setDisable(false);
                send.setText("Send");
            });

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
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

        username.positionCaret("username".length());
        username.selectHome();

        AtomicReference<HashMap<String, String>> credentials = new AtomicReference<>(null);
        login.setOnAction(e -> {
            if (EmailAddressValidator.isValid(username.getText() + "@gmail.com")) {
                credentials.set(new HashMap<>());
                credentials.get().put("username", username.getText());
                credentials.get().put("password", password.getText());
                popupStage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid email username (just the part before the @)").show();
                username.setStyle("-fx-background: #ff705a");
                credentials.set(null);
            }
        });

        popupStage.showAndWait();

        return credentials.get();
    }

}