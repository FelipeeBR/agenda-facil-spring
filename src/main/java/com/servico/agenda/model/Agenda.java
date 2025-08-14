package com.servico.agenda.model;

import java.util.Date;

import com.servico.agenda.dto.AgendaDTO;
import com.servico.agenda.enums.AppointmentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendas")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "date_time", nullable = false)
    private Date dateTime;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

	@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "job_id")
    private Job job;

    private AppointmentStatus status;

    public Agenda() {}

    public Agenda(Date dateTime, User user, Job job, AppointmentStatus status) {
        this.dateTime = dateTime;
        this.user = user;
        this.job = job;
        this.status = status;
    }

    public Agenda(AgendaDTO agenda) {
        this.id = agenda.getId();
        this.dateTime = agenda.getDateTime();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

}
