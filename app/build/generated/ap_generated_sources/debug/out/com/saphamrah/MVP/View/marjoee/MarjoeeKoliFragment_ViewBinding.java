// Generated code from Butter Knife. Do not modify!
package com.saphamrah.MVP.View.marjoee;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.saphamrah.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MarjoeeKoliFragment_ViewBinding implements Unbinder {
  private MarjoeeKoliFragment target;

  @UiThread
  public MarjoeeKoliFragment_ViewBinding(MarjoeeKoliFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MarjoeeKoliFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
  }
}
