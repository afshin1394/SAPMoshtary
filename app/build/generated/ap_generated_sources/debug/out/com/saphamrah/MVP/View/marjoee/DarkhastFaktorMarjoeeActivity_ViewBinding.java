// Generated code from Butter Knife. Do not modify!
package com.saphamrah.MVP.View.marjoee;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DarkhastFaktorMarjoeeActivity_ViewBinding implements Unbinder {
  private DarkhastFaktorMarjoeeActivity target;

  private View view7f09017b;

  private View view7f09017c;

  private View view7f09017a;

  private View view7f090188;

  private View view7f090191;

  private View view7f090193;

  private View view7f0900b0;

  @UiThread
  public DarkhastFaktorMarjoeeActivity_ViewBinding(DarkhastFaktorMarjoeeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DarkhastFaktorMarjoeeActivity_ViewBinding(final DarkhastFaktorMarjoeeActivity target,
      View source) {
    this.target = target;

    View view;
    target.lblActivityTitle = Utils.findRequiredViewAsType(source, R.id.lblActivityTitle, "field 'lblActivityTitle'", TextView.class);
    target.fabMenu = Utils.findRequiredViewAsType(source, R.id.fabMenu, "field 'fabMenu'", FloatingActionMenu.class);
    view = Utils.findRequiredView(source, R.id.fabMarjoeeKoli, "field 'fabMarjoeeKoli' and method 'fabMarjoeeKoli'");
    target.fabMarjoeeKoli = Utils.castView(view, R.id.fabMarjoeeKoli, "field 'fabMarjoeeKoli'", FloatingActionButton.class);
    view7f09017b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabMarjoeeKoli();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabMarjoeeMoredi, "field 'fabMarjoeeMoredi' and method 'fabMarjoeeMoredi'");
    target.fabMarjoeeMoredi = Utils.castView(view, R.id.fabMarjoeeMoredi, "field 'fabMarjoeeMoredi'", FloatingActionButton.class);
    view7f09017c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabMarjoeeMoredi();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabMarjoeeForoshandeh, "field 'fabMarjoeeForoshandeh' and method 'fabMarjoeeForoshandeh'");
    target.fabMarjoeeForoshandeh = Utils.castView(view, R.id.fabMarjoeeForoshandeh, "field 'fabMarjoeeForoshandeh'", FloatingActionButton.class);
    view7f09017a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabMarjoeeForoshandeh();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSabtMarjoee, "field 'fabSabtMarjoee' and method 'fabSabtMarjoee'");
    target.fabSabtMarjoee = Utils.castView(view, R.id.fabSabtMarjoee, "field 'fabSabtMarjoee'", FloatingActionButton.class);
    view7f090188 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSabtMarjoee();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSearchNameKala, "field 'fabSearchNameKala' and method 'fabSearchNameKala'");
    target.fabSearchNameKala = Utils.castView(view, R.id.fabSearchNameKala, "field 'fabSearchNameKala'", FloatingActionButton.class);
    view7f090191 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSearchNameKala();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSend, "method 'fabSend'");
    view7f090193 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSend();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'btn_back'");
    view7f0900b0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DarkhastFaktorMarjoeeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lblActivityTitle = null;
    target.fabMenu = null;
    target.fabMarjoeeKoli = null;
    target.fabMarjoeeMoredi = null;
    target.fabMarjoeeForoshandeh = null;
    target.fabSabtMarjoee = null;
    target.fabSearchNameKala = null;

    view7f09017b.setOnClickListener(null);
    view7f09017b = null;
    view7f09017c.setOnClickListener(null);
    view7f09017c = null;
    view7f09017a.setOnClickListener(null);
    view7f09017a = null;
    view7f090188.setOnClickListener(null);
    view7f090188 = null;
    view7f090191.setOnClickListener(null);
    view7f090191 = null;
    view7f090193.setOnClickListener(null);
    view7f090193 = null;
    view7f0900b0.setOnClickListener(null);
    view7f0900b0 = null;
  }
}
