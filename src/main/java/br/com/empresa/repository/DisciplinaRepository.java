package br.com.empresa.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import br.com.empresa.entity.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepositoryImplementation<Disciplina, Integer>{
	// JpaRepository <T, ID> 
	// T= a entidade que vai persistir 
	// ID -> tipo da chave primária da entidade

} //PARTE 3 - AULA 01
