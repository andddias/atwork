package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Produto;
import model.entities.ProdutoCategoria;
import model.exceptions.ValidationException;
import model.services.ProdutoCategoriaService;
import model.services.ProdutoService;

public class ProdutoFormularioControle implements Initializable {
	
	private Produto produto;	

	private ProdutoService produtoService;
	
	private ProdutoCategoria produtoCategoria;
	
	private ProdutoCategoriaService produtoCategoriaService;
	
	private List<AlteracaoDadosListener> alteracaoDadosListeners = new ArrayList<>();

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;
	
	@FXML
	private Button btEditarCategoria;
	

	@FXML
	private Label lblId_produto;
	
	@FXML
	private Label lblProdutoCategoria;

	@FXML
	private TextField txtP_codigo;

	@FXML
	private TextField txtP_codBarras;

	@FXML
	private TextField txtP_desc;
	
	@FXML
	private TextField txtId_cat;

	@FXML
	private TextField txtP_custo;

	@FXML
	private TextField txtP_venda;

	@FXML
	private TextField txtP_lucro;

	@FXML
	private TextField txtP_maxDesc;

	@FXML
	private TextField txtP_cfab;

	/*
	 * @FXML private ComboBox<T> cboxP_cat;
	 * 
	 * @FXML private ComboBox<T> cboxP_forn;
	 * 
	 * @FXML private ComboBox<T> cboxP_fab;
	 * 
	 * @FXML private ComboBox<T> cboxP_status;
	 */
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}	
	
	public void setProdutoCategoria(ProdutoCategoria produtoCategoria) {
		this.produtoCategoria = produtoCategoria;
	}

	public void setProdutoCategoriaService(ProdutoCategoriaService produtoCategoriaService) {
		this.produtoCategoriaService = produtoCategoriaService;
	}

	public void subscreverAlteracaoDadosListener(AlteracaoDadosListener listener) {
		alteracaoDadosListeners.add(listener);
	}
	
	@FXML
	public void OnTxtIdCategoriaAction(ActionEvent event) {
		atualizarLabelCategoria(Integer.parseInt(txtId_cat.getText()));		
	}
	
	private void atualizarLabelCategoria(Integer id_cat) {		
		produtoCategoria = produto.getProdutoCategoria();
		setProdutoCategoriaService(new ProdutoCategoriaService());		
		if(produtoCategoriaService.findById(id_cat) != null) {
			setProdutoCategoria(produtoCategoriaService.findById(id_cat));
			lblProdutoCategoria.setText(produtoCategoria.getP_cat());		
		}
		else {			
			Alerts.showAlert("Erro Consulta Produto Categoria", null, "Não Existe categoria de Poduto com o ID digitado!"
					+ "\nO valor será retornado para o Original", AlertType.ERROR);
			txtId_cat.setText(String.valueOf(produtoCategoria.getId_cat()));
			lblProdutoCategoria.setText(produtoCategoria.getP_cat());
		}			
	}

	@FXML
	public void OnBtPesquisarCategoriaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		produtoCategoria = produto.getProdutoCategoria();
		ProdutoCategoria produtoCategoriaSelecao = createDialogForm(produtoCategoria, "/gui/ProdutoCategoriaLista.fxml", parentStage);
		if(produtoCategoriaSelecao != null) {
			txtId_cat.setText(String.valueOf(produtoCategoriaSelecao.getId_cat()));
			atualizarLabelCategoria(produtoCategoriaSelecao.getId_cat());
		}		
	}

	@FXML
	public void OnBtSalvarAction(ActionEvent event) {
		if (produto == null) {
			throw new IllegalStateException("Produto está nulo");
		}
		if (produtoService == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		try {
			produto = getFormularioDados();
			produtoService.saveOrUpdate(produto);			
			notificarAlteracaoDadosListeners(produto);
			Utils.currentStage(event).close();
		}
		catch (DbIntegrityException e) {
			Alerts.showAlert("Erro ao salvar o produto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificarAlteracaoDadosListeners(Produto prod) {
		for (AlteracaoDadosListener listener : alteracaoDadosListeners) {
			listener.onAlteracaoDados(prod);
		}		
	}

	@FXML
	public void OnBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNode();

	}
	
	private void initializeNode() {
		Constraints.setTextFieldInteger(txtP_codBarras);
		Constraints.setTextFieldInteger(txtId_cat);
		Constraints.setTextFieldInteger(txtP_codigo);
		Constraints.setTextFieldInteger(txtP_lucro);
		Constraints.setTextFieldInteger(txtP_maxDesc);
		Constraints.setTextFieldDouble(txtP_custo);
		Constraints.setTextFieldDouble(txtP_venda);
		Constraints.setTextFieldMaxLength(txtP_desc, 80);
	}
	
	public void atualizarDadosFormulario() {
		if(produto != null) {			
			lblId_produto.setText(String.valueOf(produto.getId_produto()));
			lblProdutoCategoria.setText(produto.getProdutoCategoria().getP_cat());
			txtP_codigo.setText(produto.getP_codigo());
			txtP_codBarras.setText(produto.getP_codBarra());
			txtP_desc.setText(produto.getP_desc());
			txtId_cat.setText(String.valueOf(produto.getProdutoCategoria().getId_cat()));
			txtP_custo.setText(String.valueOf(produto.getP_custo()));
			txtP_venda.setText(String.valueOf(produto.getP_venda()));
			txtP_lucro.setText(String.valueOf(produto.getP_lucro()));
			txtP_maxDesc.setText(String.valueOf(produto.getP_maxDesc()));
			txtP_cfab.setText(produto.getP_cfab());
		}
		else {
			Alerts.showAlert("Erro seleção produto", null, "Selecione o produto que quer editar", AlertType.ERROR);
			throw new IllegalStateException("Produto está nulo");
		}	
	}
	
	private Produto getFormularioDados() {
		Produto produto = new Produto();
		
		ValidationException exception = new ValidationException("");
		
		produto.setId_produto(Utils.tryParseToInt(lblId_produto.getText()));
		produto.setP_codigo(txtP_codigo.getText());
		produto.setP_codBarra(txtP_codBarras.getText());
		
		if (txtP_desc.getText() == null || txtP_desc.getText().trim().equals("")) {
			exception.addError("descricao", "Campo não pode estar vazio");
		}
		
		produto.setP_desc(txtP_desc.getText());
		
		produto.setProdutoCategoria(produtoCategoria);
		
		produto.setP_custo(Double.valueOf(txtP_custo.getText()));
		produto.setP_venda(Double.valueOf(txtP_venda.getText()));
		produto.setP_lucro(Utils.tryParseToInt(txtP_lucro.getText()));
		produto.setP_maxDesc(Utils.tryParseToInt(txtP_maxDesc.getText()));
		produto.setP_cfab(txtP_cfab.getText());
		
		if (exception.getErros().size() > 0) {
			throw exception;
		}
		
		return produto;
	}
	
	private ProdutoCategoria createDialogForm(ProdutoCategoria obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoCategoriaListaControle controller = loader.getController();
			controller.setProdutoCategoriaService(new ProdutoCategoriaService());
			controller.onSelecionarCategoria(obj);			
			//controller.subscreverAlteracaoDadosListener(this);			

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Lista de Categorias de Produto");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);			
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			obj = controller.getProdCategSelecao();
			return obj;
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a visualização", e.getMessage(), AlertType.ERROR);
			return null;
		}		
	}
}
