package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Programa extends Application {
	
	private static Scene cenaPrincipal;
	
	@Override
	public void start(Stage estagioPrimario) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ProgramaTela.fxml"));
			ScrollPane scrollPane = loader.load();
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			cenaPrincipal = new Scene(scrollPane);
			estagioPrimario.setScene(cenaPrincipal);
			estagioPrimario.setTitle("Programa AT Work");
			estagioPrimario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getCenaPrincipal() {
		return cenaPrincipal;
	}

	public static void main(String[] args) {
		launch(args);
	}
}