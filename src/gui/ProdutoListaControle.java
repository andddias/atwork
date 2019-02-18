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
import javafx.application.Platform;
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
import model.entities.Produto;
import model.entities.ProdutoCategoria;
import model.services.ProdutoService;

public class ProdutoListaControle implements Initializable, AlteracaoDadosListener {

	private ProdutoService produtoService;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private Button btExcluir;
	
	@FXML
	private Button btEditar;
	
	@FXML
	private TextField txtPesquisar;

	@FXML
	private TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, Integer> tableColumnId_produto;
	
	@FXML
	private TableColumn<Produto, ProdutoCategoria> tableColumnP_cat;

	@FXML
	private TableColumn<Produto, String> tableColumnP_codigo;

	@FXML
	private TableColumn<Produto, String> tableColumnP_desc;

	@FXML
	private TableColumn<Produto, Double> tableColumnP_venda;
	
	@FXML
	private TableColumn<Produto, Double> tableColumnP_custo;
	
	@FXML
	private TableColumn<Produto, String> tableColumnP_codBarras;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnP_fab;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnP_forn;
	
	@FXML
	private TableColumn<Produto, String> tableColumnP_cfab;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnP_lucro;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnP_maxDesc;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnP_status;
	
	private ObservableList<Produto> obsList;

	@FXML
	public void OnBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);		
		ProdutoCategoria produtoCategoria = new ProdutoCategoria();
		Produto produto = new Produto();
		produto.setProdutoCategoria(produtoCategoria);
		createDialogForm(produto, "/gui/ProdutoFormulario.fxml", parentStage);		
	}
	
	@FXML
	public void OnBtExcluirAction(ActionEvent event) {
		Produto prod = tableViewProduto.getSelectionModel().getSelectedItem();		
		ExcluirProduto(prod);
	}
	
	@FXML
	public void OnBtEditarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Produto produto = tableViewProduto.getSelectionModel().getSelectedItem();		
		createDialogForm(produto, "/gui/ProdutoFormulario.fxml", parentStage);
	}
	
	@FXML
	public void OnTxtPesquisarAction(ActionEvent event) {
		updatePesquisaTableView(txtPesquisar.getText());		
	}	

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId_produto.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
		tableColumnP_cat.setCellValueFactory(new PropertyValueFactory<>("produtoCategoria"));
		tableColumnP_codigo.setCellValueFactory(new PropertyValueFactory<>("p_codigo"));
		tableColumnP_desc.setCellValueFactory(new PropertyValueFactory<>("p_desc"));
		tableColumnP_venda.setCellValueFactory(new PropertyValueFactory<>("p_venda"));
		tableColumnP_custo.setCellValueFactory(new PropertyValueFactory<>("p_custo"));
		tableColumnP_codBarras.setCellValueFactory(new PropertyValueFactory<>("p_codBarra"));
		tableColumnP_fab.setCellValueFactory(new PropertyValueFactory<>("p_fab"));
		tableColumnP_forn.setCellValueFactory(new PropertyValueFactory<>("p_forn"));
		tableColumnP_cfab.setCellValueFactory(new PropertyValueFactory<>("p_cfab"));
		tableColumnP_lucro.setCellValueFactory(new PropertyValueFactory<>("p_lucro"));
		tableColumnP_maxDesc.setCellValueFactory(new PropertyValueFactory<>("p_maxDesc"));
		tableColumnP_status.setCellValueFactory(new PropertyValueFactory<>("p_status"));

		// comandos para fazer tableViewProduto acompanhar o tamanho da janela
		Stage stage = (Stage) Programa.getCenaPrincipal().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());		
	}

	public void updateTableView() {
		if (produtoService == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		
		List<Produto> list = produtoService.findAll();		
		obsList = FXCollections.observableArrayList(list);		
		tableViewProduto.setItems(obsList);		
	}
	
	@Override
	public void onAlteracaoDados(Produto prod) {
		updateTableView();
		Platform.runLater(() ->
		  {
			  tableViewProduto.refresh();
			  tableViewProduto.requestFocus();
			  tableViewProduto.scrollTo(prod);
		  });
	}	
	
	public void updatePesquisaTableView(String string) {
		if (produtoService == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		try {
			List<Produto> list = produtoService.findCodigoOuDescricao(string);
			obsList = FXCollections.observableArrayList(list);
			tableViewProduto.setItems(obsList);
		}
		catch (DbIntegrityException e) {
			Alerts.showAlert("Erro ao pesquisar descrição", null, e.getMessage(), AlertType.ERROR);
		}	
	}	
	
	private void createDialogForm(Produto produto, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoFormularioControle controller = loader.getController();
			controller.setProduto(produto);
			controller.setProdutoService(new ProdutoService());			
			controller.subscreverAlteracaoDadosListener(this);
			controller.atualizarDadosFormulario();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do produto");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a visualização", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void ExcluirProduto(Produto obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação","Você tem certeza que quer excluir este item"
				+ " Código: " + obj.getP_codigo() + " - Descrição: " + obj.getP_desc() + " ?");
		if (result.get() == ButtonType.OK) {
			if (produtoService == null) {
				throw new IllegalStateException("Serviço está nulo");
			}
			try {
				produtoService.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover o objeto", null, e.getMessage(), AlertType.ERROR);
			}			
		}
	}	
}
