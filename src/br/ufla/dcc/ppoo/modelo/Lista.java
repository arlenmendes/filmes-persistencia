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
    private List<Avaliacao> avaliacoes;
    private List<Comentario> comentarios;
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
    public Lista(int id, String n, List<Palavra> c, List<Filme> filmes, int usuario_id, String autor, int p, List<Avaliacao> avaliacoes, List<Comentario> comentarios){
        this.id = id;
        this.nome = n;
        this.autor = autor;
        this.chaves = c;
        this.usuario_id = usuario_id;
        this.filmes = filmes;
        this.publica = p;
        this.avaliacoes = avaliacoes;
        this.comentarios = comentarios;
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

    /**
     * @return the avaliacoes
     */
    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    /**
     * @param avaliacoes the avaliacoes to set
     */
    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    /**
     * @return the comentarios
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    public String toStringTextPane() {
        
        int totalAvaliacao = 0;
        
        for(Avaliacao a : getAvaliacoes()) {
            totalAvaliacao += a.getNota();
        }
        
        String avaliacao;
        
        if(totalAvaliacao < 1000) {
            avaliacao = Integer.toString(totalAvaliacao);
        } else if (totalAvaliacao < 1000000){
            totalAvaliacao = totalAvaliacao / 1000;
            
            avaliacao = totalAvaliacao + "k";
        } else {
            totalAvaliacao = totalAvaliacao / 1000000;
            
            avaliacao = totalAvaliacao + "m";
        }
        
        String dados = "<pre>";
        
        String titulo = "<b>Lista:</b> " + this.nome + " (" + avaliacao + ")" + "<br>";
        
        String palavras = "<b>Palavras-chave:</b> ";
        
        for(Palavra p : this.chaves){
            palavras += p.getNome() + ", ";
        }
        
        palavras += "<br>";
        
        String filmes = "<b>Lista de Filmes:</b> <br>";
        
        for(Filme f : this.filmes){
            filmes += "\t" + f.getId() + ". " + f.getNome() + " (" + f.getGenero() + ")<br>";
        }
        String comentarios = "<b>Comentarios</b> <br>";
        
        for(Comentario c : this.getComentarios()){
            comentarios += "\t - " + c.getTexto();
            if(c.getDiasAtras() == 0 ){
                comentarios += " (Hoje)<br>";
            } else {
                comentarios += "(" + c.getDiasAtras() + "d atras)<br>";
            }
        }
        
        dados += titulo + palavras + filmes + comentarios + "</pre>";
        
        return dados;
    }
    
    public String toStringHtml() {
        
        int totalAvaliacao = 0;
        
        for(Avaliacao a : getAvaliacoes()) {
            totalAvaliacao += a.getNota();
        }
        
        String avaliacao;
        
        if(totalAvaliacao < 1000) {
            avaliacao = Integer.toString(totalAvaliacao);
        } else if (totalAvaliacao < 1000000){
            totalAvaliacao = totalAvaliacao / 1000;
            
            avaliacao = totalAvaliacao + "k";
        } else {
            totalAvaliacao = totalAvaliacao / 1000000;
            
            avaliacao = totalAvaliacao + "m";
        }
        
        
        String dados = "";
        String abreHtml = "<html>", fechaHtml = "</html>";
        String head = "<head>\n" +
                "	<meta charset=\"UTF-8\">\n" +
        "	<title>Teste</title>\n" +
        "	<link rel=\"stylesheet\" href=\"style.css\">\n" +
        "</head>";
        
        String body = "<body>";
        
        body += "<div class=\"container\">";
        body += "<div class=\"cabecalho\">";
        body += "<h1>" + this.getNome() + " (" + avaliacao + ")</h1>";
        body += "<h3>" + this.getAutor() + "</h3>";
        body += "</div>";
        body += "<div class=\"conteudo\">";
        body += "<div class=\"dados\">";
        body += "<h2>Filmes</h2>";
        body += "<ul>";
        for(Filme f : this.filmes){
            body +="<li> - " + f.getNome() + "  (" + f.getGenero() +  ")</li>";
        }
        body += "</ul></div>";
        body += "<div class=\"dados\">";
        body += "<h2>Comentarios</h2>";
        body += "<ul>";
        for(Comentario c : this.comentarios){
            body += "<li> - " + c.getTexto();
            if(c.getDiasAtras() == 0 ){
                body += "  (Hoje)</li>";
            } else {
                body += "  (" + c.getDiasAtras() + "d atras)</li>";
            }
        }
        body += "</ul></div>";
        body += "</div>";
        body += "</div>";
        body += "</body>";

        return dados += abreHtml + head + body + fechaHtml;
    }
}
