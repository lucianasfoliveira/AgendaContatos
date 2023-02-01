package model;

import java.util.List;

public interface InterfaceContatoDAO {
	
	void incluir (Contato contato);
	
	void atualizar (Contato contato);
	
	void remover (int id);

	//método para listar tds os contatos, para ser usado qnd iniciar a agenda
	List<Contato> obterTodos();
	
	//retorna um contato
	Contato obterPorId (int id);
	
}
