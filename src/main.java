/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tohar
 */
public class main extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/menu utama.fxml"));
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest((event) -> System.exit(0));
//        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
