package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Programa;
import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.ProdutoCategoria;
import model.services.ProdutoCategoriaService;

public class ProdutoCategoriaListaControle implements Initializable, AlteracaoDadosListener {

	private ProdutoCategoriaService service;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private Button btExcluir;
	
	@FXML
	private Button btEditar;
	
	@FXML
	private TextField txtPesquisar;

	@FXML
	private TableView<ProdutoCategoria> tableView;

	@FXML
	private TableColumn<ProdutoCategoria, Integer> tableColumnId_cat;
	
	@FXML
	private TableColumn<ProdutoCategoria, String> tableColumnP_cat;
	
	private ObservableList<ProdutoCategoria> obsList;

	@FXML
	public void OnBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		ProdutoCategoria obj = new ProdutoCategoria();
		createDialogForm(obj, "/gui/ProdutoCategoriaFormulario.fxml", parentStage);		
	}
	
	@FXML
	public void OnBtExcluirAction(ActionEvent event) {
		ProdutoCategoria obj = tableView.getSelectionModel().getSelectedItem();
		ExcluirProdutoCategoria(obj);
	}
	
	@FXML
	public void OnBtEditarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		ProdutoCategoria obj = tableView.getSelectionModel().getSelectedItem();
		createDialogForm(obj, "/gui/ProdutoCategoriaFormulario.fxml", parentStage);
	}
	
	@FXML
	public void OnTxtPesquisarAction(ActionEvent event) {
		updatePesquisaTableView(txtPesquisar.getText());		
	}	

	public void setProdutoCategoriaService(ProdutoCategoriaService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId_cat.setCellValueFactory(new PropertyValueFactory<>("id_cat"));
		tableColumnP_cat.setCellValueFactory(new PropertyValueFactory<>("p_cat"));		
		
		Stage stage = (Stage) Programa.getCenaPrincipal().getWindow();
		tableView.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		List<ProdutoCategoria> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableView.setItems(obsList);
	}
	
	@Override
	public void onAlteracaoDados(String codigo) {
		updatePesquisaTableView(codigo);		
	}
	
	public void updatePesquisaTableView(String string) {
		if (service == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		try {
			List<ProdutoCategoria> list = service.findDescricao(string);
			obsList = FXCollections.observableArrayList(list);
			tableView.setItems(obsList);
		}
		catch (DbIntegrityException e) {
			Alerts.showAlert("Erro ao pesquisar descrição", null, e.getMessage(), AlertType.ERROR);
		}	
	}	
	
	private void createDialogForm(ProdutoCategoria obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoCategoriaFormularioControle controller = loader.getController();
			controller.setProdutoCategoria(obj);
			controller.setProdutoCategoriaService(new ProdutoCategoriaService());
			controller.subscreverAlteracaoDadosListener(this);
			controller.atualizarDadosFormulario();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados Categoria de Produto");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a visualização", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void ExcluirProdutoCategoria(ProdutoCategoria obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação","Você tem certeza que quer excluir este item"
				+ " Descrição: " + obj.getP_cat() + " ?");
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serviço está nulo");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover o objeto", null, e.getMessage(), AlertType.ERROR);
			}			
		}
	}	
}
