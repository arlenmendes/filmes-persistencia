/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.modelo;

import br.ufla.dcc.ppoo.seguranca.SessaoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arlen
 */
public class Lista {
    
    private int id;
    private String nome;
    private List<Palavra> chaves;
    private List<Filme> filmes;
    private String autor;
    private int publica;
    private int usuario_id;
    
    
    //esta funcao deve ser chamada apenas ao criar uma nova lista.
    public Lista(String nome, List<Palavra> chaves, List<Filme> filmes){
        this.nome = nome;
//        this.autor = SessaoUsuario.obterInstancia().obterUsuario().obterNome();
        this.chaves = chaves;
//        this.usuario_id = SessaoUsuario.obterInstancia().obterUsuario().obterId();
        this.filmes = filmes;
    }
    
    //esta funcao deve ser chamada para manipular das demais formas
    public Lista(int id, String n, List<Palavra> c, List<Filme> filmes, int usuario_id, String autor, int p){
        this.id = id;
        this.nome = n;
        this.autor = autor;
        this.chaves = c;
        this.usuario_id = usuario_id;
        this.filmes = filmes;
        this.publica = p;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the chaves
     */
    public List<Palavra> getChaves() {
        return chaves;
    }

    /**
     * @param chaves the chaves to set
     */
    public void setChaves(List<Palavra> chaves) {
        this.chaves = chaves;
    }

    /**
     * @return the filmes
     */
    public List<Filme> getFilmes() {
        return filmes;
    }

    /**
     * @param filmes the filmes to set
     */
    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    /**
     * @return the usuario
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return the publica
     */
    public int isPublica() {
        return publica;
    }

    /**
     * @param publica the publica to set
     */
    public void setPublica(int publica) {
        this.publica = publica;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the usuario_id
     */
    public int getUsuario_id() {
        return usuario_id;
    }

    /**
     * @param usuario_id the usuario_id to set
     */
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    
}
