package br.com.empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empresa.entity.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer>{
	// JpaRepository <T, ID> 
	// T= a entidade que vai persistir 
	// ID -> tipo da chave primária da entidade
} // Parte 2 - Aula 02
