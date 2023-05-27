package com.eupc.appsalud.rest;

import com.eupc.appsalud.entidades.CentroSalud;
import com.eupc.appsalud.entidades.CentroSaludResultado;
import com.eupc.appsalud.negocio.NegocioCentroSalud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCentroSalud {
    @Autowired
    private NegocioCentroSalud negocioCentroSalud;
    @PostMapping("/centroSalud")
    public CentroSalud registrar(@RequestBody CentroSalud centroSalud){
        return negocioCentroSalud.registrar(centroSalud);

    }
    @GetMapping("/centroSalud")
    public List<CentroSalud> obtenerReporte(){
        return negocioCentroSalud.obtenerReporte();
    }
    @GetMapping ("/centroSalud/{xCodigo}")
    public CentroSalud obtenerCentro (@PathVariable(value = "xCodigo") Long xCodigo)
            throws Exception
    {
        try {
            return negocioCentroSalud.obtenerCentro(xCodigo);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No fue posible encontrar su registro");
        }
    }
    @GetMapping("/centroSaludResultado")
    public List<CentroSaludResultado> obtenerReporteResultados(){
        return negocioCentroSalud.obtenerReporteResultados();
    }
}
