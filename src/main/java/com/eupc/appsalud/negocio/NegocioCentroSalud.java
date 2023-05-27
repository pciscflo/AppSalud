package com.eupc.appsalud.negocio;

import com.eupc.appsalud.entidades.CentroSalud;
import com.eupc.appsalud.entidades.CentroSaludResultado;
import com.eupc.appsalud.repositorio.RepositorioCentroSalud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class NegocioCentroSalud {
    @Autowired
    private RepositorioCentroSalud repositorioCentroSalud;

    public double calcularCalificacion(CentroSalud centroSalud) {
        return (centroSalud.getCalificacionInfraestructura()*0.35 +
                centroSalud.getCalificacionServicios()*0.65);
    }

    public String calcularResultadoFinal(CentroSalud centroSalud) {
        if (calcularCalificacion(centroSalud) >= 80) {
            return "Aprobó";
        } else {
            return "Desaprobó";
        }
    }
    public double calcularCalificacion(Long codigo){
       CentroSalud salud = repositorioCentroSalud.findById(codigo).get();
       return calcularCalificacion(salud);
    }
    public CentroSalud registrar(CentroSalud centroSalud){
        CentroSalud salud = repositorioCentroSalud.save(centroSalud);
        //llamar a un correo
        return salud;
    }
    public String calcularResultadoFinal(Long codigo){
        CentroSalud salud = repositorioCentroSalud.findById(codigo).get();
        return calcularResultadoFinal(salud);
    }
    public List<CentroSalud> obtenerReporte(){
        return repositorioCentroSalud.findAll();
    }
    public List<CentroSaludResultado> obtenerReporteResultados(){
        List<CentroSalud> centros;
        centros = obtenerReporte();
        List<CentroSaludResultado> centroSaludResultados= new ArrayList<>();

        for(CentroSalud p:centros){
            CentroSaludResultado resultado = new CentroSaludResultado();
            resultado.setCodigo(p.getCodigo());
            resultado.setCalificacionInfraestructura(p.getCalificacionInfraestructura());
            resultado.setCalificacionServicios(p.getCalificacionServicios());
            resultado.setAmbulancias(p.isAmbulancias());
            resultado.setTipo(p.getTipo());
            resultado.setCalificacionFinal(calcularCalificacion(p));
            centroSaludResultados.add(resultado);
        }
        return centroSaludResultados;
    }
    public CentroSalud obtenerCentro (Long codigo){
        return repositorioCentroSalud.findById(codigo).get();
    }
}
