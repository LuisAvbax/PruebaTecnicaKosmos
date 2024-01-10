package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "Cita")
public class Cita {
    @Id
    @Column(name = "idCita", nullable = false)
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "idConsultorio", nullable = false)
    private Consultorio consultorio;

    @ManyToOne
    @JoinColumn(name = "idDoctor", nullable = false)
    private Doctor doctor;

    @Column(name = "horarioConsulta", nullable = false)
    private Date horarioConsulta;

    @ManyToOne
    @JoinColumn(name = "nombrePaciente", nullable = false)
    private Doctor nombrePaciente;

}