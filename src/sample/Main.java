package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String scene = "sample.fxml";
        File file = new File("user.settings");      //проверяем существует ли такой файл cериализацией, чтобы, если пользователь авторизован, заходить сразу на другое окно
        boolean exists = file.exists();
        if(exists){
            FileInputStream fis = new FileInputStream("user.settings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User user = (User) ois.readObject();
            if(!user.getLogin().equals(""))         // если логин не равен пустой строке(т.к. при выходе по кнопке будем обнулять логин в файле сериализации)
                scene = "main.fxml";

            ois.close();
        }


        Parent root = FXMLLoader.load(getClass().getResource("scenes/" + scene));
        primaryStage.setTitle("Регистрация и авторизация");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

