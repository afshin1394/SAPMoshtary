package com.saphamrah.customer.presentation.createRequest.selectableBonus.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.System;
import java.util.HashMap;

public class SelectableBonusFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SelectableBonusFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private SelectableBonusFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static SelectableBonusFragmentArgs fromBundle(@NonNull Bundle bundle) {
    SelectableBonusFragmentArgs __result = new SelectableBonusFragmentArgs();
    bundle.setClassLoader(SelectableBonusFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("bonus")) {
      SelectableBonus[] bonus;
      Parcelable[] __array = bundle.getParcelableArray("bonus");
      if (__array != null) {
        bonus = new SelectableBonus[__array.length];
        System.arraycopy(__array, 0, bonus, 0, __array.length);
      } else {
        bonus = null;
      }
      if (bonus == null) {
        throw new IllegalArgumentException("Argument \"bonus\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("bonus", bonus);
    } else {
      throw new IllegalArgumentException("Required argument \"bonus\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SelectableBonusFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    SelectableBonusFragmentArgs __result = new SelectableBonusFragmentArgs();
    if (savedStateHandle.contains("bonus")) {
      SelectableBonus[] bonus;
      bonus = savedStateHandle.get("bonus");
      if (bonus == null) {
        throw new IllegalArgumentException("Argument \"bonus\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("bonus", bonus);
    } else {
      throw new IllegalArgumentException("Required argument \"bonus\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SelectableBonus[] getBonus() {
    return (SelectableBonus[]) arguments.get("bonus");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("bonus")) {
      SelectableBonus[] bonus = (SelectableBonus[]) arguments.get("bonus");
      __result.putParcelableArray("bonus", bonus);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("bonus")) {
      SelectableBonus[] bonus = (SelectableBonus[]) arguments.get("bonus");
      __result.set("bonus", bonus);
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
    SelectableBonusFragmentArgs that = (SelectableBonusFragmentArgs) object;
    if (arguments.containsKey("bonus") != that.arguments.containsKey("bonus")) {
      return false;
    }
    if (getBonus() != null ? !getBonus().equals(that.getBonus()) : that.getBonus() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + java.util.Arrays.hashCode(getBonus());
    return result;
  }

  @Override
  public String toString() {
    return "SelectableBonusFragmentArgs{"
        + "bonus=" + getBonus()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull SelectableBonusFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull SelectableBonus[] bonus) {
      if (bonus == null) {
        throw new IllegalArgumentException("Argument \"bonus\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("bonus", bonus);
    }

    @NonNull
    public SelectableBonusFragmentArgs build() {
      SelectableBonusFragmentArgs result = new SelectableBonusFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBonus(@NonNull SelectableBonus[] bonus) {
      if (bonus == null) {
        throw new IllegalArgumentException("Argument \"bonus\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("bonus", bonus);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public SelectableBonus[] getBonus() {
      return (SelectableBonus[]) arguments.get("bonus");
    }
  }
}
