package com.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private int id;
    private int hospedeId;
    private int quartoId;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String status; // PENDENTE, CONFIRMADA, CANCELADA
    private LocalDateTime createdAt;

    // Objetos relacionados (para JOINs)
    private Hospede hospede;
    private Quarto quarto;

    public Reserva() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHospedeId() { return hospedeId; }
    public void setHospedeId(int hospedeId) { this.hospedeId = hospedeId; }

    public int getQuartoId() { return quartoId; }
    public void setQuartoId(int quartoId) { this.quartoId = quartoId; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Hospede getHospede() { return hospede; }
    public void setHospede(Hospede hospede) { this.hospede = hospede; }

    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }

    public long getDiarias() {
        if (dataEntrada != null && dataSaida != null) {
            return ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        }
        return 0;
    }
}
