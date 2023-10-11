package com.Grupo6.Control2.Services;

import com.Grupo6.Control2.models.Tarea;
import com.Grupo6.Control2.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    public TareaService(TareaRepository tareaRepository){
        this.tareaRepository = tareaRepository;
    }

    public Tarea crearTarea(Tarea tarea){
        tareaRepository.crearTarea(tarea);
        return tarea;
    }
    public void borrarTarea(Long id_tarea){
        tareaRepository.borrarTarea(id_tarea);
    }
    public void actualizarTarea(Tarea tarea){
        tareaRepository.actualizarTarea(tarea);
    }
    public ArrayList<Tarea> obtenerTareas(){
        return tareaRepository.todasLasTareas();
    }
    public Tarea obtenerTarea(Long id_tarea){
        return tareaRepository.obtenerTareaPorId(id_tarea);
    }
    public ArrayList<Tarea> obtenerTareasPorUsuario(String nombre_usuario){
        Long id_usuario = usuarioService.obtenerIdUsuarioPorNombre(nombre_usuario);
        System.out.println(id_usuario);
        return (ArrayList<Tarea>) tareaRepository.obtenerTareasPorUsuario(id_usuario);
    }

    public Tarea crearTareaSinID(Tarea tarea){
        tareaRepository.crearTareaSinID(tarea);
        return tarea;
    }

    public void cambiarStatus(Long id){
        tareaRepository.actualizarEstatus(id,false);
    }

    public ArrayList<Tarea> obtenerTareasAtrasadas(String nombre_usuario){
        ArrayList<Tarea> todasLasTareas = obtenerTareasPorUsuario(nombre_usuario);
        LocalDate fechaActual = LocalDate.now();
        ArrayList<Tarea> todasLasTareasPorFinalizar = new ArrayList<>();

        for (Tarea tarea : todasLasTareas) {
            LocalDate fechaTarea = tarea.getVencimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(ChronoUnit.DAYS.between(fechaActual, fechaTarea) == 1){
                todasLasTareasPorFinalizar.add(tarea);
            }
        }
        return todasLasTareasPorFinalizar;
    }
}
