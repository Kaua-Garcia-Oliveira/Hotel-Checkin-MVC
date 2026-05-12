package com.hotel.dao;

import com.hotel.model.Hospede;
import com.hotel.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HospedeDAO {

    public void salvar(Hospede hospede) throws SQLException {
        String sql = "INSERT INTO hospedes (nome, cpf, email, telefone, data_nascimento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getCpf());
            stmt.setString(3, hospede.getEmail());
            stmt.setString(4, hospede.getTelefone());
            stmt.setDate(5, hospede.getDataNascimento() != null ? Date.valueOf(hospede.getDataNascimento()) : null);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Hospede hospede) throws SQLException {
        String sql = "UPDATE hospedes SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getCpf());
            stmt.setString(3, hospede.getEmail());
            stmt.setString(4, hospede.getTelefone());
            stmt.setDate(5, hospede.getDataNascimento() != null ? Date.valueOf(hospede.getDataNascimento()) : null);
            stmt.setInt(6, hospede.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM hospedes WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Hospede buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM hospedes WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public List<Hospede> listarTodos() throws SQLException {
        List<Hospede> hospedes = new ArrayList<>();
        String sql = "SELECT * FROM hospedes ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                hospedes.add(mapear(rs));
            }
        }
        return hospedes;
    }

    public List<Hospede> buscarPorNome(String nome) throws SQLException {
        List<Hospede> hospedes = new ArrayList<>();
        String sql = "SELECT * FROM hospedes WHERE nome LIKE ? ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hospedes.add(mapear(rs));
                }
            }
        }
        return hospedes;
    }

    private Hospede mapear(ResultSet rs) throws SQLException {
        Hospede h = new Hospede();
        h.setId(rs.getInt("id"));
        h.setNome(rs.getString("nome"));
        h.setCpf(rs.getString("cpf"));
        h.setEmail(rs.getString("email"));
        h.setTelefone(rs.getString("telefone"));
        Date dataNasc = rs.getDate("data_nascimento");
        if (dataNasc != null) {
            h.setDataNascimento(dataNasc.toLocalDate());
        }
        return h;
    }
}
