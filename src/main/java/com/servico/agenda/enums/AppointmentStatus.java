package com.servico.agenda.enums;

public enum AppointmentStatus {
    PENDING("Pendente"),
    CONFIRMED("Confirmado"),
    CANCELLED("Cancelado"),
    AVALIABLE("Disponivel");
    
    private final String description;
    
    AppointmentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
