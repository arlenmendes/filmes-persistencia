/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.gui;

import br.ufla.dcc.ppoo.modelo.Palavra;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arlen
 */
public class PalavrasTableModel extends AbstractTableModel {
    private List<Palavra> palavras;
    private String[] colunas = {"Nome"};
    
    public PalavrasTableModel(List<Palavra> palavras) {
        this.palavras = new ArrayList<>();
        this.palavras = palavras;
    }
    
    public PalavrasTableModel(){
        this.palavras = new ArrayList<>();
    }
    
    public void  adicionarPalavra(Palavra palavra) {
        this.palavras.add(palavra);
        this.fireTableDataChanged();
    }
    
    public void removerPalavra(int linha) {
        if(linha >=0)
            this.palavras.remove(linha);
        this.fireTableDataChanged();
    }
    
    public Palavra buscarPalavra(int linha) {
        return this.palavras.get(linha);
    }
    
    public List<Palavra> buscarPalavras() {
        return this.palavras;
    }
    
    public void limpar() {
        this.palavras.clear();
        fireTableDataChanged();
    }
    
    public void atualizarPalavras(List<Palavra> palavras) {
        this.palavras = palavras;
    }
    
    public void atualizarPalavra(Palavra p, int linha) {
        this.palavras.get(linha).setNome(p.getNome());
        this.palavras.get(linha).setId(p.getId());
    }
    
    @Override
    public int getRowCount() {
        return this.palavras.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        if(linha >= 0){
            switch(coluna){
                case 0: return palavras.get(linha).getNome();
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
