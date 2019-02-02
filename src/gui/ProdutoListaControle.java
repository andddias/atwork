package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Programa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Produto;
import model.services.ProdutoService;

public class ProdutoListaControle implements Initializable {

	private ProdutoService service;
	
	@FXML
	private Button btNovo;

	@FXML
	private TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, Integer> tableColumnId_produto;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnP_cat;

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
		System.out.println("OnBtNovoAction");
		/*
		Stage parentStage = Utils.currentStage(event);
		Produto obj = new Produto();
		createDialogForm(obj, "/gui/ProdutoForm.fxml", parentStage);
		*/
	}

	public void setProdutoService(ProdutoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId_produto.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
		tableColumnP_cat.setCellValueFactory(new PropertyValueFactory<>("p_cat"));
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
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Produto> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProduto.setItems(obsList);
		//initEditButtons();
		//initRemoveButtons();
	}
	/*

	private void createDialogForm(Produto obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoFormController controller = loader.getController();
			controller.setProduto(obj);
			controller.setProdutoService(new ProdutoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Produto data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	

	@Override
	public void onDataChange() {
		updateTableView();
	}
	

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ProdutoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Produto obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}			
		}
	}
	*/

}
