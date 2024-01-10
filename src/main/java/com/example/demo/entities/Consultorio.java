package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "Consultorios")
public class Consultorio {
    @Id
    @Column(name = "idConsultorio", nullable = false)
    private Integer idConsultorio;

    @Column(name = "numeroConsultorio", length = 50)
    private Integer numeroConsultorio;

    @Column(name = "piso")
    private Integer piso;
}