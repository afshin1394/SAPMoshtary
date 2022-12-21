package com.saphamrah.customer.utils.RxUtils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxEditable implements TextWatcher, androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private static final String TAG = RxEditable.class.getSimpleName();
    private final PublishSubject<String> querySubject = PublishSubject.create();
    View editText;
    String defaultValue;

    public RxEditable(View view, String defaultValue)
    {
        this.editText = view;
        this.defaultValue = defaultValue;
        if (view instanceof  EditText) {
            Log.i(TAG, "EditText: "+view);
            ((EditText) view).addTextChangedListener(this);
        }
        if (view instanceof SearchView) {
            Log.i(TAG, "SearchView: "+view);
            ((androidx.appcompat.widget.SearchView) view).setOnQueryTextListener(this);
        }
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (((EditText) editText).getText().toString().equals("") || ((EditText) editText).getText().toString().isEmpty()){
//            ((EditText) editText).setText(defaultValue);
        }else {
            querySubject.onNext(editable.toString());
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i(TAG, "onQueryTextSubmit: "+query);
        querySubject.onNext(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i(TAG, "onQueryTextChange: "+newText);
        querySubject.onNext(newText);
        return false;
    }
}

