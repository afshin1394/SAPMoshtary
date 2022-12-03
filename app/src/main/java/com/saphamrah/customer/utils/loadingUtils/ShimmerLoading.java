package com.saphamrah.customer.utils.loadingUtils;

import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ShimmerLoading {
    ShimmerFrameLayout[] shimmerFrameLayouts;
    View[] inflatedViews;
    public  void  startLoading(ShimmerFrameLayout[] shimmerViews,View[] inflatedViews){
        this.shimmerFrameLayouts = shimmerViews;
        this.inflatedViews = inflatedViews;
        Observable.fromArray(shimmerViews)
                .forEach(shimmerFrameLayout -> {
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmer();
                }).dispose();

        Observable.fromArray(inflatedViews)
                .forEach(view -> {
                    Log.i("SHIMMER", "accept: "+view.getRootView().getId());
                    view.setVisibility(View.GONE);
                }).dispose();
    }

    public void stopLoading(){
        Observable.fromArray(inflatedViews)
                .forEach(new Consumer<View>() {
                    @Override
                    public void accept(View view) throws Exception {
                        Log.i("SHIMMER", "accept: "+view.getRootView().getId());
                        view.setVisibility(View.VISIBLE);
                    }
                }).dispose();
        Observable.fromArray(shimmerFrameLayouts)
                .forEach(shimmerFrameLayout -> {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }).dispose();

    }
}
