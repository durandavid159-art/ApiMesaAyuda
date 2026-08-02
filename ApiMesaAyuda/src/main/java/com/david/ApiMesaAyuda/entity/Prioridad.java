package com.david.ApiMesaAyuda.entity;

public enum Prioridad {
    ALTA(4),
    MEDIA(24),
    BAJA(72);

    private final int horasSla;

    Prioridad (int horasSla){
        this.horasSla = horasSla;
    }

    public int getHorasSla(){
        return horasSla;
    }
}
