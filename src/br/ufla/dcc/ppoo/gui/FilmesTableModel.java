/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.gui;

import br.ufla.dcc.ppoo.modelo.Filme;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arlen
 */
public class FilmesTableModel extends AbstractTableModel {
    
    private List<Filme> filmes;
    private String[] colunas = {"Codigo" , "Nome" ,"Genero"};

    public FilmesTableModel(List<Filme> filmes) {
        this.filmes = filmes;
    }
    
    public FilmesTableModel(){
        this.filmes = new ArrayList<>();
    }
    
    public void  adicionarFilme(Filme f) {
        this.filmes.add(f);
        this.fireTableDataChanged();
    }
    
    public void atualizarFilme(Filme f, int linha) {
        this.filmes.get(linha).setNome(f.getNome());
        this.filmes.get(linha).setGenero(f.getGenero());
        this.filmes.get(linha).setDescricao(f.getDescricao());
        this.filmes.get(linha).setAno(f.getAno());
        this.filmes.get(linha).setDuracao(f.getDuracao());
        this.fireTableDataChanged();
    }
    
    public Filme buscarFilme(int linha) {
        return this.filmes.get(linha);
    }
    
    public void limpar() {
        this.filmes.clear();
        fireTableDataChanged();
    }
    
    public void atualizarListaFilmes(List<Filme> filmes) {
//        limpar();
        this.filmes = filmes;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return this.filmes.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        if(linha >= 0){
            switch(coluna){
                case 0: return filmes.get(linha).getId();
                case 1: return filmes.get(linha).getNome();
                case 2: return filmes.get(linha).getGenero();
                default: System.out.println("Parametro de coluna invalido.");
            }
        }
        return null;
    }
    
    @Override  
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        return false;  
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }
    
}
