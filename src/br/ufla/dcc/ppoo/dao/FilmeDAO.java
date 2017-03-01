/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.dao;

import br.ufla.dcc.ppoo.modelo.Filme;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author arlen
 */
public class FilmeDAO {
    private Connection conexao;
    
    private void conectar(){
        conexao = Conexao.conectar();
    }
    
    public void adicionar(Filme f) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sql = "INSERT INTO filme(nome, genero, ano, duracao, descricao)"
                       + "VALUES('" + f.getNome() + "','" + f.getGenero() + "', " + f.getAno() +", " + f.getDuracao() + ", '" + f.getDescricao() + "')";
            
            comando.executeUpdate(sql);
            
            comando.close();
            Conexao.fecharConexao();
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public List<Filme> buscarTodos() throws SQLException {
        conectar();
        List<Filme> filmes = new ArrayList<>();
        if(conexao != null){
            Statement comando = conexao.createStatement();
            
            String sql = "SELECT * FROM filme;";
            
            ResultSet resultado = comando.executeQuery(sql);
            
            while(resultado.next()){
                filmes.add(new Filme(resultado.getInt("id"), resultado.getString("nome"), resultado.getString("genero"), resultado.getInt("ano"), resultado.getInt("duracao"), resultado.getString("descricao")));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
        
        return filmes;
    }
    
    public Filme buscarPorId(int id) throws SQLException {
        conectar();
        if(conexao != null){
            Statement comando = conexao.createStatement();
            String sql = "SELECT * FROM filme where id = " + id + ";";
            
            ResultSet resultado = comando.executeQuery(sql);
            Filme f = null;
            while(resultado.next()){
                f = new Filme(resultado.getInt("id"), resultado.getString("nome"), resultado.getString("genero"), resultado.getInt("ano"), resultado.getInt("duracao"), resultado.getString("descricao"));
            }
            
            comando.close();
            Conexao.fecharConexao();
            return f;
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
        
    }
    
    public void alterar(Filme f) throws SQLException{
        conectar();
        if(conexao!=null){
            Statement comando = conexao.createStatement();
            
            String sql = "UPDATE filme SET " +
                         "nome = '" + f.getNome() + "', " +
                         "genero = '" + f.getGenero() + "', " +
                         "ano = " + f.getAno() + ", " +
                         "duracao = " + f.getDuracao() + ", " +
                         "descricao = '" + f.getDescricao() + "' " + 
                         "where id = " + f.getId() + ";";
            
            comando.executeUpdate(sql);
            
            comando.close();
            Conexao.fecharConexao();
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
}
