package com.saphamrah.customer.presentation.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.databinding.FragmentMainBinding;
import com.saphamrah.customer.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.personalInfoLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_personalInfoFragment);
        });

        binding.addressLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_addressFragment);
        });

        binding.hesabLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_accountNumberFragment);
        });
    }
}