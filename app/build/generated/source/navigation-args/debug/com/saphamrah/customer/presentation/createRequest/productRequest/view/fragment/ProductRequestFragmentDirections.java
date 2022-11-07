package com.saphamrah.customer.presentation.createRequest.productRequest.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ProductRequestFragmentDirections {
  private ProductRequestFragmentDirections() {
  }

  @NonNull
  public static ActionProductRequestFragmentToAddItemToCartFragment actionProductRequestFragmentToAddItemToCartFragment(
      @NonNull ProductModel product) {
    return new ActionProductRequestFragmentToAddItemToCartFragment(product);
  }

  @NonNull
  public static ActionProductRequestFragmentToFilterFragment actionProductRequestFragmentToFilterFragment(
      ) {
    return new ActionProductRequestFragmentToFilterFragment();
  }

  @NonNull
  public static ActionProductRequestFragmentToCartFragment actionProductRequestFragmentToCartFragment(
      @NonNull ProductModel[] products, @Nullable SelectableBonus[] selectedBonuses) {
    return new ActionProductRequestFragmentToCartFragment(products, selectedBonuses);
  }

  public static class ActionProductRequestFragmentToAddItemToCartFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionProductRequestFragmentToAddItemToCartFragment(@NonNull ProductModel product) {
      if (product == null) {
        throw new IllegalArgumentException("Argument \"product\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("product", product);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionProductRequestFragmentToAddItemToCartFragment setProduct(
        @NonNull ProductModel product) {
      if (product == null) {
        throw new IllegalArgumentException("Argument \"product\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("product", product);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("product")) {
        ProductModel product = (ProductModel) arguments.get("product");
        if (Parcelable.class.isAssignableFrom(ProductModel.class) || product == null) {
          __result.putParcelable("product", Parcelable.class.cast(product));
        } else if (Serializable.class.isAssignableFrom(ProductModel.class)) {
          __result.putSerializable("product", Serializable.class.cast(product));
        } else {
          throw new UnsupportedOperationException(ProductModel.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_productRequestFragment_to_addItemToCartFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ProductModel getProduct() {
      return (ProductModel) arguments.get("product");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionProductRequestFragmentToAddItemToCartFragment that = (ActionProductRequestFragmentToAddItemToCartFragment) object;
      if (arguments.containsKey("product") != that.arguments.containsKey("product")) {
        return false;
      }
      if (getProduct() != null ? !getProduct().equals(that.getProduct()) : that.getProduct() != null) {
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
      result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionProductRequestFragmentToAddItemToCartFragment(actionId=" + getActionId() + "){"
          + "product=" + getProduct()
          + "}";
    }
  }

  public static class ActionProductRequestFragmentToFilterFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionProductRequestFragmentToFilterFragment() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionProductRequestFragmentToFilterFragment setFilterSortType(int FilterSortType) {
      this.arguments.put("FilterSortType", FilterSortType);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("FilterSortType")) {
        int FilterSortType = (int) arguments.get("FilterSortType");
        __result.putInt("FilterSortType", FilterSortType);
      } else {
        __result.putInt("FilterSortType", 1);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_productRequestFragment_to_filterFragment;
    }

    @SuppressWarnings("unchecked")
    public int getFilterSortType() {
      return (int) arguments.get("FilterSortType");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionProductRequestFragmentToFilterFragment that = (ActionProductRequestFragmentToFilterFragment) object;
      if (arguments.containsKey("FilterSortType") != that.arguments.containsKey("FilterSortType")) {
        return false;
      }
      if (getFilterSortType() != that.getFilterSortType()) {
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
      result = 31 * result + getFilterSortType();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionProductRequestFragmentToFilterFragment(actionId=" + getActionId() + "){"
          + "FilterSortType=" + getFilterSortType()
          + "}";
    }
  }

  public static class ActionProductRequestFragmentToCartFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionProductRequestFragmentToCartFragment(@NonNull ProductModel[] products,
        @Nullable SelectableBonus[] selectedBonuses) {
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("products", products);
      this.arguments.put("selectedBonuses", selectedBonuses);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionProductRequestFragmentToCartFragment setProducts(
        @NonNull ProductModel[] products) {
      if (products == null) {
        throw new IllegalArgumentException("Argument \"products\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("products", products);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionProductRequestFragmentToCartFragment setSelectedBonuses(
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
      return R.id.action_productRequestFragment_to_cartFragment;
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
      ActionProductRequestFragmentToCartFragment that = (ActionProductRequestFragmentToCartFragment) object;
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
      return "ActionProductRequestFragmentToCartFragment(actionId=" + getActionId() + "){"
          + "products=" + getProducts()
          + ", selectedBonuses=" + getSelectedBonuses()
          + "}";
    }
  }
}
