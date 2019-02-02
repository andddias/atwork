package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Programa;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ProdutoService;

public class ProgramaTelaControle implements Initializable {
	
	@FXML
	private MenuItem menuItemProduto;
	
	@FXML
	private MenuItem menuItemUsuario;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemProdutoAction() {
		CarregarTela("/gui/ProdutoLista.fxml", (ProdutoListaControle controle) -> {
			controle.setProdutoService(new ProdutoService());
			controle.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemUsuarioAction() {
		System.out.println("onMenuItemUsuarioAction");
		/*
		CarregarTela("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
		*/
	}
	
	@FXML
	public void onMenuItemAboutAction() {		
		CarregarTela("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	private synchronized <T> void CarregarTela(String absoluteName, Consumer<T> initializingAction) {		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			//pegando Scene da Tela Principal
			Scene mainScene = Programa.getCenaPrincipal();
			//pegando referencia p/ VBox da tela principal
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//Guardando primeiro filho da MainVBox referente a barra de menu
			Node mainMenu = mainVBox.getChildren().get(0);
			//limpando filhos VBox da cena principal
			mainVBox.getChildren().clear();
			
			//Montando a cena da tela About
			//adicionando o menu principal guardado
			mainVBox.getChildren().add(mainMenu);
			//adicionando todos os filhos VBox About.fxml
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);			
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);			
		}
	}
}
