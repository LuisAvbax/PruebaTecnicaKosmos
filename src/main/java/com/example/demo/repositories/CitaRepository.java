package com.example.demo.repositories;

import com.example.demo.entities.Cita;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByDoctor(Integer idDoctor);
    List<Cita> findByConsultorio(Integer idConsultorio);
    List<Cita> findByHorarioConsulta(Date fecha);

    @Query("SELECT c FROM Cita c WHERE c.horarioConsulta BETWEEN :inicio AND :fin OR DATEADD(HOUR, 1, c.horarioConsulta) BETWEEN :inicio AND :fin")
    List<Cita> findCitasQuePodrianTraslaparse(@Param("inicio") Date inicio, @Param("fin") Date fin);
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.doctor.nombre = :nombreDoctor AND c.horarioConsulta BETWEEN :inicioDia AND :finDia")
    long countCitasPorDoctorEnDia(@Param("nombreDoctor") String nombreDoctor, @Param("inicioDia") Date inicioDia, @Param("finDia") Date finDia);
    List<Cita> findByNombrePacienteAndHorarioConsulta(String nombrePaciente, Date fecha );
    List<Cita> findByDoctorAndHorarioConsulta(Integer idDoctor, Date fecha);
    List<Cita> findByConsultorioAndHorarioConsulta(Integer idConsultorio, Date fecha);
    List<Cita> findByConsultorioAndDoctorAndHorarioConsulta(Date fecha, Integer idConsultorio, Integer idDoctor);
}
