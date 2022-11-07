package com.saphamrah.customer.presentation.profile.personalInfo.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class PersonalInfoFragmentDirections {
  private PersonalInfoFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPersonalInfoFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_personalInfoFragment_to_profileFragment);
  }

  @NonNull
  public static NavDirections actionPersonalInfoFragmentToFirstLastNameFragment() {
    return new ActionOnlyNavDirections(R.id.action_personalInfoFragment_to_firstLastNameFragment);
  }
}
