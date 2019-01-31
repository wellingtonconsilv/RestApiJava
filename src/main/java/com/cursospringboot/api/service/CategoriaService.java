package com.cursospringboot.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursospringboot.api.model.Categoria;
import com.cursospringboot.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	public Optional<Categoria> buscorPorId(Long id) {
		return categoriaRepository.findById(id);
	}
	
	public Categoria criar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
}
