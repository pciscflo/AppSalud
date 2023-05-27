package com.eupc.appsalud.entidades;

public class CentroSaludResultado extends CentroSalud{
    private double calificacionFinal;

    public CentroSaludResultado() {
    }

    public CentroSaludResultado(Long codigo, String tipo, int calificacionInfraestructura,
                                int calificacionServicios, boolean ambulancias,
                                double calificacionFinal) {
        super(codigo, tipo, calificacionInfraestructura, calificacionServicios, ambulancias);
        this.calificacionFinal = calificacionFinal;
    }

    public double getCalificacionFinal() {
        return calificacionFinal;
    }

    public void setCalificacionFinal(double calificacionFinal) {
        this.calificacionFinal = calificacionFinal;
    }

    @Override
    public String toString() {
        return "CentroSaludResultado{" +
                "calificacionFinal=" + calificacionFinal +
                "} " + super.toString();
    }
}
