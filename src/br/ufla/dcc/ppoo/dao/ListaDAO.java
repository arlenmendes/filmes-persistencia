/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.dao;

import br.ufla.dcc.ppoo.modelo.Filme;
import br.ufla.dcc.ppoo.modelo.Lista;
import br.ufla.dcc.ppoo.servicos.GerenciadorFilmes;
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
public class ListaDAO {
    private Connection conexao;
    
    private void conectar(){
        conexao = Conexao.conectar();
    }
    
    public void adicionar(Lista l) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();

            String sql = "INSERT INTO lista(nome, autor, usuario_id) "
                       + "VALUES('" + l.getNome() + "', '"
                                    + l.getAutor() + "',"
                                    + l.getUsuario_id() +")";
            
            comando.executeUpdate(sql);
            
            sql = "SELECT id FROM lista  ORDER BY id DESC LIMIT 1";
            
            ResultSet resultado = comando.executeQuery(sql);
            
            resultado.next();
            
            int lista_id = resultado.getInt("id");
            
            for(Filme f : l.getFilmes()) {
                sql = "INSERT INTO lista_filme(lista_id, filme_id) "
                    + "VALUES(" + lista_id + ", " + f.getId() + ");";
                comando.executeUpdate(sql);
            }
            
            for(String chave : l.getChaves()){
                sql = "INSERT INTO chaves(lista_id, nome) "
                    + "VALUES(" + lista_id + ", '" + chave + "');";
                comando.executeUpdate(sql);
            }
            
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public Lista buscaPorId(int id) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sqlLista = "SELECT * FROM lista where id = " + id + ";";
            String sqlChaves = "SELECT * FROM chaves where lista_id = " + id + ";";
            String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista.id = " + id +";";
            
            //recuperar as chaves da lista
            ResultSet chave = comando.executeQuery(sqlChaves);
            List<String> chaves = new ArrayList<>();
            while(chave.next()) {
                chaves.add(chave.getString("nome"));
            }
            
            //recuperar os filmes da lista;
            ResultSet filme = comando.executeQuery(sqlFilmes);
            List<Filme> filmes = new ArrayList<>();
            while(filme.next()) {
                filmes.add(new Filme(filme.getInt("id"), filme.getString("nome"), filme.getString("genero"), filme.getInt("ano"), filme.getInt("duracao"), filme.getString("descricao")));
            }
            
            //recuperar a Lista pelo id
            ResultSet result = comando.executeQuery(sqlLista);
            result.next();
            return new Lista(result.getInt("id"), result.getString("nome"), chaves, filmes, result.getInt("usuario_id"), result.getString("autor"));
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
    }
    
    public void atualizar(Lista l) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sqlLista = "UPDATE lista SET "
                            + "nome = " + l.getNome()
                            + "WHERE id = " + l.getId();
            comando.executeUpdate(sqlLista);
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public void atualizarFilmes(Lista l) throws SQLException{
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            List<Filme> filmesAtualizados = l.getFilmes();
            List<Filme> filmesDoBanco = new ArrayList<>();
            
            String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista.id = " + l.getId() +";";
            ResultSet res = comando.executeQuery(sqlFilmes);
            while(res.next()){
                filmesDoBanco.add(new Filme(res.getInt("id"), res.getString("nome"), res.getString("genero"), res.getInt("ano"), res.getInt("duracao"), res.getString("descricao")));
            }
            //laço de interaçao que verifica se foi adicionado algum filmeRS
            for(Filme filmeAtualizado : filmesAtualizados){
                boolean pertence = false;
                for(Filme filmeDoBanco: filmesDoBanco){
                    if(filmeAtualizado.getId() == filmeDoBanco.getId()){
                        pertence = true;
                    }
                }
                if(!pertence){
                    String sql = "INSERT INTO lista_filme(lista_id, filme_id) "
                    + "VALUES(" + l.getId() + ", " + filmeAtualizado.getId() + ");";
                    comando.executeUpdate(sql);
                }
            }
            
            //laço de interacao que verifica se foi removido algum filmeRS
            for(Filme filmeDoBanco: filmesDoBanco){
                boolean remover = true;
                for(Filme filmeAtualizado : filmesAtualizados){
                    if(filmeAtualizado.getId() == filmeDoBanco.getId()){
                        remover = false;
                    }
                }
                if(!remover) {
                    String sql = "DELETE FROM lista_filme where id = " + filmeDoBanco.getId();
                }
            }
            
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }

    public List<Lista> buscaPorUsuario(int usuario_id) throws SQLException {
        conectar();
        if(conexao != null) {
            List<Lista> listas = new ArrayList<>();
            
            Statement statementLista = conexao.createStatement();
            
            String sqlLista = "SELECT * FROM lista where usuario_id = " + usuario_id + ";";
            
            
            ResultSet listasRS = statementLista.executeQuery(sqlLista);
            
            while(listasRS.next()) {
                
                Statement statementFilme = conexao.createStatement();
                String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista_id = " + listasRS.getInt("id") +";";
                ResultSet filmeRS = statementFilme.executeQuery(sqlFilmes);
                List<Filme> filmes = new ArrayList<>();
                while(filmeRS.next()) {
                    filmes.add(new Filme(filmeRS.getInt("id"), filmeRS.getString("nome"), filmeRS.getString("genero"), filmeRS.getInt("ano"), filmeRS.getInt("duracao"), filmeRS.getString("descricao")));
                }
                statementFilme.close();
                
                Statement statementChaves = conexao.createStatement();
                String sqlChaves = "SELECT * FROM chaves where lista_id = " + listasRS.getInt("id") + ";";
                ResultSet chave = statementChaves.executeQuery(sqlChaves);
                List<String> chaves = new ArrayList<>();
                while(chave.next()) {
                    chaves.add(chave.getString("nome"));
                }
                statementChaves.close();
                
                listas.add(new Lista(listasRS.getInt("id"), listasRS.getString("nome"), chaves, filmes, listasRS.getInt("usuario_id"), listasRS.getString("autor")));
            }
            statementLista.close();
            Conexao.fecharConexao();
            return listas;
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
    }
}
