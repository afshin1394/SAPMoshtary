package com.saphamrah.customer.presentation.createRequest.productRequest.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.System;
import java.util.HashMap;

public class ProductRequestFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ProductRequestFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ProductRequestFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static ProductRequestFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ProductRequestFragmentArgs __result = new ProductRequestFragmentArgs();
    bundle.setClassLoader(ProductRequestFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("filters")) {
      FilterSortModel[] filters;
      Parcelable[] __array = bundle.getParcelableArray("filters");
      if (__array != null) {
        filters = new FilterSortModel[__array.length];
        System.arraycopy(__array, 0, filters, 0, __array.length);
      } else {
        filters = null;
      }
      if (filters == null) {
        throw new IllegalArgumentException("Argument \"filters\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("filters", filters);
    } else {
      throw new IllegalArgumentException("Required argument \"filters\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ProductRequestFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ProductRequestFragmentArgs __result = new ProductRequestFragmentArgs();
    if (savedStateHandle.contains("filters")) {
      FilterSortModel[] filters;
      filters = savedStateHandle.get("filters");
      if (filters == null) {
        throw new IllegalArgumentException("Argument \"filters\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("filters", filters);
    } else {
      throw new IllegalArgumentException("Required argument \"filters\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public FilterSortModel[] getFilters() {
    return (FilterSortModel[]) arguments.get("filters");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("filters")) {
      FilterSortModel[] filters = (FilterSortModel[]) arguments.get("filters");
      __result.putParcelableArray("filters", filters);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("filters")) {
      FilterSortModel[] filters = (FilterSortModel[]) arguments.get("filters");
      __result.set("filters", filters);
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
    ProductRequestFragmentArgs that = (ProductRequestFragmentArgs) object;
    if (arguments.containsKey("filters") != that.arguments.containsKey("filters")) {
      return false;
    }
    if (getFilters() != null ? !getFilters().equals(that.getFilters()) : that.getFilters() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + java.util.Arrays.hashCode(getFilters());
    return result;
  }

  @Override
  public String toString() {
    return "ProductRequestFragmentArgs{"
        + "filters=" + getFilters()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ProductRequestFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull FilterSortModel[] filters) {
      if (filters == null) {
        throw new IllegalArgumentException("Argument \"filters\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("filters", filters);
    }

    @NonNull
    public ProductRequestFragmentArgs build() {
      ProductRequestFragmentArgs result = new ProductRequestFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setFilters(@NonNull FilterSortModel[] filters) {
      if (filters == null) {
        throw new IllegalArgumentException("Argument \"filters\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("filters", filters);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public FilterSortModel[] getFilters() {
      return (FilterSortModel[]) arguments.get("filters");
    }
  }
}
