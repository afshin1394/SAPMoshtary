package com.saphamrah.customer.utils.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

import com.saphamrah.customer.R;
import com.saphamrah.customer.utils.NumberUtils;
import com.saphamrah.customer.utils.RxUtils.Watcher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class EditTextWatcher extends AppCompatEditText implements TextWatcher{
    private final BehaviorSubject<String> querySubject = BehaviorSubject.create();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public EditTextWatcher(@NonNull Context context)
    {
        super(context);
        this.addTextChangedListener(this);
    }

    public EditTextWatcher(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.addTextChangedListener(this);
    }

    public EditTextWatcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        querySubject.onNext(NumberUtils.arabicToDecimal(editable.toString()));
    }

    /**this is necessary for disposing the querySubject**/
    public void removeWatcher()
    {
        compositeDisposable.clear();
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
                               compositeDisposable.add(d);
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

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EditTextWatcher.this.setTextCursorDrawable(null);
            EditTextWatcher.this.setTextColor(getContext().getColor(R.color.colorTextPrimary));
            EditTextWatcher.this.setBackgroundTintList(getContext().getResources().getColorStateList(R.drawable.edit_text_background_shape));
        }
    }


}
