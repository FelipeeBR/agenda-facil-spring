package com.servico.agenda.dto;

import java.util.Date;

import com.servico.agenda.enums.AppointmentStatus;
import com.servico.agenda.model.Agenda;

public class AgendaDTO {
    private Long id;
    private Date dateTime;
    private Long userId;
    private Long jobId;

    private AppointmentStatus status;

    public AgendaDTO() {}

    public AgendaDTO(Long id, Date dateTime, Long userId, Long jobId, AppointmentStatus status) {
        this.id = id;
        this.dateTime = dateTime;
        this.userId = userId;
        this.jobId = jobId;
        this.status = status;
    }

    public AgendaDTO(Agenda agenda) {
        this.id = agenda.getId();
        this.dateTime = agenda.getDateTime();
        this.userId = agenda.getUser().getId();
        this.jobId = agenda.getJob().getId();
        this.status = agenda.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
