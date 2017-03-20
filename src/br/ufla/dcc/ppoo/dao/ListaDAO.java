/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.dao;

import br.ufla.dcc.ppoo.modelo.Avaliacao;
import br.ufla.dcc.ppoo.modelo.Comentario;
import br.ufla.dcc.ppoo.modelo.Filme;
import br.ufla.dcc.ppoo.modelo.Lista;
import br.ufla.dcc.ppoo.modelo.Palavra;
import br.ufla.dcc.ppoo.servicos.GerenciadorFilmes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.transform.Result;

/**
 *
 * @author arlen
 */
public class ListaDAO {
    private Connection conexao;
    
    private void conectar(){
        conexao = Conexao.conectar();
    }
    
    public void adicionar(Lista l) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();

            String sql = "INSERT INTO lista(nome, autor, usuario_id, publica) "
                       + "VALUES('" + l.getNome() + "', '"
                                    + l.getAutor() + "',"
                                    + l.getUsuario_id() + ", "
                                    + l.isPublica() +")";
            
            comando.executeUpdate(sql);
            
            sql = "SELECT id FROM lista  ORDER BY id DESC LIMIT 1";
            
            ResultSet resultado = comando.executeQuery(sql);
            
            resultado.next();
            
            int lista_id = resultado.getInt("id");
            //relaciona a lista aos filmes selecionados
            for(Filme f : l.getFilmes()) {
                sql = "INSERT INTO lista_filme(lista_id, filme_id) "
                    + "VALUES(" + lista_id + ", " + f.getId() + ");";
                comando.executeUpdate(sql);
            }
            
            
            //cadastra as palavras chaves
            for(Palavra chave : l.getChaves()){
                sql = "INSERT INTO chaves(lista_id, nome) "
                    + "VALUES(" + lista_id + ", '" + chave.getNome() + "');";
                comando.executeUpdate(sql);
            }
            
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public Lista buscaPorId(int id) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sqlLista = "SELECT * FROM lista where id = " + id + ";";
            String sqlChaves = "SELECT * FROM chaves where lista_id = " + id + ";";
            String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista.id = " + id +";";
            String sqlAvaliacoes = "SELECT * FROM avaliacao WHERE lista_id = " + id +";";
            String sqlComentarios = "SELECT * FROM comentario WHERE lista_id = " + id +";";
            
            //recuperar as chaves da lista
            ResultSet chave = comando.executeQuery(sqlChaves);
            List<Palavra> chaves = new ArrayList<>();
            while(chave.next()) {
                chaves.add(new Palavra(chave.getInt("id"), chave.getString("nome")));
            }
            
            //recuperar os filmes da lista;
            ResultSet filme = comando.executeQuery(sqlFilmes);
            List<Filme> filmes = new ArrayList<>();
            while(filme.next()) {
                filmes.add(new Filme(filme.getInt("id"), filme.getString("nome"), filme.getString("genero"), filme.getInt("ano"), filme.getInt("duracao"), filme.getString("descricao")));
            }
            
            //recuperar as avaliacoes
            ResultSet avaliacoesRS = comando.executeQuery(sqlAvaliacoes);
            List<Avaliacao> avaliacoes = new ArrayList<>();
            while(avaliacoesRS.next()){
                avaliacoes.add(new Avaliacao(avaliacoesRS.getInt("id"), avaliacoesRS.getInt("nota"), avaliacoesRS.getInt("lista_id"), avaliacoesRS.getInt("usuario_id")));
            }
            
            //Recuperar os comentarios
            ResultSet comentarioRS = comando.executeQuery(sqlComentarios);
            List<Comentario> comentarios = new ArrayList<>();
            while(comentarioRS.next()){
                //calcula quantos dias se passaram
                Date dataComentario = comentarioRS.getDate("data");
                Date atual = new Date(System.currentTimeMillis());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Calendar dts = Calendar.getInstance();
                dts.setTime(new Date());
                Calendar dte = Calendar.getInstance();
                dte.setTime(dataComentario);
                dts.add(Calendar.DATE, - dte.get(Calendar.DAY_OF_MONTH));
                int dias = dts.get(Calendar.DAY_OF_MONTH);
                
                comentarios.add(new Comentario(comentarioRS.getInt("id"), comentarioRS.getString("texto"), dias, comentarioRS.getInt("lista_id")));
                
            }
            
            
            
