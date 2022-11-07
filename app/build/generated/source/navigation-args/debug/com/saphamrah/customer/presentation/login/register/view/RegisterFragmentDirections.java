package com.saphamrah.customer.presentation.login.register.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class RegisterFragmentDirections {
  private RegisterFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionRegisterFragmentToVerifyOtpLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_RegisterFragment_to_VerifyOtpLoginFragment);
  }
}
