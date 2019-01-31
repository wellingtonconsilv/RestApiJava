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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.api.event.ResourceCreateEvent;
import com.cursospringboot.api.model.Lancamento;
import com.cursospringboot.api.repository.ResumoLancamento;
import com.cursospringboot.api.repository.filter.LancamentoFilter;
import com.cursospringboot.api.service.LancamentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lancamentos")
@Api(value = "Lancamentos")
public class LancamentoResource {
	
	@Autowired
	LancamentoService lancamentoService;
	@Autowired
	ApplicationEventPublisher publisher;
	
	@PostMapping
	
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento novoLancamento = lancamentoService.criar(lancamento);
		publisher.publishEvent(new ResourceCreateEvent(this, response, novoLancamento.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novoLancamento);
		
	}
	
//	@GetMapping("/filtro")
//	public ResponseEntity<List<Lancamento>> listar(@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
//													@RequestParam(name = "dataFim", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim){
//		return ResponseEntity.ok().body(lancamentoService.listar(inicio, fim));	
//	}
	
	@ApiOperation("Buscar todos lançamentos.")
	@GetMapping
	public Page<Lancamento> listar(LancamentoFilter lancamento, Pageable pageable){
		return lancamentoService.listar(lancamento, pageable);
	}
	
	@ApiOperation("Buscar lançamento com parametros.")
	@GetMapping("resumo")
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamento, Pageable pageable){
		return lancamentoService.resumir(lancamento, pageable);
	}
	
	@ApiOperation("Atualizar um lançamento pelo id.")
	@PutMapping("/{id}")
	public ResponseEntity<Lancamento> atualizar(@Valid @RequestBody Lancamento lancamento, @PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(lancamentoService.atualizar(lancamento, id));
		
	}
	
	@ApiOperation("Exclir um lançamento pelo id.")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		lancamentoService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation("Buscar lançamento pelo id.")
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> buscarPorId(@PathVariable Long id) {
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		return (lancamento.isPresent()) ? ResponseEntity.ok().body(lancamento.get()) : ResponseEntity.notFound().build();
	}
}
