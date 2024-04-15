import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Chatbot extends Application {

    private StringProperty responseProperty = new SimpleStringProperty("");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chatbot");

        Label chatLabel = new Label("Chatbot");
        chatLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16pt;");
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        TextField inputField = new TextField();
        inputField.setPromptText("Type your message here...");
        Button sendButton = new Button("Send");

        VBox chatLayout = new VBox(10);
        chatLayout.setAlignment(Pos.CENTER);
        chatLayout.getChildren().addAll(chatLabel, chatArea, inputField, sendButton);

        Task<Void> chatTask = new Task<>() {
            @Override
            protected Void call() {
                String input = inputField.getText();
                if (!input.isEmpty()) {
                    String response = queryAPI(input);
                    if (response != null) {
                        responseProperty.set(response);
                    } else {
                        responseProperty.set("I'm sorry, I didn't understand that.");
                    }
                }
                return null;
            }
        };

        sendButton.setOnAction(event -> {
            String userMessage = inputField.getText();
            chatArea.appendText("You: " + userMessage + "\n");
            new Thread(chatTask).start();
        });

        responseProperty.addListener((observable, oldValue, newValue) -> {
            chatArea.appendText("Bot: " + newValue + "\n");
            inputField.clear();
        });

        Scene scene = new Scene(chatLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String queryAPI(String input) {
        // Mock API call, replace this with actual API integration
        if (input.toLowerCase().contains("hello")) {
            return "Hello! How can I assist you?";
        } else if (input.toLowerCase().contains("bye")) {
            return "Goodbye! Have a great day!";
        } else if (input.toLowerCase().contains("weather")) {
            return "The weather today is sunny.";
        } else if (input.toLowerCase().contains("time")) {
            return "The current time is 10:00 AM.";
        } else if (input.toLowerCase().contains("news")) {
            return "Here are the latest news headlines...";
        } else {
            return null; // Return null for unknown input
        }
    }
}
