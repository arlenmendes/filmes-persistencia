/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.dao;

import br.ufla.dcc.ppoo.modelo.Filme;
import br.ufla.dcc.ppoo.modelo.Lista;
import br.ufla.dcc.ppoo.modelo.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arlen
 */
public class TesteBD {
    
    public static void main(String[] arg) throws SQLException{
//        UsuarioDAO dao = new UsuarioDAO();
//        char[] senha = new char[]{'1', '2', '3'};
//        dao.adicionar(new Usuario("valdeci", senha, "Valdeci"));
        ListaDAO dao = new ListaDAO();
        FilmeDAO dao2 = new FilmeDAO();
//        dao.adicionar(new Filme("Vingadores", "Açao/Ficçao", 2010, 115, "Filme da Marvel"));
//        List<Filme> filmes = dao.buscarTodos();
//        for(Filme f : filmes){
//            System.out.println(f.getId() + " - " + f.getNome() + "\n\t" + f.getDescricao());
//        }
        
//        Filme f = dao.buscarPorId(1);
//        System.out.println(f.getId() + " - " + f.getNome() + "\n\t" + f.getDescricao());
        List<String> chaves = new ArrayList();
        chaves.add("arlen");
        chaves.add("terror");
//        dao.adicionar(new Lista("TesteBD", chaves, dao2.buscarTodos(2)));
        Lista lista = dao.buscaPorId(1);
        
        System.out.println(lista.getAutor());
    }
}
