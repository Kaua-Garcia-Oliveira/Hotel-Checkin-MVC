package com.hotel.dao;

import com.hotel.model.Quarto;
import com.hotel.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO {

    public void salvar(Quarto quarto) throws SQLException {
        String sql = "INSERT INTO quartos (numero, tipo, preco_diaria, disponivel, descricao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quarto.getNumero());
            stmt.setString(2, quarto.getTipo());
            stmt.setBigDecimal(3, quarto.getPrecoDiaria());
            stmt.setBoolean(4, quarto.isDisponivel());
            stmt.setString(5, quarto.getDescricao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Quarto quarto) throws SQLException {
        String sql = "UPDATE quartos SET numero=?, tipo=?, preco_diaria=?, disponivel=?, descricao=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quarto.getNumero());
            stmt.setString(2, quarto.getTipo());
            stmt.setBigDecimal(3, quarto.getPrecoDiaria());
            stmt.setBoolean(4, quarto.isDisponivel());
            stmt.setString(5, quarto.getDescricao());
            stmt.setInt(6, quarto.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM quartos WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Quarto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM quartos WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Quarto> listarTodos() throws SQLException {
        List<Quarto> quartos = new ArrayList<>();
        String sql = "SELECT * FROM quartos ORDER BY numero";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) quartos.add(mapear(rs));
        }
        return quartos;
    }

    public List<Quarto> listarDisponiveis() throws SQLException {
        List<Quarto> quartos = new ArrayList<>();
        String sql = "SELECT * FROM quartos WHERE disponivel = TRUE ORDER BY numero";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) quartos.add(mapear(rs));
        }
        return quartos;
    }

    public void atualizarDisponibilidade(int id, boolean disponivel) throws SQLException {
        String sql = "UPDATE quartos SET disponivel=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    private Quarto mapear(ResultSet rs) throws SQLException {
        Quarto q = new Quarto();
        q.setId(rs.getInt("id"));
        q.setNumero(rs.getString("numero"));
        q.setTipo(rs.getString("tipo"));
        q.setPrecoDiaria(rs.getBigDecimal("preco_diaria"));
        q.setDisponivel(rs.getBoolean("disponivel"));
        q.setDescricao(rs.getString("descricao"));
        return q;
    }
}
