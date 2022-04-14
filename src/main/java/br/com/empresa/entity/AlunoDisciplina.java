package br.com.empresa.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable // caracteriza que é uma chave composta
public class AlunoDisciplina implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8991167693361438921L;

	//chave composta
	@ManyToOne
	private Aluno aluno;
	
	@ManyToOne
	private Disciplina disciplina;	
}
