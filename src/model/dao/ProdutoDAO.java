/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Produto;

/**
 *
 * @author horaciocome1
 */
public class ProdutoDAO {
    
    //definir
    public void create(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO produto (descricao,qtd,preco)VALUES(?,?,?)");
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        } finally { // o programa sempre vai passar daquicomo se esta parte estivesse no catch e no try
            ConnectionFactory.closeConnection(con, stmt);
        } 
    }
    
    //buscar
    public List<Produto> read(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQtd(rs.getInt("qtd"));
                produto.setPreco(rs.getDouble("preco"));
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null , "Erro ao buscar: "+ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return produtos;
    }
    
    //actualizar
    public void update(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM produto WHERE id = ?");
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: "+ex);
        } finally { // o programa sempre vai passar daquicomo se esta parte estivesse no catch e no try
            ConnectionFactory.closeConnection(con, stmt);
        } 
    }
    
    //apagar
    public void delete(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE produto SET descricao = ?,qtd = ?,preco = ? WHERE id = ?");
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualizado com sucesso.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao actualizar: "+ex);
        } finally { // o programa sempre vai passar daquicomo se esta parte estivesse no catch e no try
            ConnectionFactory.closeConnection(con, stmt);
        } 
    }
    
    // buscar pela descricao
    public List<Produto> readForDesc(String desc){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM produto WHERE descricao LIKE ?");
            stmt.setString(1, "%"+desc+"%");
                //LIKE tudo que tenha em alguma parte o conteudo pesquisado
                /*
                o percentual no inico pega os que tem desc no fim
                o contrario e o contrario
                */
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQtd(rs.getInt("qtd"));
                produto.setPreco(rs.getDouble("preco"));
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null , "Erro ao buscar: "+ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return produtos;
    }
    
}
