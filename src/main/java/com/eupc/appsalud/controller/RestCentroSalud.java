package com.eupc.appsalud.controller;

import com.eupc.appsalud.entidades.CentroSalud;
import com.eupc.appsalud.dtos.CentroSaludDTO;
import com.eupc.appsalud.negocio.NegocioCentroSalud;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestCentroSalud {
    @Autowired
    private NegocioCentroSalud negocioCentroSalud;
    @PostMapping("/centroSalud")
    public CentroSaludDTO registrar(@RequestBody CentroSaludDTO centroSaludDTO){
        CentroSalud centroSalud;
        centroSalud = convertToEntity(centroSaludDTO);
        centroSalud = negocioCentroSalud.registrar(centroSalud);
        return convertToDto(centroSalud);

    }
    @GetMapping("/centroSalud")
    public List<CentroSaludDTO> obtenerReporte(){
        return convertToLisDto(negocioCentroSalud.obtenerReporte());
    }
    @GetMapping ("/centroSalud/{xCodigo}")
    public CentroSaludDTO obtenerCentro (@PathVariable(value = "xCodigo") Long xCodigo)
            throws Exception
    {
        CentroSalud centroSalud;
        try {
            centroSalud = negocioCentroSalud.obtenerCentro(xCodigo);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No fue posible encontrar su registro");
        }
        return convertToDto(centroSalud);
    }
    @GetMapping("/centroSaludResultado")
    public List<CentroSaludDTO> obtenerReporteResultados(){
        return negocioCentroSalud.obtenerReporteResultados();
    }

    private CentroSaludDTO convertToDto(CentroSalud centroSalud) {
        ModelMapper modelMapper = new ModelMapper();
        CentroSaludDTO centroSaludDTO = modelMapper.map(centroSalud, CentroSaludDTO.class);
        return centroSaludDTO;
    }

    private CentroSalud convertToEntity(CentroSaludDTO centroSaludDTO) {
        ModelMapper modelMapper = new ModelMapper();
        CentroSalud post = modelMapper.map(centroSaludDTO, CentroSalud.class);
        return post;
    }

    private List<CentroSaludDTO> convertToLisDto(List<CentroSalud> list){
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
