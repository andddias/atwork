package gui;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.exceptions.ValidationException;
import model.services.ProdutoService;

public class ProdutoFormularioControle implements Initializable {
	
	private Produto produto;

	private ProdutoService produtoService;
	
	private List<AlteracaoDadosListener> alteracaoDadosListeners = new ArrayList<>();

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	@FXML
	private Label lblId_produto;

	@FXML
	private TextField txtP_codigo;

	@FXML
	private TextField txtP_codBarras;

	@FXML
	private TextField txtP_desc;

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
	
	public void subscreverAlteracaoDadosListener(AlteracaoDadosListener listener) {
		alteracaoDadosListeners.add(listener);
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
			notificarAlteracaoDadosListeners(produto.getP_codigo());
			Utils.currentStage(event).close();
		}
		catch (DbIntegrityException e) {
			Alerts.showAlert("Erro ao salvar o produto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificarAlteracaoDadosListeners(String codigo) {
		for (AlteracaoDadosListener listener : alteracaoDadosListeners) {
			listener.onAlteracaoDados(codigo);
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
		Constraints.setTextFieldInteger(txtP_codigo);
		Constraints.setTextFieldInteger(txtP_lucro);
		Constraints.setTextFieldInteger(txtP_maxDesc);
		Constraints.setTextFieldDouble(txtP_custo);
		Constraints.setTextFieldDouble(txtP_venda);
		Constraints.setTextFieldMaxLength(txtP_desc, 80);
	}
	
	public void atualizarDadosFormulario() {
		if(produto == null) {			
			Alerts.showAlert("Erro seleção produto", null, "Selecione o produto que quer editar", AlertType.ERROR);
			throw new IllegalStateException("Produto está nulo");
		}
		lblId_produto.setText(String.valueOf(produto.getId_produto()));
		txtP_codigo.setText(produto.getP_codigo());
		txtP_codBarras.setText(produto.getP_codBarra());
		txtP_desc.setText(produto.getP_desc());
		txtP_custo.setText(String.valueOf(produto.getP_custo()));
		txtP_venda.setText(String.valueOf(produto.getP_venda()));
		txtP_lucro.setText(String.valueOf(produto.getP_lucro()));
		txtP_maxDesc.setText(String.valueOf(produto.getP_maxDesc()));
		txtP_cfab.setText(produto.getP_cfab());
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
}
