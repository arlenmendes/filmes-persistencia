/*
	MANUTENCAO DO SISTEMA REALIZADA POR:
	Valdeci Soares da Silva Junior
                        &
        Arlen Mendes
	Site: conexusecia.com.br
	Contato: (35) 9_9195-8111 - Tim + Whatsapp
	E-mail: contato@conexusecia.com.br
*/
package br.ufla.dcc.ppoo.modelo;

/**
 *
 * Atributos
 */
public class Filme {
    
    private int id;
    private String nome;
    private String genero;
    private int ano;
    private int duracao;
    private String descricao;
    private int usuario_id;

    /**
     * Contrutor da classe filme
     * @param nome
     * @param genero
     * @param ano
     * @param duracao
     * @param descricao
     */
    
    public Filme( String nome, String genero, int ano, int duracao, String descricao){
        this.nome = nome;
        this.genero = genero;
        this.ano = ano;
        this.duracao = duracao;
        this.descricao = descricao;
    }
    
    public Filme(int id, String nome, String genero, int ano, int duracao, String descricao){
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.ano = ano;
        this.duracao = duracao;
        this.descricao = descricao;
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
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * @return the duracao
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * @param duracao the duracao to set
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    
        /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the duracao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
