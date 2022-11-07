package com.saphamrah.customer.presentation.login.verifyOtp.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class VerifyOtpLoginFragmentDirections {
  private VerifyOtpLoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionVerifyOtpLoginFragmentToSendOtpLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_VerifyOtpLoginFragment_to_SendOtpLoginFragment);
  }
}
