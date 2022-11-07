package com.saphamrah.customer.presentation.profile.accountNumber.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class AccountNumberFragmentDirections {
  private AccountNumberFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAccountNumberFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_accountNumberFragment_to_profileFragment);
  }
}
