/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.servicos;

import br.ufla.dcc.ppoo.dao.ListaDAO;
import br.ufla.dcc.ppoo.modelo.Avaliacao;
import br.ufla.dcc.ppoo.modelo.Comentario;
import br.ufla.dcc.ppoo.modelo.Lista;
import br.ufla.dcc.ppoo.modelo.Palavra;
import br.ufla.dcc.ppoo.seguranca.SessaoUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
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
        l.setAutor(SessaoUsuario.obterInstancia().obterUsuario().obterNome());
        l.setUsuario_id(SessaoUsuario.obterInstancia().obterUsuario().obterId());
        //passa 1 por padrao por que 1 indica lista privada
        l.setPublica(1);
        repositorioListas.adicionar(l);
    }
    
    public List<Lista> buscarListasUsuarioLogado() throws SQLException {
        List<Lista> listas = new ArrayList<>();
        listas = repositorioListas.buscaPorUsuario(SessaoUsuario.obterInstancia().obterUsuario().obterId());
        return listas;
    }
    
    //Este metodo atualiza apenas as INFORMAÇOES da Lista. Nao inclui a lista de Filmes
    public void atualizarDadosLista(Lista l) throws SQLException {
        repositorioListas.atualizar(l);
        repositorioListas.atualizarFilmes(l);
        repositorioListas.atualizarPalavras(l);
    }
    
    //Este metodo atualiza apenas os FILMES da Lista. Nao inclui as iformacoes da lista
    public void atualizarFilmesDaLista(Lista l) throws SQLException {
        repositorioListas.atualizarFilmes(l);
    }
    
    public void alterarVisibilidade(int id, int valor) throws SQLException{
        repositorioListas.alterarVisibilidade(id, valor);
    }
    
    public List<Lista> buscarListasPublicas(String filtro) throws SQLException {
        
        if(!filtro.equals("")){
            List<Lista> listas = repositorioListas.bucarPublicas();
            
            List<Lista> listasFiltradas = new ArrayList<>();
            
            for(Lista lista : listas){
                boolean aceito = true;
                if(lista.getNome().equals(filtro)){
                    listasFiltradas.add(lista);
                    aceito = false;
                }
                if(aceito){
                    for(Palavra palavra : lista.getChaves()){
                        if(palavra.getNome().equals(filtro)){
                            listasFiltradas.add(lista);
                            break;
                        }
                    }
                }
                
            }
            
            return listasFiltradas;
        } else {
            List<Lista> listas = repositorioListas.bucarPublicas();
            return listas;
        }
        
    }
    
    public void cadastrarListaImportada(Lista l) throws SQLException{
        l.setUsuario_id(SessaoUsuario.obterInstancia().obterUsuario().obterId());
        //passa 1 por padrao por que 1 indica lista privada
        l.setPublica(1);
        repositorioListas.adicionar(l);
    }
    
    public void avaliarLista(Avaliacao a) throws SQLException {
        repositorioListas.adicionarAvaliacao(a);
    }
    
    public void comentarLista(Comentario c) throws SQLException {
        repositorioListas.comentar(c);
    }
}
