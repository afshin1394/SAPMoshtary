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

  private View view7f09014d;

  private View view7f09014e;

  private View view7f09014c;

  private View view7f090158;

  private View view7f09015f;

  private View view7f090161;

  private View view7f0900a0;

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
    view7f09014d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabMarjoeeKoli();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabMarjoeeMoredi, "field 'fabMarjoeeMoredi' and method 'fabMarjoeeMoredi'");
    target.fabMarjoeeMoredi = Utils.castView(view, R.id.fabMarjoeeMoredi, "field 'fabMarjoeeMoredi'", FloatingActionButton.class);
    view7f09014e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabMarjoeeMoredi();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabMarjoeeForoshandeh, "field 'fabMarjoeeForoshandeh' and method 'fabMarjoeeForoshandeh'");
    target.fabMarjoeeForoshandeh = Utils.castView(view, R.id.fabMarjoeeForoshandeh, "field 'fabMarjoeeForoshandeh'", FloatingActionButton.class);
    view7f09014c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabMarjoeeForoshandeh();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSabtMarjoee, "field 'fabSabtMarjoee' and method 'fabSabtMarjoee'");
    target.fabSabtMarjoee = Utils.castView(view, R.id.fabSabtMarjoee, "field 'fabSabtMarjoee'", FloatingActionButton.class);
    view7f090158 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSabtMarjoee();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSearchNameKala, "field 'fabSearchNameKala' and method 'fabSearchNameKala'");
    target.fabSearchNameKala = Utils.castView(view, R.id.fabSearchNameKala, "field 'fabSearchNameKala'", FloatingActionButton.class);
    view7f09015f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSearchNameKala();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSend, "method 'fabSend'");
    view7f090161 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSend();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'btn_back'");
    view7f0900a0 = view;
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

    view7f09014d.setOnClickListener(null);
    view7f09014d = null;
    view7f09014e.setOnClickListener(null);
    view7f09014e = null;
    view7f09014c.setOnClickListener(null);
    view7f09014c = null;
    view7f090158.setOnClickListener(null);
    view7f090158 = null;
    view7f09015f.setOnClickListener(null);
    view7f09015f = null;
    view7f090161.setOnClickListener(null);
    view7f090161 = null;
    view7f0900a0.setOnClickListener(null);
    view7f0900a0 = null;
  }
}
