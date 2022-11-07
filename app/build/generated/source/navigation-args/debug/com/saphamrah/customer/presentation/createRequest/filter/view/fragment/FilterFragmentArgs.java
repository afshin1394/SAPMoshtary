package com.saphamrah.customer.presentation.createRequest.filter.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class FilterFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private FilterFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private FilterFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static FilterFragmentArgs fromBundle(@NonNull Bundle bundle) {
    FilterFragmentArgs __result = new FilterFragmentArgs();
    bundle.setClassLoader(FilterFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("FilterSortType")) {
      int FilterSortType;
      FilterSortType = bundle.getInt("FilterSortType");
      __result.arguments.put("FilterSortType", FilterSortType);
    } else {
      __result.arguments.put("FilterSortType", 1);
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static FilterFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    FilterFragmentArgs __result = new FilterFragmentArgs();
    if (savedStateHandle.contains("FilterSortType")) {
      int FilterSortType;
      FilterSortType = savedStateHandle.get("FilterSortType");
      __result.arguments.put("FilterSortType", FilterSortType);
    } else {
      __result.arguments.put("FilterSortType", 1);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getFilterSortType() {
    return (int) arguments.get("FilterSortType");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("FilterSortType")) {
      int FilterSortType = (int) arguments.get("FilterSortType");
      __result.putInt("FilterSortType", FilterSortType);
    } else {
      __result.putInt("FilterSortType", 1);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("FilterSortType")) {
      int FilterSortType = (int) arguments.get("FilterSortType");
      __result.set("FilterSortType", FilterSortType);
    } else {
      __result.set("FilterSortType", 1);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    FilterFragmentArgs that = (FilterFragmentArgs) object;
    if (arguments.containsKey("FilterSortType") != that.arguments.containsKey("FilterSortType")) {
      return false;
    }
    if (getFilterSortType() != that.getFilterSortType()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getFilterSortType();
    return result;
  }

  @Override
  public String toString() {
    return "FilterFragmentArgs{"
        + "FilterSortType=" + getFilterSortType()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull FilterFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public FilterFragmentArgs build() {
      FilterFragmentArgs result = new FilterFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setFilterSortType(int FilterSortType) {
      this.arguments.put("FilterSortType", FilterSortType);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getFilterSortType() {
      return (int) arguments.get("FilterSortType");
    }
  }
}
