package com.saphamrah.customer.presentation.profile.address.view;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.AddressMoshtaryModel;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;
import com.saphamrah.customer.presentation.profile.address.view.adapter.CustomerAddressAdapter;

import java.util.ArrayList;


public class AddressFragment extends BaseFragment<ProfilePresenter, FragmentAddressBinding> implements ProfileInteracts.RequiredViewOps {

    public AddressFragment() {
        super(R.layout.fragment_address);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {
        ArrayList<AddressMoshtaryModel> addressModels = new ArrayList<>();


        addressModels.add(new AddressMoshtaryModel("تهران کوچه باغی نبش فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران کوچه باغی نبش فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران خیابان شهید فهمیده کوچه فهمیده تر رو به روی فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران کوچه باغی نبش فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران کوچه باغی نبش فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران خیابان شهید فهمیده کوچه فهمیده تر رو به روی فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران کوچه باغی نبش فهمیده", "درخواست و تحویل","09125557896", "6571688996"));
        addressModels.add(new AddressMoshtaryModel("تهران خیابان شهید فهمیده کوچه فهمیده تر رو به روی فهمیده", "درخواست و تحویل","09125557896", "6571688996"));

        CustomerAddressAdapter adapter = new CustomerAddressAdapter(requireContext(),addressModels);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        viewBinding.recyclerView.setLayoutManager(mLayoutManager);
        viewBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentAddressBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAddressBinding.inflate(inflater, container, false);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return requireContext();
    }
}