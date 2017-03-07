/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.gui;

import br.ufla.dcc.ppoo.modelo.Lista;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arlen
 */
public class ListasTableModel extends AbstractTableModel {
    
    private List<Lista> listas;
    private String[] colunas = {"Codigo", "Nome","Palavras-Chave"};
    
    public ListasTableModel(List<Lista> listas) {
        this.listas = listas;
    }
    
    public ListasTableModel(){
        this.listas = new ArrayList<>();
    }
    
    public void  adicionarFilme(Lista l) {
        this.listas.add(l);
        this.fireTableDataChanged();
    }
    
    public Lista buscarLista(int linha) {
        return this.listas.get(linha);
    }
    
    public void limpar() {
        this.listas.clear();
        fireTableDataChanged();
    }
    
    public void atualizarListaFilmes(List<Lista> listas) {
        this.listas = listas;
    }
    
    public void atualizarLista(Lista l, int linha) {
        this.listas.get(linha).setNome(l.getNome());
        this.listas.get(linha).setPublica(l.isPublica());
        this.listas.get(linha).setFilmes(l.getFilmes());
        this.listas.get(linha).setChaves(l.getChaves());
        this.listas.get(linha).setAutor(l.getAutor());
    }
    
    @Override
    public int getRowCount() {
        return this.listas.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        if(linha >= 0){
            switch(coluna){
                case 0: return listas.get(linha).getId();
                case 1: return listas.get(linha).getNome();
                case 2: 
                    String chaves = "";
                    for(String chave: this.listas.get(linha).getChaves()){
                        chaves = chaves + chave + ", ";
                    }
                    return chaves;
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
