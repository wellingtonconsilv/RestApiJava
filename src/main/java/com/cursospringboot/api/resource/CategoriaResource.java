package com.cursospringboot.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.api.event.ResourceCreateEvent;
import com.cursospringboot.api.model.Categoria;
import com.cursospringboot.api.service.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/categorias")
@Api(value = "Categoria")
public class CategoriaResource {
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation(value = "Listar todas as categorias.")	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaService.listar();
	}
	
	@ApiOperation(value = "Cadastrar uma categoria.")
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria novaCategoria = categoriaService.criar(categoria);
		publisher.publishEvent(new ResourceCreateEvent(this, response, novaCategoria.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	}
	
	@ApiOperation(value = "Buscar uma categoria pelo id.")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.buscorPorId(id);
		return (categoria.isPresent()) ? ResponseEntity.ok().body(categoria.get()) : ResponseEntity.notFound().build();
	}

}
