package com.jjos.springboot.backend.api.rest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jjos.springboot.backend.api.rest.models.entity.Cliente;

/*
 * Se crea una Interface IClienteDao y esta hereda todos los Metodos de CrudRepository
 * es decir que vamos a tener todos esos metodos para un crud basic, por otro lado si
 * queremos crear metodos nuevos es posible creandolos normal dentro la Interface, es 
 * importante tener en cuenta que estos metodos deben tener unas anotaciones basicas
 * que necesitan cada una (Transactional y supongo que otras más)
 * */
public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
}