            //recuperar a Lista pelo id
            ResultSet result = comando.executeQuery(sqlLista);
            result.next();
            return new Lista(result.getInt("id"), result.getString("nome"), chaves, filmes, result.getInt("usuario_id"), result.getString("autor"), result.getInt("publica"), avaliacoes, comentarios);
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
    }
    
    public void atualizar(Lista l) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sqlLista = "UPDATE lista SET "
                            + "nome = '" + l.getNome()
                            + "' WHERE id = " + l.getId() + ";";
            comando.executeUpdate(sqlLista);
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public void atualizarFilmes(Lista l) throws SQLException{
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            List<Filme> filmesAtualizados = l.getFilmes();
            List<Filme> filmesDoBanco = new ArrayList<>();
            
            String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista_id = " + l.getId() +";";
            ResultSet res = comando.executeQuery(sqlFilmes);
            while(res.next()){
                filmesDoBanco.add(new Filme(res.getInt("id"), res.getString("nome"), res.getString("genero"), res.getInt("ano"), res.getInt("duracao"), res.getString("descricao")));
            }
            //laço de interaçao que verifica se foi adicionado algum filmeRS
            for(Filme filmeAtualizado : filmesAtualizados){
                boolean pertence = false;
                for(Filme filmeDoBanco: filmesDoBanco){
                    if(filmeAtualizado.getId() == filmeDoBanco.getId()){
                        pertence = true;
                    }
                }
                if(!pertence){
                    String sql = "INSERT INTO lista_filme(lista_id, filme_id) "
                    + "VALUES(" + l.getId() + ", " + filmeAtualizado.getId() + ");";
                    comando.executeUpdate(sql);
                }
            }
            
            //laço de interacao que verifica se foi removido algum filmeRS
            for(Filme filmeDoBanco: filmesDoBanco){
                boolean remover = true;
                for(Filme filmeAtualizado : filmesAtualizados){
                    if(filmeAtualizado.getId() == filmeDoBanco.getId()){
                        remover = false;
                    }
                }
                if(remover) {
                    String sql = "DELETE FROM lista_filme WHERE filme_id = " + filmeDoBanco.getId() + " and lista_id = " + l.getId() + ";";
                    comando.executeUpdate(sql);
                }
            }
            
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public void atualizarPalavras(Lista l) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sqlPalavras = "SELECT * FROM chaves WHERE lista_id = " + l.getId() + ";";
            ResultSet res = comando.executeQuery(sqlPalavras);
            
            List<Palavra> palavrasDoBanco = new ArrayList<>();
            List<Palavra> palavrasDaLista = l.getChaves();
            
            while(res.next()) {
                palavrasDoBanco.add(new Palavra(res.getInt("id"), res.getString("nome")));
            }
            //laço de interaçao que verifica se foi adicionado alguma palavra
            for(Palavra palavraDaLista : palavrasDaLista) {
                boolean pertence = false;
                    for(Palavra palavraDoBanco : palavrasDoBanco) {
                    if(palavraDoBanco.getNome().equals(palavraDaLista.getNome()))
                        pertence = true;
                }
                if(!pertence){
                    String sql = "INSERT INTO chaves(nome, lista_id) values('"+ palavraDaLista.getNome() +"', " + l.getId() +");";
                    comando.executeUpdate(sql);
                }
            }
            
            //laço de interacao que verifica se foi removida alguma palavra chave
            for(Palavra palavraDoBanco : palavrasDoBanco) {
                boolean remover = true;
                for(Palavra palavraDaLista : l.getChaves()){
                    if(palavraDoBanco.getNome().equals(palavraDaLista.getNome()))
                        remover = false;
                }
                if(remover){
                    String sql = "DELETE FROM chaves WHERE lista_id = " + l.getId() + " and nome = '" + palavraDoBanco.getNome() + "';";
                    comando.executeUpdate(sql);
                }
            }
            comando.close();
            Conexao.fecharConexao();
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }

    public List<Lista> buscaPorUsuario(int usuario_id) throws SQLException {
        conectar();
        if(conexao != null) {
            List<Lista> listas = new ArrayList<>();
            
            Statement statementLista = conexao.createStatement();
            
            String sqlLista = "SELECT * FROM lista where usuario_id = " + usuario_id + ";";
            
            
            ResultSet listasRS = statementLista.executeQuery(sqlLista);
            
            while(listasRS.next()) {
                
                Statement statementFilme = conexao.createStatement();
                String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista_id = " + listasRS.getInt("id") +";";
                ResultSet filmeRS = statementFilme.executeQuery(sqlFilmes);
                List<Filme> filmes = new ArrayList<>();
                while(filmeRS.next()) {
                    filmes.add(new Filme(filmeRS.getInt("id"), filmeRS.getString("nome"), filmeRS.getString("genero"), filmeRS.getInt("ano"), filmeRS.getInt("duracao"), filmeRS.getString("descricao")));
                }
                statementFilme.close();
                
                Statement statementChaves = conexao.createStatement();
                String sqlChaves = "SELECT * FROM chaves where lista_id = " + listasRS.getInt("id") + ";";
                ResultSet chave = statementChaves.executeQuery(sqlChaves);
                List<Palavra> chaves = new ArrayList<>();
                while(chave.next()) {
                    chaves.add(new Palavra (chave.getInt("id"), chave.getString("nome")));
                }
                statementChaves.close();
                
                Statement statementAvaliacoes = conexao.createStatement();
                
                String sqlAvaliacoes = "SELECT * FROM avaliacao where lista_id = " + listasRS.getInt("id");
                ResultSet avaliacao = statementAvaliacoes.executeQuery(sqlAvaliacoes);
                List<Avaliacao> avaliacoes = new ArrayList<>();
                while(avaliacao.next()){
                    avaliacoes.add(new Avaliacao(avaliacao.getInt("id"), avaliacao.getInt("nota"), avaliacao.getInt("lista_id"), avaliacao.getInt("usuario_id")));
                }
                
                statementAvaliacoes.close();
                
                //Recuperar os comentarios
                
                Statement statementComentario = conexao.createStatement();
                String sqlComentarios = "SELECT * FROM comentario where lista_id = " + listasRS.getInt("id");;
                ResultSet comentarioRS = statementComentario.executeQuery(sqlComentarios);
                List<Comentario> comentarios = new ArrayList<>();
                while(comentarioRS.next()){
                    //calcula quantos dias se passaram
                    Date dataComentario = comentarioRS.getDate("data");
                    Date atual = new Date(System.currentTimeMillis());
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar dts = Calendar.getInstance();
                    dts.setTime(atual);
                    Calendar dte = Calendar.getInstance();
                    dte.setTime(dataComentario);
                    dts.add(Calendar.DATE, - dte.get(Calendar.DATE));
                    int dias = dts.get(Calendar.DAY_OF_MONTH);

                    comentarios.add(new Comentario(comentarioRS.getInt("id"), comentarioRS.getString("texto"), dias, comentarioRS.getInt("lista_id")));

                }
                
                listas.add(new Lista(listasRS.getInt("id"), listasRS.getString("nome"), chaves, filmes, listasRS.getInt("usuario_id"), listasRS.getString("autor"), listasRS.getInt("publica"), avaliacoes, comentarios));
            }
            statementLista.close();
            Conexao.fecharConexao();
            return listas;
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
    }
    
    public void alterarVisibilidade(int id, int valor) throws SQLException{
        conectar();
        if(conexao != null){
            Statement st = conexao.createStatement();
            
            String sql = "UPDATE lista SET publica = " + valor + " where id = " + id + ";";
            
            st.executeUpdate(sql);
        }
    }
    
    public List<Lista> bucarPublicas() throws SQLException {
        
        conectar();
        if(conexao != null) {
            List<Lista> listas = new ArrayList<>();
            
            Statement statementLista = conexao.createStatement();
            
            String sqlLista = "SELECT * FROM lista where publica = 0;";
            
            
            ResultSet listasRS = statementLista.executeQuery(sqlLista);
            
            while(listasRS.next()) {
                
                Statement statementFilme = conexao.createStatement();
                String sqlFilmes = "SELECT * FROM lista_filme_view WHERE lista_id = " + listasRS.getInt("id") +";";
                ResultSet filmeRS = statementFilme.executeQuery(sqlFilmes);
                List<Filme> filmes = new ArrayList<>();
                while(filmeRS.next()) {
                    filmes.add(new Filme(filmeRS.getInt("id"), filmeRS.getString("nome"), filmeRS.getString("genero"), filmeRS.getInt("ano"), filmeRS.getInt("duracao"), filmeRS.getString("descricao")));
                }
                statementFilme.close();
                
                Statement statementChaves = conexao.createStatement();
                String sqlChaves = "SELECT * FROM chaves where lista_id = " + listasRS.getInt("id") + ";";
                ResultSet chave = statementChaves.executeQuery(sqlChaves);
                List<Palavra> chaves = new ArrayList<>();
                while(chave.next()) {
                    chaves.add(new Palavra (chave.getInt("id"), chave.getString("nome")));
                }
                statementChaves.close();
                
                Statement statementAvaliacoes = conexao.createStatement();
                
                String sqlAvaliacoes = "SELECT * FROM avaliacao where lista_id = " + listasRS.getInt("id");
                ResultSet avaliacao = statementAvaliacoes.executeQuery(sqlAvaliacoes);
                List<Avaliacao> avaliacoes = new ArrayList<>();
                while(avaliacao.next()){
                    avaliacoes.add(new Avaliacao(avaliacao.getInt("id"), avaliacao.getInt("nota"), avaliacao.getInt("lista_id"), avaliacao.getInt("usuario_id")));
                }
                statementAvaliacoes.close();
                
                Statement statementComentario = conexao.createStatement();
                String sqlComentarios = "SELECT * FROM comentario where lista_id = " + listasRS.getInt("id");;
                ResultSet comentarioRS = statementComentario.executeQuery(sqlComentarios);
                List<Comentario> comentarios = new ArrayList<>();
                while(comentarioRS.next()){
                    //calcula quantos dias se passaram
                    Date dataComentario = comentarioRS.getDate("data");
                    Date atual = new Date(System.currentTimeMillis());
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar dts = Calendar.getInstance();
                    dts.setTime(atual);
                    Calendar dte = Calendar.getInstance();
                    dte.setTime(dataComentario);
                    dts.add(Calendar.DATE, - dte.get(Calendar.DATE));
                    int dias = dts.get(Calendar.DAY_OF_MONTH);

                    comentarios.add(new Comentario(comentarioRS.getInt("id"), comentarioRS.getString("texto"), dias, comentarioRS.getInt("lista_id")));

                }
                statementComentario.close();
                
                listas.add(new Lista(listasRS.getInt("id"), listasRS.getString("nome"), chaves, filmes, listasRS.getInt("usuario_id"), listasRS.getString("autor"), listasRS.getInt("publica"), avaliacoes, comentarios));
            }
            statementLista.close();
            Conexao.fecharConexao();
            return listas;
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
            return null;
        }
    }
    
    public void adicionarAvaliacao(Avaliacao avaliacao) throws SQLException {
        conectar();
        if(conexao != null) {
            Statement comando = conexao.createStatement();
            
            String sql = "INSERT INTO avaliacao(nota, lista_id, usuario_id) VALUES(" + avaliacao.getNota() +", " + avaliacao.getLista_id() + ", " + avaliacao.getUsuario_id() + ");";
            comando.executeUpdate(sql);
            comando.close();
            Conexao.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro com a conexao ao Banco de Dados.");
        }
    }
    
    public void comentar(Comentario c) throws SQLException {
        conectar();
        if(conexao != null){
            
            Statement comando = conexao.createStatement();
            
            String sql = "INSERT INTO comentario(texto, lista_id) VALUES('" + c.getTexto() + "', " + c.getLista_id() + ");";
            
            comando.executeUpdate(sql);
            comando.close();
            Conexao.fecharConexao();
        }
    }
}
