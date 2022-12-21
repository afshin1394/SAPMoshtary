package com.saphamrah.customer.utils.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
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

@SuppressLint("AppCompatCustomView")
public class PrefixPhoneNumberCustomEditText extends TextInputEditText implements TextWatcher {

    private static final String TAG = PrefixPhoneNumberCustomEditText.class.getSimpleName();
    private final PublishSubject<String> querySubject = PublishSubject.create();

    private String mPrefix = "09"; // can be hardcoded for demo purposes
    private Rect mPrefixRect = new Rect(); // actual prefix size

    public PrefixPhoneNumberCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addTextChangedListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getPaint().getTextBounds(mPrefix, 0, mPrefix.length(), mPrefixRect);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawText(mPrefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + mPrefixRect.width();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        querySubject.onNext(editable.toString());
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
//                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
