package com.saphamrah.customer.utils.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.customer.utils.RxUtils.Watcher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MaterialSearchWatcher extends MaterialSearchView implements MaterialSearchView.OnQueryTextListener {

    private final PublishSubject<String> querySubject = PublishSubject.create();
    private Context context;



    public MaterialSearchWatcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnQueryTextListener(this);
        this.context = context;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        querySubject.onNext(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        querySubject.onNext(newText);
        return false;
    }

    public void addTextWatcher(Watcher watcher, long emitTime) {
        querySubject
                .debounce(emitTime, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<String>>) Observable::just)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        watcher.onTextChange(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
