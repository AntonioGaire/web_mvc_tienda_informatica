package org.iesvegademijas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.iesvegademijas.model.Usuario;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO{

	@Override
	public void create(Usuario usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
        	conn = connectDB();

        	ps = conn.prepareStatement("insert into usuariov2(username, passwordhash, roleid) values(?, sha2(?, 256), ?)", Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            ps.setString(idx++, usuario.getUsername());
            ps.setString(idx++, usuario.getpassword());
            ps.setString(idx, usuario.getRole());
                   
            int rows = ps.executeUpdate();
            if (rows == 0) 
            	System.out.println("INSERT de Usuario con 0 filas insertadas.");
            
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) 
            	usuario.setId(rsGenKeys.getInt(1));
                      
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
		
	}

	@Override
	public List<Usuario> getAll() {
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Usuario> listUsers = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("select us.id, us.username, ur.rolename from usuariov2 as us inner join userrole as ur on us.roleid = ur.id order by us.id;");          
            while (rs.next()) {
            	Usuario us = new Usuario();
            	int idx = 1;
            	
            	us.setId(rs.getInt(idx++));
            	us.setUsername(rs.getString(idx++));
            	us.setRole(rs.getString(idx));
            	
            	listUsers.add(us);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listUsers;
        
	}

	@Override
	public Optional<Usuario> find(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("select us.id, us.username, ur.rolename from usuariov2 as us inner join userrole as ur on us.roleid = ur.id where us.id = ? ");
        	
        	int idx =  1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Usuario us = new Usuario();
        		idx = 1;
        		
            	us.setId(rs.getInt(idx++));
            	us.setUsername(rs.getString(idx++));
            	us.setRole(rs.getString(idx));
        		
        		return Optional.of(us);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
        return Optional.empty();
	}

	@Override
	public void update(Usuario usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("UPDATE usuariov2 SET username = ? , passwordhash = sha2(?, 256) , roleid = ?  WHERE id = ?");
        	
        	int idx = 1;
        	
        	ps.setString(idx++, usuario.getUsername());
        	ps.setString(idx++, usuario.getpassword());
        	ps.setInt(idx++, Integer.parseInt(usuario.getRole()));
        	ps.setInt(idx, usuario.getId());
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de Usuario con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}

	@Override
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM usuariov2 WHERE id = ?");
        	int idx = 1;        	
        	ps.setInt(idx, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de Usuario con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}

	public Map<Integer, String> getRoles() {
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
		Map<Integer, String> rolemap = new HashMap<Integer, String>();
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT id, rolename FROM userrole");          
            while (rs.next()) {
            	int idx=1;
            	Integer id = rs.getInt(idx++);
            	String rolename = rs.getString(idx);
            	
            	rolemap.put(id, rolename);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return rolemap;
	}

	@Override
	public Optional<Usuario> check(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("select us.id, us.username, ur.rolename from usuariov2 as us inner join userrole as ur"
        			+ " on us.roleid = ur.id "
        			+ "where us.username like ? and us.passwordhash like sha2(?,256)");
        	
        	int idx = 1;      
        	
        	ps.setString(idx++, usuario.getUsername());
        	ps.setString(idx, usuario.getpassword());
        	
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		
        		Usuario us = new Usuario();
        		idx = 1;
        		
            	us.setId(rs.getInt(idx++));
            	us.setUsername(rs.getString(idx++));
            	us.setRole(rs.getString(idx));
        		
        		return Optional.of(us);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		return Optional.empty();
	}

}
