package com.saphamrah.customer.presentation.login.sendOtp.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class SendOtpLoginFragmentDirections {
  private SendOtpLoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSendOtpLoginFragmentToVerifyOtpLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_SendOtpLoginFragment_to_VerifyOtpLoginFragment);
  }

  @NonNull
  public static NavDirections actionSendOtpLoginFragmentToRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_SendOtpLoginFragment_to_RegisterFragment);
  }
}
