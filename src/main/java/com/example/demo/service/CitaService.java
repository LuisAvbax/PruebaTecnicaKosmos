package com.example.demo.service;

import com.example.demo.entities.Cita;
import com.example.demo.entities.Doctor;
import com.example.demo.repositories.CitaRepository;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    CitaRepository citaRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ConsultorioRepository consultorioRepository;
    public List<Cita> findByDoctor(Integer idDoctor) {
        return citaRepository.findByDoctor(idDoctor);
    }
    public List<Cita> findByConsultorio(Integer idConsultorio) {
        return citaRepository.findByConsultorio(idConsultorio);
    }
    public List<Cita> findByHorarioConsulta(Date fecha) {
        return citaRepository.findByHorarioConsulta(fecha);
    }
    public List<Cita> findByConsultorioAndDoctorAndHorarioConsulta(Date fecha, Integer idConsultorio, Integer idDoctor) {
        return citaRepository.findByConsultorioAndDoctorAndHorarioConsulta(fecha,idConsultorio,idDoctor);
    }
    public void inicializarDoctoresYConsultorios(){
    }
    public Cita crearCita (Cita cita){
    return cita;
    }
    private boolean esConsultorioDisponible(Cita cita) {
        List<Cita> citasMismoHorario = citaRepository.findByConsultorioAndHorarioConsulta(cita.getConsultorio().getIdConsultorio(), cita.getHorarioConsulta());
        return citasMismoHorario.isEmpty();
    }
    private boolean esDoctorDisponible(Cita cita) {
        List<Cita> citasMismoHorario = citaRepository.findByDoctorAndHorarioConsulta(cita.getDoctor().getIdDoctor(), cita.getHorarioConsulta());
        return citasMismoHorario.isEmpty();
    }
    public boolean existeTraslapeDeCitas(Date horarioConsulta) {
        Date unaHoraAntes = new Date(horarioConsulta.getTime() - 3600 * 1000); // Resta una hora
        Date unaHoraDespues = new Date(horarioConsulta.getTime() + 3600 * 1000); // Suma una hora
        List<Cita> citasTraslapadas = citaRepository.findCitasQuePodrianTraslaparse(unaHoraAntes, unaHoraDespues);
        return !citasTraslapadas.isEmpty();
    }
    public boolean puedeDoctorTomarCita(String nombreDoctor, Date horarioConsulta) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(horarioConsulta);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date inicioDia = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date finDia = calendar.getTime();
        long countCitas = citaRepository.countCitasPorDoctorEnDia(nombreDoctor, inicioDia, finDia);
        return countCitas < 8;
    }
    private boolean esCitaValida(Cita cita) {
        return esConsultorioDisponible(cita) && esDoctorDisponible(cita)
                && existeTraslapeDeCitas(cita.getHorarioConsulta()) && puedeDoctorTomarCita(cita.getDoctor().getNombre(), cita.getHorarioConsulta());
    }
}
