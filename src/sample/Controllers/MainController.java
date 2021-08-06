package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DB;
import sample.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private Button btn_exit, btn_add_article;

    @FXML
    private VBox paneVBox;

    private DB db = new DB();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ResultSet res = null;
            res = db.getArticles();

        while(res.next()){
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/sample/scenes/article.fxml"));

                Label title = (Label) node.lookup("#title");    //устанавливаем в объект в Scene Builder по id = title
                title.setText(res.getString("title"));  // берем поле title из БД

                Label intro = (Label) node.lookup("#intro");     //устанавливаем в объект в Scene Builder по id = intro
                intro.setText(res.getString("intro"));  // берем поле intro из БД

                final Node nodeSet = node;
                node.setOnMouseEntered(event -> {           //добавим обработчик события при наведении мышки на объект
                    nodeSet.setStyle("-fx-background-color: #707173");
                });
                node.setOnMouseExited(event -> {            //добавим обработчик события при отведении мышки с объекта
                    nodeSet.setStyle("-fx-background-color: #343434");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(10);
        }

        // Переход пользователя с одного окна в другое после выхода
        btn_exit.setOnAction(event -> {
            try {
                FileOutputStream fos = new FileOutputStream("user.settings");
                ObjectOutputStream oos = new ObjectOutputStream(fos);      //объект для сериализации

                oos.writeObject(new User(""));

                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/sample.fxml"));
            Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Регистрация и авторизация");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        });

    btn_add_article.setOnAction(event -> {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/addArticle.fxml"));
            Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Регистрация и авторизация");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }
}
