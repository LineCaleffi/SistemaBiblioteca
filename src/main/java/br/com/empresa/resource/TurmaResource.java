package br.com.empresa.resource;

import java.net.URI;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.empresa.constantes.Messages;
import br.com.empresa.entity.Turma;
import br.com.empresa.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = Messages.SWAGGER_TAG_TURMA_ENDPOINT)
@RestController
@RequestMapping("/turma")
public class TurmaResource {
//essa classe é o Controller
	@Autowired
	private TurmaService turmaService;
	
	@Operation(description = Messages.SWAGGER_GETALL) // cria uma descrição no Swagger OpenAPI
	@GetMapping
	public ResponseEntity<List<Turma>> listarTurmas(){
		List<Turma> turmas = turmaService.listaTodasTurmas();
		return ResponseEntity.ok().body(turmas);
	} // PARTE 2 - AULA 07

	@Operation(description = Messages.SWAGGER_GETPAGE)
	// paginação
	// localhost:8080/api-sistema/page?pagina=0&linhaPorPagina=10&direcao=ASC&ordenacao=nome
	@GetMapping(value = "/v1/page")
	public ResponseEntity<Page<Turma>> listarTurmasPorPaginacao(
				@RequestParam(value="pagina", defaultValue = "0") int pagina,
				@RequestParam(value="linhasPorPagina", defaultValue = "24") int linhasPorPagina	,
				@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
				@RequestParam(value="orderBy", defaultValue = "nome")String orderBy
				){
		
		Page<Turma> turmas = turmaService.buscaPorPaginacao(pagina, linhasPorPagina, direcao, orderBy);
		return ResponseEntity.ok().body(turmas);
	} 
	
	//Versionamento
	@Operation(description = Messages.SWAGGER_GETV2)
	@GetMapping("/v2/page")
	public ResponseEntity<Page<Turma>> listarTurmasPorPaginacaoV2(
			@RequestParam(value="pagina", defaultValue = "0") int pagina,
			@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
			){
		
		Page<Turma> turmas = turmaService.buscaPorPaginacao(pagina, 10, direcao, orderBy);
		return ResponseEntity.ok().body(turmas);
	}
	
	@Operation(description = Messages.SWAGGER_GETONE) // cria uma descrição no Swagger OpenAPI
	@GetMapping("/{id}")
	public ResponseEntity<Turma> buscaPorId(@PathVariable int id) throws ObjectNotFoundException{
		Turma turmas = turmaService.buscaPorId(id);
		return ResponseEntity.ok().body(turmas);
	} // PARTE 2 - AULA 08
	
	@Operation(description = Messages.SWAGGER_POST) // cria uma descrição no Swagger OpenAPI
	@PostMapping
	public ResponseEntity<Void> inserir (@RequestBody Turma objTurma){
		Turma turma = turmaService.salvar(objTurma);
		
		//diz qual a URL da turma que foi criada no postman (Headers)
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(turma.getId()).toUri();
		return ResponseEntity.created(uri).build();
	} // PARTE 2 - AULA 09
	
	@Operation(description = Messages.SWAGGER_PATCH) // cria uma descrição no Swagger OpenAPI
	@PatchMapping("{id}")
	public ResponseEntity<Void> alterar(@RequestBody Turma turma, @PathVariable int id){
		turma.setId(id);
		turmaService.alterar(turma);
		return ResponseEntity.noContent().build();
	} // PARTE 2 - AULA 10
	
	@Operation(description = Messages.SWAGGER_DELETE) // cria uma descrição no Swagger OpenAPI
	@DeleteMapping("{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id){
		turmaService.excluir(id);
		return ResponseEntity.noContent().build();
	} // PARTE 2 - AULA 10	
}
