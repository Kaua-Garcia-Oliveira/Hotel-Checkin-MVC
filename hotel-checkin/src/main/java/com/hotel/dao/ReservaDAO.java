package com.hotel.dao;

import com.hotel.model.Hospede;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void salvar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (hospede_id, quarto_id, data_entrada, data_saida, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getHospedeId());
            stmt.setInt(2, reserva.getQuartoId());
            stmt.setDate(3, Date.valueOf(reserva.getDataEntrada()));
            stmt.setDate(4, Date.valueOf(reserva.getDataSaida()));
            stmt.setString(5, reserva.getStatus() != null ? reserva.getStatus() : "PENDENTE");
            stmt.executeUpdate();
        }
    }

    public void atualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET hospede_id=?, quarto_id=?, data_entrada=?, data_saida=?, status=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getHospedeId());
            stmt.setInt(2, reserva.getQuartoId());
            stmt.setDate(3, Date.valueOf(reserva.getDataEntrada()));
            stmt.setDate(4, Date.valueOf(reserva.getDataSaida()));
            stmt.setString(5, reserva.getStatus());
            stmt.setInt(6, reserva.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizarStatus(int id, String status) throws SQLException {
        String sql = "UPDATE reservas SET status=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public Reserva buscarPorId(int id) throws SQLException {
        String sql = "SELECT r.*, h.nome as hospede_nome, h.cpf, h.email, h.telefone, " +
                     "q.numero, q.tipo, q.preco_diaria, q.disponivel, q.descricao " +
                     "FROM reservas r " +
                     "JOIN hospedes h ON r.hospede_id = h.id " +
                     "JOIN quartos q ON r.quarto_id = q.id " +
                     "WHERE r.id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearComJoin(rs);
            }
        }
        return null;
    }

    public List<Reserva> listarTodas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, h.nome as hospede_nome, h.cpf, h.email, h.telefone, " +
                     "q.numero, q.tipo, q.preco_diaria, q.disponivel, q.descricao " +
                     "FROM reservas r " +
                     "JOIN hospedes h ON r.hospede_id = h.id " +
                     "JOIN quartos q ON r.quarto_id = q.id " +
                     "ORDER BY r.data_entrada DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) reservas.add(mapearComJoin(rs));
        }
        return reservas;
    }

    public List<Reserva> listarConfirmadas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, h.nome as hospede_nome, h.cpf, h.email, h.telefone, " +
                     "q.numero, q.tipo, q.preco_diaria, q.disponivel, q.descricao " +
                     "FROM reservas r " +
                     "JOIN hospedes h ON r.hospede_id = h.id " +
                     "JOIN quartos q ON r.quarto_id = q.id " +
                     "WHERE r.status = 'CONFIRMADA' " +
                     "ORDER BY r.data_entrada";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) reservas.add(mapearComJoin(rs));
        }
        return reservas;
    }

    private Reserva mapearComJoin(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("id"));
        r.setHospedeId(rs.getInt("hospede_id"));
        r.setQuartoId(rs.getInt("quarto_id"));
        r.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
        r.setDataSaida(rs.getDate("data_saida").toLocalDate());
        r.setStatus(rs.getString("status"));

        Hospede h = new Hospede();
        h.setId(rs.getInt("hospede_id"));
        h.setNome(rs.getString("hospede_nome"));
        h.setCpf(rs.getString("cpf"));
        h.setEmail(rs.getString("email"));
        h.setTelefone(rs.getString("telefone"));
        r.setHospede(h);

        Quarto q = new Quarto();
        q.setId(rs.getInt("quarto_id"));
        q.setNumero(rs.getString("numero"));
        q.setTipo(rs.getString("tipo"));
        q.setPrecoDiaria(rs.getBigDecimal("preco_diaria"));
        q.setDisponivel(rs.getBoolean("disponivel"));
        q.setDescricao(rs.getString("descricao"));
        r.setQuarto(q);

        return r;
    }
}
