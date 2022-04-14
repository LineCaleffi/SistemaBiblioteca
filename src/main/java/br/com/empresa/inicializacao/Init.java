package br.com.empresa.inicializacao;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.empresa.entity.Aluno;
import br.com.empresa.entity.AlunoDisciplina;
import br.com.empresa.entity.Avaliacao;
import br.com.empresa.entity.Disciplina;
import br.com.empresa.entity.Turma;
import br.com.empresa.repository.AlunoRepository;
import br.com.empresa.service.AvaliacaoService;
import br.com.empresa.service.DisciplinaService;
import br.com.empresa.service.TurmaService;

@Component
//Context -> toda vez que a minha aplicação inicializar eu quero que alguma coisa aconteça
public class Init implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired 
	private AlunoRepository alunoRepository;
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private DisciplinaService discService;
	
	@Autowired
	private AvaliacaoService avService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Aluno aluno1 = new Aluno();
		aluno1.setNome("Aline");
		
		Aluno aluno2 = new Aluno();
		aluno2.setNome("Maria");
		
		Aluno aluno3 = new Aluno();
		aluno3.setNome("Lívia");
			
		Aluno aluno4 = new Aluno();
		aluno4.setNome("Lola");
		
		// salva todos de uma vez por arrayList no banco de dados
		alunoRepository.saveAll(Arrays.asList(aluno1, aluno2, aluno3, aluno4));// Faz o INSERT INTO <nome_tabela> (.....) // VALUES (.....)

		//Adicionar Turma
		Turma ads = new Turma();
		ads.setNome("Analise de Desenvolvimento de Sistema");
		turmaService.salvar(ads);
		
		Turma redes = new Turma();
		redes.setNome("Redes");
		turmaService.salvar(redes);
		
		Turma cc = new Turma();
		cc.setNome("Ciencia da Computação");
		turmaService.salvar(cc);		
		
		/****************** PARTE 02 - IMPRIME NO CONSOLE *******************/
//		List<Turma> listaTurmas = turmaService.listaTodasTurmas();
//		
//		System.out.println("");
//		Turma turma1 = turmaService.buscaPorId(1);
//		System.out.println(turma1.getNome());
//		
//		for (Turma turma : listaTurmas) {
//			System.out.println("Nome da turma: " +turma.getNome());
//		} 
		
//		 turmaService.excluir(4);
		
//		Turma turmaAlterar= new Turma();
//		//turmaAlterar.setId(1);
//		//turmaAlterar.setNome("ADS");
//		//turmaAlterar.setId(2);
//		//turmaAlterar.setNome("Redes de Computadores");
//		turmaAlterar.setId(5);
//		turmaAlterar.setNome("Ciencia da computação");
//		turmaService.alterar(turmaAlterar);
//		
//		
//		List<Turma> listaAlterada = turmaService.listaTodasTurmas();
//
//		for (Turma turma : listaAlterada) {
//			System.out.println("Nome da turma: " +turma.getNome());
//		} 	
//		
		
		/****************** PARTE 04 - RELACIONAMENTO *******************/
		Disciplina java = new Disciplina();
		java.setNome("Java");
		discService.salvar(java);
		
		Disciplina java2 = new Disciplina();
		java2.setNome("Java 2");
		discService.salvar(java2);	
		
		Disciplina spring = new Disciplina();
		spring.setNome("Spring Tool");
		discService.salvar(spring);
		
		Disciplina arquitetura = new Disciplina();
		arquitetura.setNome("Arquitetura");
		discService.salvar(arquitetura);
		
		Disciplina enfermagem = new Disciplina();
		enfermagem.setNome("Enfermagem");
		discService.salvar(enfermagem);

		//grava as turmas do aluno
		aluno1.setTurma(ads);
		aluno2.setTurma(cc);
		aluno3.setTurma(redes);
		aluno4.setTurma(ads);
		
		//grava as disciplinas do aluno
		aluno1.setDisciplinas(Arrays.asList(java,java2,arquitetura));
		aluno2.setDisciplinas(Arrays.asList(enfermagem,arquitetura));
		aluno3.setDisciplinas(Arrays.asList(java,java2,enfermagem));
		aluno4.setDisciplinas(Arrays.asList(spring,java2,java));
		
		// salva no banco
		alunoRepository.save(aluno1);
		alunoRepository.save(aluno2);
		alunoRepository.save(aluno3);
		alunoRepository.save(aluno4);
		
		/****************** PARTE 05 - AVALIAÇÃO *******************/
		//Atrelando a avaliação dos alunos 
		Avaliacao avAlu1 = new Avaliacao();
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
		alunoDisciplina.setAluno(aluno1); // Aluno que sera atrelado
		alunoDisciplina.setDisciplina(java); // Disciplina que sera atrelada
		avAlu1.setAlunoDisciplina(alunoDisciplina); // atrela o ID da disciplina e ID Aluno
		avAlu1.setConceito("A"); // cria conceito
		avService.save(avAlu1); // salva na tabela no BD
		
		Avaliacao avAlu2 = new Avaliacao();
		AlunoDisciplina alu2 = new AlunoDisciplina();
		alu2.setAluno(aluno2); // Aluno que sera atrelado
		alu2.setDisciplina(arquitetura); // Disciplina que sera atrelada
		avAlu2.setAlunoDisciplina(alu2);  // atrela o ID da disciplina e ID Aluno
		avAlu2.setConceito("B"); // cria o conceito
		avService.save(avAlu2); // salva na tabela no BD
		 
		Avaliacao avAlu3 = new Avaliacao();
		AlunoDisciplina alu3 = new AlunoDisciplina();
		alu3.setAluno(aluno3); // Aluno que sera atrelado
		alu3.setDisciplina(enfermagem); // Disciplina que sera atrelada
		avAlu3.setAlunoDisciplina(alu3);  // atrela o ID da disciplina e ID Aluno
		avAlu3.setConceito("C"); // cria o conceito
		avService.save(avAlu3); // salva na tabela no BD
		
		Avaliacao avAlu4 = new Avaliacao();
		AlunoDisciplina alu4 = new AlunoDisciplina();
		alu4.setAluno(aluno4); // Aluno que sera atrelado
		alu4.setDisciplina(spring); // Disciplina que sera atrelada
		avAlu4.setAlunoDisciplina(alu4);  // atrela o ID da disciplina e ID Aluno
		avAlu4.setConceito("A"); // cria o conceito
		avService.save(avAlu4); // salva na tabela no BD
		
		/***************testando avaliacao no console***************/
		Avaliacao aval = avService.buscarNotaAlunoDisciplina(alunoDisciplina);
		System.out.println("\nAluno: " + aval.getAlunoDisciplina().getAluno().getNome());
		System.out.println("Disciplina: " + aval.getAlunoDisciplina().getDisciplina().getNome());
		System.out.println("Avaliação: " + aval.getConceito());
		
		Avaliacao avaliacao = avService.buscarNotaAlunoDisciplina(alu2);
		System.out.println("\nAluno: " + avaliacao.getAlunoDisciplina().getAluno().getNome());
		System.out.println("Disciplina: " + avaliacao.getAlunoDisciplina().getDisciplina().getNome());
		System.out.println("Avaliação: " + avaliacao.getConceito()+"\n");
	}
}