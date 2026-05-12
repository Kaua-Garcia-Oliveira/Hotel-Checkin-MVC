package com.hotel.dao;

import com.hotel.model.CheckIn;
import com.hotel.model.Hospede;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInDAO {

    public void salvar(CheckIn checkIn) throws SQLException {
        String sql = "INSERT INTO checkins (reserva_id, data_checkin, status, observacoes) VALUES (?, NOW(), ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, checkIn.getReservaId());
            stmt.setString(2, "ATIVO");
            stmt.setString(3, checkIn.getObservacoes());
            stmt.executeUpdate();
        }
    }

    public void realizarCheckout(int id) throws SQLException {
        String sql = "UPDATE checkins SET data_checkout=NOW(), status='FINALIZADO' WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM checkins WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public CheckIn buscarPorId(int id) throws SQLException {
        String sql = buildSelectQuery() + " WHERE c.id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<CheckIn> listarTodos() throws SQLException {
        List<CheckIn> checkins = new ArrayList<>();
        String sql = buildSelectQuery() + " ORDER BY c.data_checkin DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) checkins.add(mapear(rs));
        }
        return checkins;
    }

    public List<CheckIn> listarAtivos() throws SQLException {
        List<CheckIn> checkins = new ArrayList<>();
        String sql = buildSelectQuery() + " WHERE c.status='ATIVO' ORDER BY c.data_checkin DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) checkins.add(mapear(rs));
        }
        return checkins;
    }

    private String buildSelectQuery() {
        return "SELECT c.*, r.hospede_id, r.quarto_id, r.data_entrada, r.data_saida, r.status as reserva_status, " +
               "h.nome as hospede_nome, h.cpf, h.email, h.telefone, " +
               "q.numero, q.tipo, q.preco_diaria " +
               "FROM checkins c " +
               "JOIN reservas r ON c.reserva_id = r.id " +
               "JOIN hospedes h ON r.hospede_id = h.id " +
               "JOIN quartos q ON r.quarto_id = q.id";
    }

    private CheckIn mapear(ResultSet rs) throws SQLException {
        CheckIn c = new CheckIn();
        c.setId(rs.getInt("id"));
        c.setReservaId(rs.getInt("reserva_id"));
        c.setStatus(rs.getString("status"));
        c.setObservacoes(rs.getString("observacoes"));
        Timestamp tsIn = rs.getTimestamp("data_checkin");
        if (tsIn != null) c.setDataCheckin(tsIn.toLocalDateTime());
        Timestamp tsOut = rs.getTimestamp("data_checkout");
        if (tsOut != null) c.setDataCheckout(tsOut.toLocalDateTime());

        Reserva reserva = new Reserva();
        reserva.setId(rs.getInt("reserva_id"));
        reserva.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
        reserva.setDataSaida(rs.getDate("data_saida").toLocalDate());
        reserva.setStatus(rs.getString("reserva_status"));

        Hospede h = new Hospede();
        h.setId(rs.getInt("hospede_id"));
        h.setNome(rs.getString("hospede_nome"));
        h.setCpf(rs.getString("cpf"));
        h.setEmail(rs.getString("email"));
        h.setTelefone(rs.getString("telefone"));
        reserva.setHospede(h);

        Quarto q = new Quarto();
        q.setId(rs.getInt("quarto_id"));
        q.setNumero(rs.getString("numero"));
        q.setTipo(rs.getString("tipo"));
        q.setPrecoDiaria(rs.getBigDecimal("preco_diaria"));
        reserva.setQuarto(q);

        c.setReserva(reserva);
        return c;
    }
}
