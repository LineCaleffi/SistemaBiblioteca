package br.com.empresa.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.empresa.constantes.Messages;
import br.com.empresa.entity.Aluno;
import br.com.empresa.entity.AlunoDisciplina;
import br.com.empresa.entity.Avaliacao;
import br.com.empresa.entity.Disciplina;
import br.com.empresa.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = Messages.SWAGGER_TAG_AVALIACAO)
@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoResource {
	@Autowired
	private AvaliacaoService avServ;
	
	@Operation(description = Messages.SWAGGER_GETALL)
	@GetMapping
	public ResponseEntity<List<Avaliacao>> listarAvaliacao(){
		List<Avaliacao> avaliacao	= avServ.findAll();
		return ResponseEntity.ok().body(avaliacao);
	}
	
	// paginação
	@Operation(description = Messages.SWAGGER_GETPAGE)
	@GetMapping("/page")
	public ResponseEntity<Page<Avaliacao>> listaPorPaginacao(
			@RequestParam(value ="pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") int linhasPorPagina,
			@RequestParam(value = "direction", defaultValue = "ASC") String direcao,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
			){
		Page<Avaliacao> avaliacao = avServ.buscarPorPaginacao(pagina, linhasPorPagina, direcao, orderBy);
		return ResponseEntity.ok().body(avaliacao);
	}
	
	// versionamento
	@Operation(description = Messages.SWAGGER_GETV2)
	@GetMapping("/v2/page")
	public ResponseEntity<Page<Avaliacao>> listarTurmasPorPaginacaoV2(
			@RequestParam(value="pagina", defaultValue = "0") int pagina,
			@RequestParam(value="direcao", defaultValue = "ASC") String direcao,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
			){
		
		Page<Avaliacao> turmas = avServ.buscarPorPaginacao(pagina, 10, direcao, orderBy);
		return ResponseEntity.ok().body(turmas);
	}
	
	@Operation(description = Messages.SWAGGER_POST)
	@PostMapping("{id}")
	public ResponseEntity<Avaliacao> inserir(@RequestBody Avaliacao av){
		av = avServ.save(av);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(av.getAlunoDisciplina()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@Operation(description = Messages.SWAGGER_GET_ALU_DISC)
	@RequestMapping(value = "/{idAluno}/{idDisciplina}", method = RequestMethod.GET)
	public ResponseEntity<Avaliacao> buscarAvaliacaoAlunoPorDisciplina(@PathVariable int idAluno,@PathVariable int idDisciplina){
		Aluno aluno = new Aluno();
		aluno.setId(idAluno);
		
		Disciplina disciplina = new Disciplina();
		disciplina.setId(idDisciplina);
		
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
		alunoDisciplina.setAluno(aluno);
		alunoDisciplina.setDisciplina(disciplina);
		
		Avaliacao avaliacao = avServ.buscarNotaAlunoDisciplina(alunoDisciplina);
		
		return ResponseEntity.ok().body(avaliacao);
	}
}
