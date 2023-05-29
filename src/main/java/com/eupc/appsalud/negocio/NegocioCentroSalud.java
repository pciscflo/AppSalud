package com.eupc.appsalud.negocio;

import com.eupc.appsalud.entidades.CentroSalud;
import com.eupc.appsalud.dtos.CentroSaludDTO;
import com.eupc.appsalud.repositorio.RepositorioCentroSalud;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NegocioCentroSalud {
    @Autowired
    private RepositorioCentroSalud repositorioCentroSalud;

    public double calcularCalificacion(CentroSaludDTO centroSaludDTO) {
        return (centroSaludDTO.getCalificacionInfraestructura()*0.35 +
                centroSaludDTO.getCalificacionServicios()*0.65);
    }

    public String calcularResultadoFinal(CentroSaludDTO centroSaludDTO) {
        if (calcularCalificacion(centroSaludDTO) >= 80) {
            return "Aprobó";
        } else {
            return "Desaprobó";
        }
    }
    public double calcularCalificacion(Long codigo){
       CentroSalud centroSalud = repositorioCentroSalud.findById(codigo).get();
       CentroSaludDTO centroSaludDTO = convertToDto(centroSalud);
       return calcularCalificacion(centroSaludDTO);
    }
    public CentroSalud registrar(CentroSalud centroSalud){
        CentroSalud salud = repositorioCentroSalud.save(centroSalud);
        return salud;
    }
    public String calcularResultadoFinal(Long codigo){
        CentroSalud centroSalud = repositorioCentroSalud.findById(codigo).get();
        CentroSaludDTO centroSaludDTO = convertToDto(centroSalud);
        return calcularResultadoFinal(centroSaludDTO);
    }
    public List<CentroSalud> obtenerReporte(){
        return repositorioCentroSalud.findAll();
    }

    public List<CentroSalud> obtenerReporte(String tipo){
        return repositorioCentroSalud.findCentroSaludsByTipo(tipo);
    }
    public List<CentroSaludDTO> obtenerReporteResultados(){
        List<CentroSalud> centros;
        centros = obtenerReporte();
        List<CentroSaludDTO> centroSaludDTOS;
        centroSaludDTOS = convertToLisDto(centros);

        for(CentroSaludDTO p:centroSaludDTOS){
            p.setCalificacionFinal(calcularCalificacion(p));
        }
        return centroSaludDTOS;
    }
    public String obtenerCentroEvaluado (Long codigo){
        CentroSalud centroSalud = repositorioCentroSalud.findById(codigo).get();
        CentroSaludDTO centroSaludDTO = convertToDto(centroSalud);
        return calcularResultadoFinal(centroSaludDTO);
    }

    public CentroSalud actualizar(Long codigo, CentroSalud centroSalud){
        CentroSalud centroAntiguo = repositorioCentroSalud.findById(codigo).get();
        return repositorioCentroSalud.save(centroSalud); //actualizando con el enviado
    }
    private CentroSaludDTO convertToDto(CentroSalud centroSalud) {
        ModelMapper modelMapper = new ModelMapper();
        CentroSaludDTO centroSaludDTO = modelMapper.map(centroSalud, CentroSaludDTO.class);
        return centroSaludDTO;
    }

    private List<CentroSaludDTO> convertToLisDto(List<CentroSalud> list){
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
