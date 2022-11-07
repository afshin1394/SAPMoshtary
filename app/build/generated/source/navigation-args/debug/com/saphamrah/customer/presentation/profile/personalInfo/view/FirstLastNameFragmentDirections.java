package com.saphamrah.customer.presentation.profile.personalInfo.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class FirstLastNameFragmentDirections {
  private FirstLastNameFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionFirstLastNameFragmentToPersonalInfoFragment2() {
    return new ActionOnlyNavDirections(R.id.action_firstLastNameFragment_to_personalInfoFragment2);
  }
}
