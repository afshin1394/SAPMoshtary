package com.saphamrah.customer.presentation.profile.address.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class AddressFragmentDirections {
  private AddressFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddressFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_addressFragment_to_profileFragment);
  }
}
