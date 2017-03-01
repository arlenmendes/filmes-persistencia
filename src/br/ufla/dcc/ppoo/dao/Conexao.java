/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author arlen
 */
public class Conexao {
    
    private static final String nomeBD = "filmes";
    private static Connection conexao;
    public static Connection conectar(){
        
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/" + nomeBD, "root", "");
            
            return conexao;
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de Dados: \n " + e.getMessage());
            return null;
        }
        
        
    }
    
    public static void fecharConexao() throws SQLException{
        if(conexao != null){
            conexao.close();
        }
    }
}
