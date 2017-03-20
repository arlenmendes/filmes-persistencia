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
public class Comentario {
    private int id;
    private String texto;
    private int diasAtras;
    private int lista_id;
    public Comentario(int id, String texto, int dias, int lista_id) {
        this.id = id;
        this.texto = texto;
        this.diasAtras = dias;
        this.lista_id = lista_id;
    }
    
    public Comentario(String texto, int lista_id) {
        this.texto = texto;
        this.lista_id = lista_id;
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
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the diasAtras
     */
    public int getDiasAtras() {
        return diasAtras;
    }

    /**
     * @param diasAtras the diasAtras to set
     */
    public void setDiasAtras(int diasAtras) {
        this.diasAtras = diasAtras;
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
}
