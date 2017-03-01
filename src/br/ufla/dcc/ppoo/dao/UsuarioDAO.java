/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.dao;

import br.ufla.dcc.ppoo.modelo.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author arlen
 */
public class UsuarioDAO {
    private Connection conexao;
    
//   public List<Usuario> buscarUsuarios(){
//       
//       return 
//   }
    
    private void conectar(){
        conexao = Conexao.conectar();
    }
    
    public void adicionar(Usuario u) throws SQLException{
        conectar();
        if(conexao != null){
            Statement comando = conexao.createStatement();
            
            String sql = "INSERT INTO usuario(NOME, LOGIN, SENHA) VALUES('" + u.obterNome() + "','"+ u.obterLogin() +"','"+ new String(u.obterSenha()) +"');";
            
            comando.executeUpdate(sql);
            
            comando.close();
            Conexao.fecharConexao();
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public Usuario buscarUsuarioPorId(int id) throws SQLException {
        conectar();
        if(conexao != null){
            Statement comando = conexao.createStatement();
            String sql = "SELECT * FROM usuario where id = " + id + ";";
            
            ResultSet resultado = comando.executeQuery(sql);
            Usuario u = null;
            while(resultado.next()){
                u = new Usuario(resultado.getInt("id"), resultado.getString("login"), resultado.getString("senha").toCharArray(), resultado.getString("nome"));
            }
            
            comando.close();
            Conexao.fecharConexao();
            return u;
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
        
    }
    
    public Usuario buscarUsuarioLogin(String login) throws SQLException {
        conectar();
        if(conexao != null){
            Statement comando = conexao.createStatement();
            String sql = "SELECT * FROM usuario where login = '" + login + "';";
            
            ResultSet resultado = comando.executeQuery(sql);
            Usuario u = null;
            while(resultado.next()){
                u = new Usuario(resultado.getInt("id"), resultado.getString("login"), resultado.getString("senha").toCharArray(), resultado.getString("nome"));
            }
            
            comando.close();
            Conexao.fecharConexao();
            return u;
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
    }
}
