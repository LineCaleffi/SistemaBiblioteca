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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.empresa.constantes.Messages;
import br.com.empresa.entity.Disciplina;
import br.com.empresa.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = Messages.SWAGGER_TAG_DISCIPLINA)
@RestController
@RequestMapping("disciplina")
public class DisciplinaResource {
	@Autowired
	private DisciplinaService discServ;
	
	@Operation(description = Messages.SWAGGER_GETALL)
	// metodo listar todas
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Disciplina>> getAll(){
		List<Disciplina> disc = discServ.listarTodas();
		return ResponseEntity.ok().body(disc); 
	}
	
	@Operation(description = Messages.SWAGGER_GETPAGE)
	@GetMapping("/page")
	public ResponseEntity<Page<Disciplina>> listarPorPaginacao(
			@RequestParam(value="pagina", defaultValue = "0") int pagina,
			@RequestParam(value="linhasPorPagina", defaultValue = "24") int linhasPorPagina,
			@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy
			){
		Page<Disciplina> disciplina = discServ.buscarPorPaginacao(pagina, linhasPorPagina, direcao, orderBy);
		return ResponseEntity.ok().body(disciplina);
	}
	
	// versionamento
	@Operation(description = Messages.SWAGGER_GETV2)
	@GetMapping("/v2/page")
	public ResponseEntity<Page<Disciplina>> listarTurmasPorPaginacaoV2(
			@RequestParam(value="pagina", defaultValue = "0") int pagina,
			@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
			){
			
		Page<Disciplina> turmas = discServ.buscarPorPaginacao(pagina, 10, direcao, orderBy);
		return ResponseEntity.ok().body(turmas);
	}
	
	@Operation(description = Messages.SWAGGER_GETONE)
	//metodo listar po ID
	@GetMapping("{id}")
	public ResponseEntity<Disciplina> buscaPorId(@PathVariable int id) throws ObjectNotFoundException{
		Disciplina disciplina = discServ.buscaId(id);
		return ResponseEntity.ok().body(disciplina);
	}
	
	@Operation(description = Messages.SWAGGER_POST)
	//metodo inserir
	@PostMapping
	public ResponseEntity<Void> inserir(@RequestBody Disciplina objDisciplina){
		Disciplina disciplina = discServ.salvar(objDisciplina);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(disciplina.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@Operation(description = Messages.SWAGGER_PATCH)
	//metodo alterar
	@PatchMapping("{id}")
	public ResponseEntity<Void> alterar(@RequestBody Disciplina disciplina, @PathVariable int id){
		disciplina.setId(id);
		discServ.alterar(disciplina);
		return ResponseEntity.noContent().build();
	} // PARTE 2 - AULA 10
	
	@Operation(description = Messages.SWAGGER_DELETE)
	//metodo deletar
	@DeleteMapping("{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id){
		discServ.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
