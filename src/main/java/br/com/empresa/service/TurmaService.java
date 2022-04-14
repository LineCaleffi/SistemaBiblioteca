package br.com.empresa.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.empresa.entity.Turma;
import br.com.empresa.repository.TurmaRepository;

@Service
public class TurmaService {
	@Autowired
	private TurmaRepository turmaRepository;
	
	// criar metodo de listar todas as turmas
	public List<Turma> listaTodasTurmas(){
		return turmaRepository.findAll(); // faz o SELECT * FROM
	} // Parte 2 - Aula 03
	
	public Page<Turma> buscaPorPaginacao(int pagina, int linhasPorPagina, String direction, String orderBy){
		PageRequest request = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direction), orderBy);
		return new PageImpl<>(turmaRepository.findAll(), request, linhasPorPagina);
	} // Parte 8 - aula 01 
	
	// criar um metodo para trazer a turma por id
	public Turma buscaPorId(int id) throws ObjectNotFoundException{
		Optional<Turma> turma = turmaRepository.findById(id);
		//retorna a turma ou se não existir retorna como "objeto não encontrado"
		return turma.orElseThrow(() -> new ObjectNotFoundException(null,  "Objeto não encontrado"));
	} // Parte 02 - Aula 05
	
	// criar um metodo para inserir a turma
	public Turma salvar(Turma turma) {
		return turmaRepository.save(turma); // mesma coisa que INSERT INTO (..) VALUES(..)
	}// Parte 2 - Aula 03
	
	// criar um metodo para alterar a turma
	public Turma alterar(Turma objTurma) {
		Turma turma = buscaPorId(objTurma.getId());
		turma.setNome(objTurma.getNome());
		return salvar(turma);
	} // PARTE 2 - AULA 06
	
	// criar um metodo para deletar a turma
	public void excluir(int id) {
		turmaRepository.deleteById(id);
	}// PARTE 2 - AULA 06
}
