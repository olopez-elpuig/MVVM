package com.example.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiHipotecaViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();
    Executor executor;

    SimuladorHipoteca simulador;

    MutableLiveData<Integer> restante = new MutableLiveData<>();
    MutableLiveData<Integer> errorCapacidadCamion = new MutableLiveData<>();
    MutableLiveData<Integer> errorCapacidadAñadida = new MutableLiveData<>();

    public MiHipotecaViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorHipoteca();
    }

    public void calcular(int camion, int añadido) {


        final SimuladorHipoteca.Solicitud solicitud = new SimuladorHipoteca.Solicitud(camion, añadido);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(solicitud, new SimuladorHipoteca.Callback() {

                    @Override
                    public void cuandoEsteCalculadaLaMasa(int masa) {
                        errorCapacidadCamion.postValue(null);
                        errorCapacidadAñadida.postValue(null);
                        restante.postValue(masa);
                    }

                    @Override
                    public void cuandoHayaErrorDeMasaInferiorAlMinimo(int camion) {
                        errorCapacidadCamion.postValue(camion);
                    }

                    @Override
                    public void cuandoHayaErrorDeMasaAñadidaInferiorAlMinimo(int añadido) {
                        errorCapacidadAñadida.postValue(añadido);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }
                });
            }
        });
    }
}