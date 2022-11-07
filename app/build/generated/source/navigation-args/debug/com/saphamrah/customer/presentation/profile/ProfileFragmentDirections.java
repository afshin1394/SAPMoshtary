package com.saphamrah.customer.presentation.profile;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class ProfileFragmentDirections {
  private ProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionProfileFragmentToMainFragment() {
    return new ActionOnlyNavDirections(R.id.action_profileFragment_to_mainFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToPersonalInfoFragment() {
    return new ActionOnlyNavDirections(R.id.action_profileFragment_to_personalInfoFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToAddressFragment() {
    return new ActionOnlyNavDirections(R.id.action_profileFragment_to_addressFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToAccountNumberFragment() {
    return new ActionOnlyNavDirections(R.id.action_profileFragment_to_accountNumberFragment);
  }
}
