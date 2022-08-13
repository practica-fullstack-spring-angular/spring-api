package com.jjos.springboot.backend.api.rest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjos.springboot.backend.api.rest.models.dao.IClienteDao;
import com.jjos.springboot.backend.api.rest.models.entity.Cliente;

@Service  /*Una anotacion para decorar esta clsae como una de servicio y esta se puede inyectar en los controladores*/
public class ClienteServiceImpl implements IClienteService{

	@Autowired /* Inyeccion de dependencia en Spring con esta anotacion inyecta en este caso el clienteDao para mas informacion buscar inyeccion de dependencias*/
	private IClienteDao clienteDao;
	
	@Override
	//@Transactional(readOnly = true )
	public List<Cliente> findAll() {
		
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void deleteById(Long idCliente) {
		clienteDao.deleteById(idCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long idCliente) {
		return clienteDao.findById(idCliente).orElse(null);
	}
		
}
