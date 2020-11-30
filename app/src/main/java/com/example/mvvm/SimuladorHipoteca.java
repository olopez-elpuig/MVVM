package com.example.mvvm;

public class SimuladorHipoteca {
    public static class Solicitud {
        public double camion;
        public int añadido;

        public Solicitud(double capital, int plazo) {
            this.camion = camion;
            this.añadido = añadido;
        }
    }
    interface Callback {
        void cuandoEsteCalculadaLaMasa(int masa);
        void cuandoHayaErrorDeMasaInferiorAlMinimo(int camion);
        void cuandoHayaErrorDeMasaAñadidaInferiorAlMinimo(int añadido);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Solicitud solicitud, Callback callback) {
        callback.cuandoEmpieceElCalculo();

        int masaMaximaCamion = 0;
        int masaMaximaAñadida = 0;

        callback.cuandoFinaliceElCalculo();

        try {
            Thread.sleep(100);   // simular operacion de larga duracion (10s)
            masaMaximaCamion = 2000;
            masaMaximaAñadida = 0;
        } catch (InterruptedException e) {}
        boolean error = false;
        if (solicitud.camion < masaMaximaCamion) {
            callback.cuandoHayaErrorDeMasaInferiorAlMinimo(masaMaximaCamion);
            error = true;
        }

        if (solicitud.añadido < masaMaximaAñadida) {
            callback.cuandoHayaErrorDeMasaAñadidaInferiorAlMinimo(masaMaximaAñadida);
            error = true;
        }

        if(!error) {
            callback.cuandoEsteCalculadaLaMasa((int) (solicitud.camion - solicitud.añadido));
        }
    }
}
