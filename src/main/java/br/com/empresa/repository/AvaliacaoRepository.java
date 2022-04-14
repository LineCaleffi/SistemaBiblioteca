package br.com.empresa.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import br.com.empresa.entity.AlunoDisciplina;
import br.com.empresa.entity.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepositoryImplementation<Avaliacao, AlunoDisciplina>{
 // AlunoDisciplina -> tipo da chave composta/primaria da classe Avaliação
	
	//select * from avaliacao where idaluno=3 and iddisciplina = 6
	Avaliacao findByAlunoDisciplina(AlunoDisciplina alunoDisciplina);
}
