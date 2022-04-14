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

import br.com.empresa.entity.Aluno;
import br.com.empresa.entity.Turma;
import br.com.empresa.repository.AlunoRepository;

@Service
public class AlunoService {
	@Autowired // Injeção de dependência - o sistema define quando usar
	private AlunoRepository alunoRepository;
	
	public List<Aluno> listaTodosAlunos(){
		// select *from -> findAll() -> mesma coisa
		return alunoRepository.findAll();
	}
	
	public Page<Aluno> buscaPorPaginacao(int pagina, int linhasPorPagina, String direction, String orderBy){
		PageRequest request = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direction), orderBy);
		return new PageImpl<>(alunoRepository.findAll(), request, linhasPorPagina);
	} // Parte 8 - aula 01 
	
	
	//lança uma exceção quando o ID não for encontrado
	public Aluno buscaPorID(int id) throws ObjectNotFoundException{
		Optional<Aluno> aluno = alunoRepository.findById(id);
		//se não for encontrado aparece a mensagem "objeto não encontrado"
		return aluno.orElseThrow(() -> new ObjectNotFoundException(null, "Objeto não encontrado"));
	}
	
	//método para salvar
	public Aluno salvar(Aluno aluno) {
		return alunoRepository.save(aluno);
	}// Parte 1 - Aula 11
	
	//método para deletar
	public void excluir(int id) {
		alunoRepository.deleteById(id);;
	} // Parte 1 - Aula 11
	
	//método para alterar
	public Aluno alterar(Aluno objAluno) {
		Aluno aluno = buscaPorID(objAluno.getId());
		aluno.setNome(objAluno.getNome());
		aluno.setTurma(objAluno.getTurma());
		aluno.setDisciplinas(objAluno.getDisciplinas());
		
		return salvar(objAluno);
	} // Parte 1 - Aula 11
}
