package com.cursospringboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursospringboot.api.model.Categoria;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
