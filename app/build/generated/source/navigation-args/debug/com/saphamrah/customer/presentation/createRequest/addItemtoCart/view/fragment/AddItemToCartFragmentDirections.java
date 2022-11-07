package com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class AddItemToCartFragmentDirections {
  private AddItemToCartFragmentDirections() {
  }

  @NonNull
  public static ActionAddItemToCartFragmentToCartFragment actionAddItemToCartFragmentToCartFragment(
      @NonNull ProductModel[] products, @Nullable SelectableBonus[] selectedBonuses) {
    return new ActionAddItemToCartFragmentToCartFragment(products, selectedBonuses);
  }

  public static class ActionAddItemToCartFragmentToCartFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionAddItemToCartFragmentToCartFragment(@NonNull ProductModel[] products,
        @Nullable SelectableBonus[] selectedBonuses) {
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("products", products);
      this.arguments.put("selectedBonuses", selectedBonuses);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionAddItemToCartFragmentToCartFragment setProducts(@NonNull ProductModel[] products) {
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("products", products);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionAddItemToCartFragmentToCartFragment setSelectedBonuses(
        @Nullable SelectableBonus[] selectedBonuses) {
      this.arguments.put("selectedBonuses", selectedBonuses);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_addItemToCartFragment_to_cartFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionAddItemToCartFragmentToCartFragment that = (ActionAddItemToCartFragmentToCartFragment) object;
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
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + java.util.Arrays.hashCode(getProducts());
      result = 31 * result + java.util.Arrays.hashCode(getSelectedBonuses());
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionAddItemToCartFragmentToCartFragment(actionId=" + getActionId() + "){"
          + "products=" + getProducts()
          + ", selectedBonuses=" + getSelectedBonuses()
          + "}";
    }
  }
}
