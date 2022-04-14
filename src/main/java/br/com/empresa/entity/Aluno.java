package br.com.empresa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Aluno implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1138113722947941051L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	
	@ManyToOne // MUITOS para UM (muitas Turmas para um Aluno)
	@JoinColumn(name = "id_turma")
	private Turma turma;
	
	@ManyToMany 
	// cria uma tabela "matricula" e relaciona com os ids dos alunos e das diciplinas
	@JoinTable(name = "matricula", joinColumns =  { @JoinColumn(name = "id_aluno")}, 
				inverseJoinColumns = {@JoinColumn(name = "id_disciplina")}) 
	private List<Disciplina> disciplinas;
	
	
}

/* ----------------- COMENTARIOS ---------------- */

/* 
 * @ManyToMany // MUITOS para MUITOS(varios alunos, para varias diciplinas)
 * @ManyToOne // MUITOS para UM (muitas Turmas para um Aluno)
 * 
 */
