/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.servicos;

import br.ufla.dcc.ppoo.dao.FilmeDAO;
import br.ufla.dcc.ppoo.modelo.Filme;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author arlen
 */
public class GerenciadorFilmes {
    private final FilmeDAO repositorioFilme;
    
    public GerenciadorFilmes(){
        repositorioFilme = new FilmeDAO();
    }
    
    public void cadastrarFilme(Filme f) throws SQLException{
        repositorioFilme.adicionar(f);
    }
    
    public void atualizarFilme(Filme f) throws SQLException{
        repositorioFilme.alterar(f);
    }
    
    public List<Filme> buscarTodosFilmes() throws SQLException{
        return repositorioFilme.buscarTodos();
    }
    
    public Filme buscarFilmePorId(int id) throws SQLException {
        return repositorioFilme.buscarPorId(id);
    }
    
    public Filme buscaUltimoFilmeCadastrado() throws SQLException {
        return repositorioFilme.buscarUltimoCadastrado();
    }
    
}
