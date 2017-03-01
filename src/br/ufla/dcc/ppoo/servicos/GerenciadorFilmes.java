/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.servicos;

import br.ufla.dcc.ppoo.dao.FilmeDAO;
import br.ufla.dcc.ppoo.modelo.Filme;

/**
 *
 * @author arlen
 */
public class GerenciadorFilmes {
    private final FilmeDAO repositorioFilme;
    
    public GerenciadorFilmes(){
        repositorioFilme = new FilmeDAO();
    }
    
    public void cadastrarFilme(Filme f){
        
    }
}
