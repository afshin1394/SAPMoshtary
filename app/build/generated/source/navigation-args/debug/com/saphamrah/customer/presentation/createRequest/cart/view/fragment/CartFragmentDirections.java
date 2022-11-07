package com.saphamrah.customer.presentation.createRequest.cart.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class CartFragmentDirections {
  private CartFragmentDirections() {
  }

  @NonNull
  public static ActionCartFragmentToSelectableBonusFragment actionCartFragmentToSelectableBonusFragment(
      @NonNull SelectableBonus[] bonus) {
    return new ActionCartFragmentToSelectableBonusFragment(bonus);
  }

  public static class ActionCartFragmentToSelectableBonusFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCartFragmentToSelectableBonusFragment(@NonNull SelectableBonus[] bonus) {
      if (bonus == null) {
        throw new IllegalArgumentException("Argument \"bonus\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("bonus", bonus);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCartFragmentToSelectableBonusFragment setBonus(@NonNull SelectableBonus[] bonus) {
      if (bonus == null) {
        throw new IllegalArgumentException("Argument \"bonus\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("bonus", bonus);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("bonus")) {
        SelectableBonus[] bonus = (SelectableBonus[]) arguments.get("bonus");
        __result.putParcelableArray("bonus", bonus);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cartFragment_to_selectableBonusFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public SelectableBonus[] getBonus() {
      return (SelectableBonus[]) arguments.get("bonus");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCartFragmentToSelectableBonusFragment that = (ActionCartFragmentToSelectableBonusFragment) object;
      if (arguments.containsKey("bonus") != that.arguments.containsKey("bonus")) {
        return false;
      }
      if (getBonus() != null ? !getBonus().equals(that.getBonus()) : that.getBonus() != null) {
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
      result = 31 * result + java.util.Arrays.hashCode(getBonus());
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCartFragmentToSelectableBonusFragment(actionId=" + getActionId() + "){"
          + "bonus=" + getBonus()
          + "}";
    }
  }
}
