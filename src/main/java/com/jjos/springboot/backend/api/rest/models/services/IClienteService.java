package com.jjos.springboot.backend.api.rest.models.services;

import java.util.List;

import com.jjos.springboot.backend.api.rest.models.entity.Cliente;

public interface IClienteService {
	public List<Cliente> findAll();
	
	public Cliente save(Cliente cliente);
	
	public void deleteById(Long idCliente);
	
	public Cliente findById(Long idCliente);
	
}
