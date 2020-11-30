package com.example.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvm.MiHipotecaViewModel;
import com.example.mvvm.databinding.FragmentMiHipotecaBinding;

public class MiHipotecaFragment extends Fragment {
    private FragmentMiHipotecaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MiHipotecaViewModel miHipotecaViewModel = new ViewModelProvider(this).get(MiHipotecaViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double masaCamion = Double.parseDouble(binding.capital.getText().toString());
                int masaAñadida = Integer.parseInt(binding.plazo.getText().toString());

                miHipotecaViewModel.calcular((int) masaCamion, masaAñadida);
            }
        });

        miHipotecaViewModel.restante.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer restante) {
                binding.cuota.setText(String.format("%.2f",restante));
            }
        });




        miHipotecaViewModel.errorCapacidadCamion.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer masaCamion) {
                if (masaCamion != null) {
                    binding.capital.setError("La masa no puede ser inferior a " + masaCamion + " Kg");
                } else {
                    binding.capital.setError(null);
                }
            }
        });

        miHipotecaViewModel.errorCapacidadAñadida.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer masaAñadida) {
                if (masaAñadida != null) {
                    binding.plazo.setError("No puedes añadir " + masaAñadida + " Kg");
                } else {
                    binding.plazo.setError(null);
                }
            }
        });
        miHipotecaViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.cuota.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.cuota.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}