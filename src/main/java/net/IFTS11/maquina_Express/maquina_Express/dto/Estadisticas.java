package net.IFTS11.maquina_Express.maquina_Express.dto;

import java.util.Date;
import java.util.Map;

public class Estadisticas {

    private float total_acumulado;

    private long cantida_acumulada;

    public Estadisticas() {
    }

    public Estadisticas(float total_acumulado, long cantida_acumulada) {
        this.total_acumulado = total_acumulado;
        this.cantida_acumulada = cantida_acumulada;
    }

    public float getTotal_acumulado() {
        return total_acumulado;
    }

    public void setTotal_acumulado(float total_acumulado) {
        this.total_acumulado = total_acumulado;
    }

    public long getCantida_acumulada() {
        return cantida_acumulada;
    }

    public void setCantida_acumulada(long cantida_acumulada) {
        this.cantida_acumulada = cantida_acumulada;
    }
    public void actualizarTotal_acumulado(float total_acumulado) {
        this.total_acumulado += total_acumulado;
    }

    public void actualizarCantida_acumulada(long cantida_acumulada) {
        this.cantida_acumulada += cantida_acumulada;
    }
}
