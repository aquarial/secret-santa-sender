import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox button_box = new VBox();
        button_box.setSpacing(40);
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
            compute.setDisable(false);
            send.setDisable(true);
        });
        compute.setOnAction(event -> {
            compute.setDisable(false);
            send.setDisable(false);
        });

        primaryStage.setScene(new Scene(button_box));
        primaryStage.setTitle("White Elephant");
        primaryStage.show();
    }
}