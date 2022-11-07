package com.saphamrah.customer.presentation.createRequest.cart.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.System;
import java.util.HashMap;

public class CartFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private CartFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private CartFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static CartFragmentArgs fromBundle(@NonNull Bundle bundle) {
    CartFragmentArgs __result = new CartFragmentArgs();
    bundle.setClassLoader(CartFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("products")) {
      ProductModel[] products;
      Parcelable[] __array = bundle.getParcelableArray("products");
      if (__array != null) {
        products = new ProductModel[__array.length];
        System.arraycopy(__array, 0, products, 0, __array.length);
      } else {
        products = null;
      }
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("products", products);
    } else {
      throw new IllegalArgumentException("Required argument \"products\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("selectedBonuses")) {
      SelectableBonus[] selectedBonuses;
      Parcelable[] __array = bundle.getParcelableArray("selectedBonuses");
      if (__array != null) {
        selectedBonuses = new SelectableBonus[__array.length];
        System.arraycopy(__array, 0, selectedBonuses, 0, __array.length);
      } else {
        selectedBonuses = null;
      }
      __result.arguments.put("selectedBonuses", selectedBonuses);
    } else {
      throw new IllegalArgumentException("Required argument \"selectedBonuses\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static CartFragmentArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    CartFragmentArgs __result = new CartFragmentArgs();
    if (savedStateHandle.contains("products")) {
      ProductModel[] products;
      products = savedStateHandle.get("products");
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("products", products);
    } else {
      throw new IllegalArgumentException("Required argument \"products\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("selectedBonuses")) {
      SelectableBonus[] selectedBonuses;
      selectedBonuses = savedStateHandle.get("selectedBonuses");
      __result.arguments.put("selectedBonuses", selectedBonuses);
    } else {
      throw new IllegalArgumentException("Required argument \"selectedBonuses\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ProductModel[] getProducts() {
    return (ProductModel[]) arguments.get("products");
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public SelectableBonus[] getSelectedBonuses() {
    return (SelectableBonus[]) arguments.get("selectedBonuses");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("products")) {
      ProductModel[] products = (ProductModel[]) arguments.get("products");
      __result.putParcelableArray("products", products);
    }
    if (arguments.containsKey("selectedBonuses")) {
      SelectableBonus[] selectedBonuses = (SelectableBonus[]) arguments.get("selectedBonuses");
      __result.putParcelableArray("selectedBonuses", selectedBonuses);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("products")) {
      ProductModel[] products = (ProductModel[]) arguments.get("products");
      __result.set("products", products);
    }
    if (arguments.containsKey("selectedBonuses")) {
      SelectableBonus[] selectedBonuses = (SelectableBonus[]) arguments.get("selectedBonuses");
      __result.set("selectedBonuses", selectedBonuses);
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
    CartFragmentArgs that = (CartFragmentArgs) object;
    if (arguments.containsKey("products") != that.arguments.containsKey("products")) {
      return false;
    }
    if (getProducts() != null ? !getProducts().equals(that.getProducts()) : that.getProducts() != null) {
      return false;
    }
    if (arguments.containsKey("selectedBonuses") != that.arguments.containsKey("selectedBonuses")) {
      return false;
    }
    if (getSelectedBonuses() != null ? !getSelectedBonuses().equals(that.getSelectedBonuses()) : that.getSelectedBonuses() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + java.util.Arrays.hashCode(getProducts());
    result = 31 * result + java.util.Arrays.hashCode(getSelectedBonuses());
    return result;
  }

  @Override
  public String toString() {
    return "CartFragmentArgs{"
        + "products=" + getProducts()
        + ", selectedBonuses=" + getSelectedBonuses()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull CartFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ProductModel[] products, @Nullable SelectableBonus[] selectedBonuses) {
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("products", products);
      this.arguments.put("selectedBonuses", selectedBonuses);
    }

    @NonNull
    public CartFragmentArgs build() {
      CartFragmentArgs result = new CartFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setProducts(@NonNull ProductModel[] products) {
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("products", products);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setSelectedBonuses(@Nullable SelectableBonus[] selectedBonuses) {
      this.arguments.put("selectedBonuses", selectedBonuses);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public ProductModel[] getProducts() {
      return (ProductModel[]) arguments.get("products");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public SelectableBonus[] getSelectedBonuses() {
      return (SelectableBonus[]) arguments.get("selectedBonuses");
    }
  }
}
