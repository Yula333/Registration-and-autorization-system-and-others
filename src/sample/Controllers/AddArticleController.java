package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB;

import java.io.IOException;
import java.sql.SQLException;

public class AddArticleController {

    @FXML
    private TextField title_field;

    @FXML
    private Button btn_add;

    @FXML
    private TextArea intro_field;

    @FXML
    private TextArea text_field;

    private DB db = new DB();

    @FXML
    void initialize(){
        btn_add.setOnAction(event -> {
            text_field.setStyle("-fx-border-color: #fafafa");
            intro_field.setStyle("-fx-border-color: #fafafa");
            text_field.setStyle("-fx-border-color: #fafafa");

            if (title_field.getCharacters().length() < 3) {
                title_field.setStyle("-fx-border-color: red");
                return;
            } else if (intro_field.getText().length() < 3) {
                intro_field.setStyle("-fx-border-color: red");
                return;
            } else if (text_field.getText().length() < 3) {
                text_field.setStyle("-fx-border-color: red");
                return;
            }

            try {
                db.addArticle(title_field.getCharacters().toString(),intro_field.getText(), text_field.getText());
                Parent root = FXMLLoader.load(getClass().getResource("/sample/scenes/main.fxml"));
                Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Регистрация и авторизация");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
