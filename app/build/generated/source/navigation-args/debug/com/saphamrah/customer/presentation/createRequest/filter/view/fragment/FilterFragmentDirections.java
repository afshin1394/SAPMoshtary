package com.saphamrah.customer.presentation.createRequest.filter.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class FilterFragmentDirections {
  private FilterFragmentDirections() {
  }

  @NonNull
  public static ActionFilterFragmentToProductRequestFragment actionFilterFragmentToProductRequestFragment(
      @NonNull FilterSortModel[] filters) {
    return new ActionFilterFragmentToProductRequestFragment(filters);
  }

  public static class ActionFilterFragmentToProductRequestFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionFilterFragmentToProductRequestFragment(@NonNull FilterSortModel[] filters) {
      if (filters == null) {
        throw new IllegalArgumentException("Argument \"filters\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("filters", filters);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionFilterFragmentToProductRequestFragment setFilters(
        @NonNull FilterSortModel[] filters) {
      if (filters == null) {
        throw new IllegalArgumentException("Argument \"filters\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("filters", filters);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("filters")) {
        FilterSortModel[] filters = (FilterSortModel[]) arguments.get("filters");
        __result.putParcelableArray("filters", filters);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_filterFragment_to_productRequestFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public FilterSortModel[] getFilters() {
      return (FilterSortModel[]) arguments.get("filters");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionFilterFragmentToProductRequestFragment that = (ActionFilterFragmentToProductRequestFragment) object;
      if (arguments.containsKey("filters") != that.arguments.containsKey("filters")) {
        return false;
      }
      if (getFilters() != null ? !getFilters().equals(that.getFilters()) : that.getFilters() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + java.util.Arrays.hashCode(getFilters());
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionFilterFragmentToProductRequestFragment(actionId=" + getActionId() + "){"
          + "filters=" + getFilters()
          + "}";
    }
  }
}
