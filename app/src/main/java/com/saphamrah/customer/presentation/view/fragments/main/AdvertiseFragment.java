package com.saphamrah.customer.presentation.view.fragments.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.databinding.FragmentAdvertiseBinding;

import java.util.List;


public class AdvertiseFragment extends Fragment {
    private FragmentAdvertiseBinding binding;
    private List<AdvertiseModel> advertiseList;


    public AdvertiseFragment() {
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
        binding = FragmentAdvertiseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setAdsItems();
        return view;
    }

    private void setAdsItems() {
        assert getArguments() != null;
        AdvertiseModel model = (AdvertiseModel) getArguments().getSerializable("data");

        String uri = model.getImageUrl();

        int imageResource = getResources().getIdentifier(uri, null, getContext().getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        binding.adsImageview.setImageDrawable(res);
    }
}