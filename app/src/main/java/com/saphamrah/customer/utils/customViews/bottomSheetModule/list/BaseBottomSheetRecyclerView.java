package com.saphamrah.customer.utils.customViews.bottomSheetModule.list;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.saphamrah.customer.R;
import com.saphamrah.customer.utils.customViews.SearchWatcher;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.BaseBottomSheet;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.abstractions.IRecyclerView;

public class BaseBottomSheetRecyclerView<V extends ViewBinding> extends BaseBottomSheet<V> implements IRecyclerView {

    protected View view;
    protected RecyclerView recyclerView;
    protected Button btnApply;
    protected SearchWatcher searchView;
    protected ImageView closeBtn;
    protected ImageView searchIcon;
    protected EditText searchEditText;

    private RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;

    public BaseBottomSheetRecyclerView(V viewBinding,
                                       Context context,
                                       int parentLayoutBottomSheetResId,
                                       RecyclerView.LayoutManager layoutManager,
                                       BaseBottomSheetRecyclerViewBuilder<V> bottomSheetRecyclerViewBuilder
                                       ) {

        super(viewBinding, context, parentLayoutBottomSheetResId);

        view = viewBinding.getRoot();

        this.layoutManager = layoutManager;
        this.dividerItemDecoration = bottomSheetRecyclerViewBuilder.dividerItemDecoration;

        recyclerView = view.findViewById(R.id.recyclerView_bottomSheet);
        btnApply = view.findViewById(R.id.button_recyclerView_bottomSheet);
        searchView = view.findViewById(R.id.searchView_recyclerView_bottomSheet);

        closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        initRecyclerView();
    }

    @Override
    public void initRecyclerView() {
        initBottomSheetView(context);

        recyclerView.setLayoutManager(layoutManager);

        if (dividerItemDecoration != null)
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static class BaseBottomSheetRecyclerViewBuilder<V extends ViewBinding> {
       /* private V viewBinding;
        private Context context;
        private int resId;
        private RecyclerView.LayoutManager layoutManager;*/

        private DividerItemDecoration dividerItemDecoration;


       /* public BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<V> setViewBinding(V viewBinding) {
            this.viewBinding = viewBinding;
            return this;
        }

        public BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<V> setContext(Context context) {
            this.context = context;
            return this;
        }

        public BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<V> setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<V> setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }*/

        public BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<V> setDividerItemDecoration(DividerItemDecoration dividerItemDecoration) {
            this.dividerItemDecoration = dividerItemDecoration;
            return this;
        }

       /* public BaseBottomSheetRecyclerView<V> create() {
            return new BaseBottomSheetRecyclerView<V>(viewBinding, context, resId, layoutManager, this);
        }*/

    }
}
