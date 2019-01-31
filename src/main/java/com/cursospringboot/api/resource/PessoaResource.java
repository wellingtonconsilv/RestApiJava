package com.cursospringboot.api.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.api.event.ResourceCreateEvent;
import com.cursospringboot.api.model.Pessoa;
import com.cursospringboot.api.repository.PessoaRepository;
import com.cursospringboot.api.service.PessoaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pessoas")
@Api(value = "Pessoas")
public class PessoaResource {

	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@PostMapping
	@ApiOperation(value = "Cadastrar uma pessoa.")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa novaPessoa = pessoaService.criar(pessoa);
		publisher.publishEvent(new ResourceCreateEvent(this, response, novaPessoa.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);		
	}
	
	
	@ApiOperation(value = "Buscar uma pessoa pelo id.")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id){
		Optional<Pessoa> pessoa = pessoaService.buscarPorId(id);
		return (pessoa.isPresent()) ? ResponseEntity.ok().body(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Excluir uma pessoa pelo id.")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('read')")
	public void deletar(@PathVariable Long id) {
		pessoaService.excluir(id);
	}
	
	@ApiOperation(value = "Atualizar uma pessoa pelo id.")
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa>atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){			
		return ResponseEntity.ok(pessoaService.atualizar(id, pessoa));
		}
	
	@ApiOperation(value = "Atualizar o ativo de uma pessoa.")
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('read')")
	public void atualizarAtivo(@PathVariable Long id, @RequestBody boolean ativo) {
				pessoaService.mudarAtivo(id, ativo);
	}
	
	@ApiOperation(value = "Listar todas as pessoas.")
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public Page<Pessoa> listar(@RequestParam(name = "nome", required = false, defaultValue="%") String nome, Pageable pageable) {
		return pessoaRepository.findByNomeContaining(nome, pageable);
	}
}
