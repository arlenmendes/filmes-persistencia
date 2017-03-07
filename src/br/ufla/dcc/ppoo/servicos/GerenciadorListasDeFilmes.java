/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.servicos;

import br.ufla.dcc.ppoo.dao.ListaDAO;
import br.ufla.dcc.ppoo.modelo.Lista;
import br.ufla.dcc.ppoo.seguranca.SessaoUsuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author arlen
 */
public class GerenciadorListasDeFilmes {
    private final ListaDAO repositorioListas;
    
    public GerenciadorListasDeFilmes(){
        repositorioListas = new ListaDAO();
    }
    
    public void cadastrarLista(Lista l) throws SQLException{
        repositorioListas.adicionar(l);
    }
    
    public List<Lista> buscarListasUsuario() throws SQLException {
        
        List<Lista> listas = repositorioListas.buscaPorUsuario(SessaoUsuario.obterInstancia().obterUsuario().obterId());
        return listas;
    }
    
    //Este metodo atualiza apenas as INFORMAÇOES da Lista. Nao inclui a lista de Filmes
    public void atualizarDadosLista(Lista l) throws SQLException {
        repositorioListas.atualizar(l);
    }
    
    //Este metodo atualiza apenas os FILMES da Lista. Nao inclui as iformacoes da lista
    public void atualizarFilmesDaLista(Lista l) throws SQLException {
        repositorioListas.atualizarFilmes(l);
    }
}
