import controllers.LoginController;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HiroMarineApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Muat tampilan utama
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent mainRoot = mainLoader.load();
        MainController mainController = mainLoader.getController();

        // Muat tampilan login
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent loginRoot = loginLoader.load();
        LoginController loginController = loginLoader.getController();

        // Setel MainController di LoginController
        loginController.setMainController(mainController);

        // Atur dan tampilkan scene
        primaryStage.setTitle("Hiro's Marine - SEA EDUCATION");
        Scene scene = new Scene(loginRoot);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
