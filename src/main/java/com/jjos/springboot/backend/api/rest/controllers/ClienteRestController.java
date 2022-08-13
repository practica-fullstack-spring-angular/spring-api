package com.jjos.springboot.backend.api.rest.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jjos.springboot.backend.api.rest.models.entity.Cliente;
import com.jjos.springboot.backend.api.rest.models.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/")
public class ClienteRestController {
	
	@Autowired
	private IClienteService interfaceClienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return interfaceClienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente= interfaceClienteService.findById(id);

			if(cliente== null) {
				response.put("mensaje", "El cliente ID: " .concat(id.toString().concat(" no existe en nuestra base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				
				return new  ResponseEntity<Cliente>(cliente, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?>  create(@Valid @RequestBody Cliente cliente, BindingResult result){
		
		Cliente clienteNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		/*
		 * if(result.hasErrors()) {
		List<String> error = new ArrayList<>();
		for( FieldError err: result.getFieldErrors()) {
			error.add("El campo '"+ err.getField()+"' "+err.getDefaultMessage());
		}
		*/
		if(result.hasErrors()) {
			
			/*de la forma jdk 8 usando programacion funcional*/
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err ->
						{	return "El campo '"+ err.getField()+"' "+err.getDefaultMessage();
						})
					.collect(Collectors.toList());
			
			response.put("mensaje", "Error de validacion de campos.");
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			
			clienteNuevo = interfaceClienteService.save(cliente);
			response.put("mensaje", "Cliente creado con exito");
			response.put("cliente", clienteNuevo);
			return new ResponseEntity<Map<String,Object>> (response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar una insercion en la base de datos.");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@PutMapping("/clientes/{id}")
	public  ResponseEntity<?> update(@Valid  @RequestBody Cliente cliente, BindingResult result , @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			
			/*de la forma jdk 8 usando programacion funcional*/
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err ->
						{	return "El campo '"+ err.getField()+"' "+err.getDefaultMessage();
						})
					.collect(Collectors.toList());
			
			response.put("mensaje", "Error de validacion de campos.");
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Cliente clienteActualizado = null;
			Cliente clienteEncontrado = interfaceClienteService.findById(id);
			
			if(clienteEncontrado == null) {
				response.put("mensaje", "El ID "+id.toString()+" no existe");
				return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NOT_FOUND);
			}else {
				
				clienteEncontrado.setApellido(cliente.getApellido());
				clienteEncontrado.setNombre(cliente.getNombre());
				clienteEncontrado.setEmail(cliente.getEmail());
				
				clienteActualizado =  interfaceClienteService.save(clienteEncontrado);
				response.put("mensaje", "Cliente actualizado con exito");
				response.put("cliente", clienteActualizado);
				
				return new ResponseEntity<Map<String,Object>> (response, HttpStatus.CREATED);
			}					
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos.");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			interfaceClienteService.deleteById(id);
			response.put("mensaje", "El cliente con ID " +id.toString() + " ha sido eliminado." );
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos.");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	

}
