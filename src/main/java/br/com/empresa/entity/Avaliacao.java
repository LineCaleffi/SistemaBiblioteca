package br.com.empresa.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "avaliacoes")
public class Avaliacao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3330787209653483038L;
	@EmbeddedId // coloca idAluno e idDisciplina na tabela
	private AlunoDisciplina alunoDisciplina;
	private String conceito;	
}
