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
import model.entities.ProdutoCategoria;
import model.exceptions.ValidationException;
import model.services.ProdutoCategoriaService;

public class ProdutoCategoriaFormularioControle implements Initializable {
	
	private ProdutoCategoria produtoCategoria;

	private ProdutoCategoriaService produtoCategoriaService;
	
	private List<AlteracaoDadosListener> alteracaoDadosListeners = new ArrayList<>();

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	@FXML
	private Label lblId_cat;

	@FXML
	private TextField txtP_cat;
	
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
	public void OnBtSalvarAction(ActionEvent event) {
		if (produtoCategoria == null) {
			throw new IllegalStateException("Produto está nulo");
		}
		if (produtoCategoriaService == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		try {
			produtoCategoria = getFormularioDados();
			produtoCategoriaService.saveOrUpdate(produtoCategoria);			
			notificarAlteracaoDadosListeners(produtoCategoria.getP_cat());
			Utils.currentStage(event).close();
		}
		catch (DbIntegrityException e) {
			Alerts.showAlert("Erro ao salvar Categoria de Produto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificarAlteracaoDadosListeners(String string) {
		for (AlteracaoDadosListener listener : alteracaoDadosListeners) {
			listener.onAlteracaoDados(string);
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
		Constraints.setTextFieldMaxLength(txtP_cat, 80);
	}
	
	public void atualizarDadosFormulario() {
		if(produtoCategoria == null) {			
			Alerts.showAlert("Erro seleção Categoria Produto", null, "Selecione o categoria que quer editar", AlertType.ERROR);
			throw new IllegalStateException("Categoria de Produto está nulo");
		}
		lblId_cat.setText(String.valueOf(produtoCategoria.getId_cat()));
		txtP_cat.setText(produtoCategoria.getP_cat());		
	}
	
	private ProdutoCategoria getFormularioDados() {
		
		produtoCategoria = new ProdutoCategoria();
		
		ValidationException exception = new ValidationException("");
		
		produtoCategoria.setId_cat(Utils.tryParseToInt(lblId_cat.getText()));
		
		
		if (txtP_cat.getText() == null || txtP_cat.getText().trim().equals("")) {
			exception.addError("descricao", "Campo não pode estar vazio");
		}
		
		produtoCategoria.setP_cat(txtP_cat.getText());
		
		if (exception.getErros().size() > 0) {
			throw exception;
		}
		
		return produtoCategoria;
	}
}
