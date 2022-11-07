package com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.saphamrah.customer.data.local.temp.ProductModel;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class AddItemToCartFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private AddItemToCartFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private AddItemToCartFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static AddItemToCartFragmentArgs fromBundle(@NonNull Bundle bundle) {
    AddItemToCartFragmentArgs __result = new AddItemToCartFragmentArgs();
    bundle.setClassLoader(AddItemToCartFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("product")) {
      ProductModel product;
      if (Parcelable.class.isAssignableFrom(ProductModel.class) || Serializable.class.isAssignableFrom(ProductModel.class)) {
        product = (ProductModel) bundle.get("product");
      } else {
        throw new UnsupportedOperationException(ProductModel.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (product == null) {
        throw new IllegalArgumentException("Argument \"product\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("product", product);
    } else {
      throw new IllegalArgumentException("Required argument \"product\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static AddItemToCartFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    AddItemToCartFragmentArgs __result = new AddItemToCartFragmentArgs();
    if (savedStateHandle.contains("product")) {
      ProductModel product;
      product = savedStateHandle.get("product");
      if (product == null) {
        throw new IllegalArgumentException("Argument \"product\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("product", product);
    } else {
      throw new IllegalArgumentException("Required argument \"product\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ProductModel getProduct() {
    return (ProductModel) arguments.get("product");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("product")) {
      ProductModel product = (ProductModel) arguments.get("product");
      if (Parcelable.class.isAssignableFrom(ProductModel.class) || product == null) {
        __result.set("product", Parcelable.class.cast(product));
      } else if (Serializable.class.isAssignableFrom(ProductModel.class)) {
        __result.set("product", Serializable.class.cast(product));
      } else {
        throw new UnsupportedOperationException(ProductModel.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    AddItemToCartFragmentArgs that = (AddItemToCartFragmentArgs) object;
    if (arguments.containsKey("product") != that.arguments.containsKey("product")) {
      return false;
    }
    if (getProduct() != null ? !getProduct().equals(that.getProduct()) : that.getProduct() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "AddItemToCartFragmentArgs{"
        + "product=" + getProduct()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull AddItemToCartFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ProductModel product) {
      if (product == null) {
        throw new IllegalArgumentException("Argument \"product\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("product", product);
    }

    @NonNull
    public AddItemToCartFragmentArgs build() {
      AddItemToCartFragmentArgs result = new AddItemToCartFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setProduct(@NonNull ProductModel product) {
      if (product == null) {
        throw new IllegalArgumentException("Argument \"product\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("product", product);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public ProductModel getProduct() {
      return (ProductModel) arguments.get("product");
    }
  }
}
