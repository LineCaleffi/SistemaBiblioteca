package br.com.empresa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.empresa.entity.AlunoDisciplina;
import br.com.empresa.entity.Avaliacao;
import br.com.empresa.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {
	@Autowired
	private AvaliacaoRepository avRep;
	
	//salva
	public Avaliacao save(Avaliacao avaliacao) {
		return avRep.save(avaliacao);
	}
	
	//paginação
	public Page<Avaliacao> buscarPorPaginacao(int pagina, int linhasPorPagina, String direction, String orderBy){
		PageRequest request = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direction), orderBy);
		return new PageImpl<>(avRep.findAll(),request, linhasPorPagina);
	}
	
	//busca todos
	public List<Avaliacao> findAll(){
		return avRep.findAll();
	}
	
	public Avaliacao buscarNotaAlunoDisciplina(AlunoDisciplina alunoDisciplina) {
		return avRep.findByAlunoDisciplina(alunoDisciplina);
	}
	
}// PARTE 5 ---- AULA 06
