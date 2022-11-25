package org.iesvegademijas.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.iesvegademijas.model.Usuario;

public interface UsuarioDAO {
	
	public void create(Usuario usuario);
	
	public List<Usuario> getAll();
	public Optional<Usuario>  find(int id);
	
	public void update(Usuario usuario);
	
	public void delete(int id);
	
	public Optional<Usuario> check(Usuario usuario);
	
	public Map<Integer, String> getRoles();
	
}
