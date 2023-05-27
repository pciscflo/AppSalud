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
       CentroSalud salud = repositorioCentroSalud.findById(codigo).get();
       CentroSaludDTO centroSaludDTO = convertToDto(salud);
       return calcularCalificacion(centroSaludDTO);
    }
    public CentroSalud registrar(CentroSalud centroSalud){
        CentroSalud salud = repositorioCentroSalud.save(centroSalud);
        //llamar a un correo
        return salud;
    }
    public String calcularResultadoFinal(Long codigo){
        CentroSalud salud = repositorioCentroSalud.findById(codigo).get();
        CentroSaludDTO centroSaludDTO = convertToDto(salud);
        return calcularResultadoFinal(centroSaludDTO);
    }
    public List<CentroSalud> obtenerReporte(){
        return repositorioCentroSalud.findAll();
    }
    public List<CentroSaludDTO> obtenerReporteResultados(){
        List<CentroSalud> centros;
        centros = obtenerReporte();
        List<CentroSaludDTO> centroSaludDTOS;
        centroSaludDTOS = convertToLisDto(centros);

        for(CentroSaludDTO p:centroSaludDTOS){
            p.setCalificacionFinal(calcularCalificacion(p));
            centroSaludDTOS.add(p);
        }
        return centroSaludDTOS;
    }
    public CentroSalud obtenerCentro (Long codigo){
        return repositorioCentroSalud.findById(codigo).get();
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
