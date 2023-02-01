package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contato;
import model.ContatoDAOMySQL;
import model.InterfaceContatoDAO;

public class AgendaContatosController implements Initializable {

	@FXML
	private TableView<Contato> tvContatos;

	@FXML
	private TableColumn<Contato, String> tcNome;

	@FXML
	private TableColumn<Contato, String> tcNumero;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtNumero;

	@FXML
	private Button btnIncluir;

	@FXML
	private Button btnAtualizar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnLimpar;

	
	//aqui vou declarar um atributo DAO, pela interface, não precisa mecher tanto no código
	private InterfaceContatoDAO contatoDAO;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

		// logica de preenchimento do formulario ao clique da tabela (tableView)
		tvContatos.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {

			if (n != null) {
				txtNome.setText(n.getNome());
				txtNumero.setText(n.getNumero());

			}

		});
		
		
		// boolean = true ou false ... booleanBinding executa a logica em questao quantas vezes for necessaria, para dar o retorno true ou false 
		// nesse caso vai verificar se o objeto selecionado é diferente de nulo
		BooleanBinding temContatoSelecionado = tvContatos.getSelectionModel().selectedItemProperty().isNotNull();
		
		//textFild preenchido, botao Limpar habilitado, vazio, não precisa do limpar
		BooleanBinding temCampoPreenchido = txtNome.textProperty().isNotEmpty().or(txtNumero.textProperty().isNotEmpty());
				
		// qnd selecionado, desabilitar incluir e habilitar atualizar e excluir. o inverso é verdadeiro
		btnIncluir.disableProperty().bind(temContatoSelecionado);
		btnAtualizar.disableProperty().bind(temContatoSelecionado.not());
		btnExcluir.disableProperty().bind(temContatoSelecionado.not());
		btnLimpar.disableProperty().bind(temCampoPreenchido.not());
		
		//inicializando DAO
		contatoDAO = new ContatoDAOMySQL();
		
		//pre carrega a minha tableview com os contatos salvos no banco de dados.
		atualizarLista();
		}
	
	private void atualizarLista() {
				
		//clear para limpar e qnd atualizar a lista tenho certeza q vai usar 100% do meu banco de dados
		tvContatos.getItems().clear();
		tvContatos.getItems().setAll(contatoDAO.obterTodos());
			
	}
	
	public void onBtnIncluirClick() {

		String nomeDigitado = txtNome.getText();
		String numeroDigitado = txtNumero.getText();

		Contato contato = new Contato(nomeDigitado, numeroDigitado);

        contatoDAO.incluir(contato);
		
		atualizarLista();

		reset();
	}

	public void onBtnAtualizarClick() {

		String nomeDigitado = txtNome.getText();
		String numeroDigitado = txtNumero.getText();

		Contato contatoSelecionado = tvContatos.getSelectionModel().getSelectedItem();

		contatoSelecionado.setNome(nomeDigitado);
		contatoSelecionado.setNumero(numeroDigitado);

		contatoDAO.atualizar(contatoSelecionado);
		
		atualizarLista();

		reset();

	}
	
	public void onBtnExcluirClick() {
	
		Contato contatoSelecionado = tvContatos.getSelectionModel().getSelectedItem();
		
		contatoDAO.remover(contatoSelecionado.getId());
		
		atualizarLista();
		
		reset();
		
	}
	
	public void onBtnLimparClick() {
		
			reset();
		
	}

	// metodo criado para limpar o formulario e o sect da lista, basta colocar - limparFormulario(); -
	// sempre que for necessario no final limpar o formulario e o q esta selecionado na lista tableview
	private void reset() {

		txtNome.setText("");
		txtNumero.setText("");
		tvContatos.getSelectionModel().clearSelection();
	}

}
