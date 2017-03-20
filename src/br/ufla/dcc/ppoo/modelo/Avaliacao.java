/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.modelo;

/**
 *
 * @author arlen
 */
public class Avaliacao {
    private int id;
    private int nota;
    private int lista_id;
    private int usuario_id;
    
    public Avaliacao(int id, int nota, int lista, int usuario){
        this.id = id;
        this.nota = nota;
        this.lista_id = lista;
        this.usuario_id = usuario;
    }
    
    public Avaliacao(int nota, int lista, int usuario){
        this.nota = nota;
        this.lista_id = lista;
        this.usuario_id = usuario;
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
     * @return the nota
     */
    public int getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(int nota) {
        this.nota = nota;
    }

    /**
     * @return the lista_id
     */
    public int getLista_id() {
        return lista_id;
    }

    /**
     * @param lista_id the lista_id to set
     */
    public void setLista_id(int lista_id) {
        this.lista_id = lista_id;
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
