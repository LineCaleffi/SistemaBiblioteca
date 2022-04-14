package br.com.empresa.resource;

import java.net.URI;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.empresa.constantes.Messages;
import br.com.empresa.entity.Aluno;
import br.com.empresa.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//classe que fornece API ao front - é o controller
@Tag(name = Messages.SWAGGER_TAG_ALUNO)
@RestController
@RequestMapping("/aluno")
public class AlunoResource {
	@Autowired
	private AlunoService alunoServ;
	
	//ResponseEntity -> para enviar resposta completa, com status, com cabeçalho e corpo.
	 @Operation(description = Messages.SWAGGER_GETALL) // Mensagens que irão aparecer no Swagger - OpenAPI
	//metodo GET no postman
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Aluno>> listarAluno(){
		List<Aluno> alunos = alunoServ.listaTodosAlunos();
		return ResponseEntity.ok().body(alunos); 		// se for tudo ok retorna no body a lista de turma

	}// Parte 1 - Aula 10
	 
	@Operation(description = Messages.SWAGGER_GETPAGE)
	@GetMapping("/page")
	public ResponseEntity<Page<Aluno>> listarPorPaginacao(
			@RequestParam(value="pagina", defaultValue = "0") int pagina,
			@RequestParam(value="linhaPorPagina", defaultValue = "24") int linhaPorPagina,
			@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy
			){
		 Page<Aluno> alunos = alunoServ.buscaPorPaginacao(pagina, linhaPorPagina, direcao, orderBy);
		 return ResponseEntity.ok().body(alunos);
	 }
	
	@Operation(description = Messages.SWAGGER_GETV2)
	@GetMapping("/v2/page")
	public ResponseEntity<Page<Aluno>> listarPorPaginacaoV2(
			@RequestParam(value="pagina", defaultValue = "0") int pagina,
			@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy
			){
		 Page<Aluno> alunos = alunoServ.buscaPorPaginacao(pagina, 10, direcao, orderBy);
		 return ResponseEntity.ok().body(alunos);
	 }
	
	 @Operation(description = Messages.SWAGGER_GETONE) // Mensagens que irão aparecer no Swagger - OpenAPI
	//metodo GET no postman
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Aluno> buscaPorId(@PathVariable int id) throws ObjectNotFoundException{
		Aluno aluno = alunoServ.buscaPorID(id);
		// se tudo for OK, retorna no body o aluno que foi acrescentado
		return ResponseEntity.ok().body(aluno);
	} // Parte 1 - Aula 12
	
	//@RequestBody -> convertido para um aluno
	@Operation(description = Messages.SWAGGER_POST) // Mensagens que irão aparecer no Swagger - OpenAPI
	//método POST no postman - inserir
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir (@RequestBody Aluno objAluno){
		Aluno aluno = alunoServ.salvar(objAluno);
		 
		//diz qual a URL do aluno que foi criado no postman (Headers)
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aluno.getId()).toUri();
		
		return ResponseEntity.created(uri).build(); // quando for criado mostra qual a URI
	} // Parte 1 - Aula 13
	
	@Operation(description = Messages.SWAGGER_DELETE) // Mensagens que irão aparecer no Swagger - OpenAPI
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable int id){
		alunoServ.excluir(id);
		return ResponseEntity.noContent().build();
	} // Parte 1 - Aula 14
	
	@Operation(description = Messages.SWAGGER_PATCH) //Mensagens que irão aparecer no Swagger - OpenAPI
	@RequestMapping(value="/{id}",method = RequestMethod.PATCH)
	public ResponseEntity<Void> alterar(@RequestBody Aluno objAluno, @PathVariable int id){
		objAluno.setId(id);
		alunoServ.alterar(objAluno);
		
		return ResponseEntity.noContent().build(); // não retorna nada
	}// Parte 1 - Aula 15
}
