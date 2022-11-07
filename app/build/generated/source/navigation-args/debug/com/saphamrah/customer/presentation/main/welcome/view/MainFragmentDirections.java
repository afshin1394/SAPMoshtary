package com.saphamrah.customer.presentation.main.welcome.view;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;

public class MainFragmentDirections {
  private MainFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionMainFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_mainFragment_to_profileFragment);
  }
}
