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

import br.com.empresa.entity.Disciplina;
import br.com.empresa.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
	@Autowired
	private DisciplinaRepository disciplinaRep;
	
	public List<Disciplina> listarTodas(){
		return disciplinaRep.findAll();
	}
	
	//metodo para buscar por id
	public Disciplina buscaId(int id) throws ObjectNotFoundException {
		Optional<Disciplina> disciplina	= disciplinaRep.findById(id);
		
		return disciplina.orElseThrow(()-> new ObjectNotFoundException(null, "não encontrado"));
	}
	
	public Page<Disciplina> buscarPorPaginacao(int pagina, int linhasPorPagina,String direction, String orderBy){
		PageRequest request = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direction), orderBy);
		return new PageImpl<>(disciplinaRep.findAll(),request, linhasPorPagina);
	}
	
	//metodo salvar
	public Disciplina salvar(Disciplina disciplina) {
		return disciplinaRep.save(disciplina);
	}
	
	//metodo alterar
	public Disciplina alterar(Disciplina disciplina) {
		Disciplina obj = buscaId(disciplina.getId());
		obj.setNome(disciplina.getNome());
		return salvar(obj);
	}
	
	//metodo deletar
	public void excluir(int id) {
		disciplinaRep.deleteById(id);
	}
}// PARTE 3 - AULA 02
