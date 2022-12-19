package com.saphamrah.customer.presentation.createRequest.verifyRequest.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.databinding.FragmentVerifyRequestBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor.VerifyRequestInteractor;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.presenter.VerifyRequestPresenter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.JayezehPishFaktorAdapter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.KalaPishFaktorAdapter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.MarjoeePishFaktorAdapter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.TakhfifPishFaktorAdatper;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.customViews.CustomSnackBar;
import com.saphamrah.customer.utils.customViews.DrawingView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class VerifyRequestFragment extends BaseFragment<VerifyRequestInteractor.PresenterOps, FragmentVerifyRequestBinding, CreateRequestActivity> implements VerifyRequestInteractor.RequiredViewOps {

    private List<ProductModel> productModelGlobal;
    //    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelsGlobal;
    private List<BonusModel> bonusModelsGlobal;
    private List<DiscountModel> discountModelsGlobal;
    private List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsGlobal;


    private DrawingView drawingView;

    @Override
    protected void onBackPressed() {
        activity.paymentState = Constants.PaymentStates.CONFIRM_REQUEST;
        navigateUp();

    }

    @Override
    protected void setPresenter() {
        presenter = new VerifyRequestPresenter(this);
    }

    @Override
    protected FragmentVerifyRequestBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVerifyRequestBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {

        initialSignLayout();

        initCustomerInfo();

        initKala();
        initTakhfif();
        initMarjoee();
        initJayezeh();

//        viewBinding.imgClearSign.setOnClickListener(v -> drawingView.clearCanvas());
        viewBinding.btnApply.setOnClickListener(v -> checkSaveSignPic());

    }



    private void checkSaveSignPic() {
        String description = viewBinding.txtDesc.getText().toString();


        takeScreenshotOfFaktor(new Random().nextInt(1000));
        CustomSnackBar.showSnack(requireContext(), getView(), getString(R.string.saveRequest), Snackbar.LENGTH_SHORT, "").show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        activity.finish();
            }
        },1000);

//        presenter.saveRequest(activity.getProductModelGlobal(),activity.getDiscountModelsGlobal(),activity.getBonusModelsGlobal(),activity.getJayezehEntekhabiMojodiModels(),activity.getElamMarjoeeForoshandehModelsGlobal());
//        activity.finish();

    }

    private void initCustomerInfo() {
        String address = activity.getAddress();
//        String receipt = activity.getReceipt();

        viewBinding.txtAddress.setText(address);
//        viewBinding.txtNoeVosol.setText(receipt);

    }

    private Bitmap getBitmapFromView(View view, int width, int height)
    {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
        {
            canvas.drawColor(Color.WHITE);
            bgDrawable.draw(canvas);
        }
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


    Bitmap DarkhastImage;

    public void takeScreenshotOfFaktor(long ccDarkhastFaktor)
    {
        try
        {
            String mPath ="";
            String typeStr ="";

            File pix = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File screenshots = new File(pix, "Screenshots");
            mPath = screenshots.getPath();
            typeStr = "/Emza-";


            File tempdir = new File(mPath);
            if (!tempdir.exists())
                tempdir.mkdirs();


            mPath = mPath + typeStr + ccDarkhastFaktor + ".jpg";


            Log.d("Printactivity","screen charkareh");
            viewBinding.layMain.setDrawingCacheEnabled(true);

            viewBinding.layMain.getChildAt(1).measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            viewBinding.layMain.layout(0, 0, viewBinding.layMain.getMeasuredWidth(), viewBinding.layMain.getMeasuredHeight() - viewBinding.btnApply.getMeasuredHeight() );
            DarkhastImage = getBitmapFromView(viewBinding.layMain, viewBinding.layMain.getWidth(), viewBinding.layMain.getHeight() -viewBinding.btnApply.getHeight() );
            viewBinding.layMain.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);
            Log.i(TAG, "takeScreenshotOfFaktor: "+mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            DarkhastImage.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            DarkhastImage.recycle();
            outputStream.flush();
            outputStream.close();
//            Resize(mPath);

        } catch (Throwable e)
        {
            e.printStackTrace();
        }
    }



    private void initJayezeh() {
        bonusModelsGlobal = activity.getBonusModelsGlobal();

        if (bonusModelsGlobal != null) {
            if (bonusModelsGlobal.size() > 0) {
                viewBinding.lyJayezeh.setVisibility(View.VISIBLE);
                JayezehPishFaktorAdapter adapter = new JayezehPishFaktorAdapter(getContext(), bonusModelsGlobal, 1);
                viewBinding.recyclerKalaJayezeh.setLayoutManager(new LinearLayoutManager(getContext()));
                viewBinding.recyclerKalaJayezeh.setItemAnimator(new DefaultItemAnimator());
                viewBinding.recyclerKalaJayezeh.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
                viewBinding.recyclerKalaJayezeh.setAdapter(adapter);
            } else {
                viewBinding.lyJayezeh.setVisibility(View.GONE);
            }
        } else {
            viewBinding.lyJayezeh.setVisibility(View.GONE);
        }

    }

    private void initMarjoee() {
        elamMarjoeeForoshandehModelsGlobal = activity.getElamMarjoeeForoshandehModelsGlobal();

        if (elamMarjoeeForoshandehModelsGlobal != null) {
            if (elamMarjoeeForoshandehModelsGlobal.size() > 0) {
                viewBinding.lyMarjoee.setVisibility(View.VISIBLE);
                MarjoeePishFaktorAdapter adapter = new MarjoeePishFaktorAdapter(getContext(), elamMarjoeeForoshandehModelsGlobal, 1);
                viewBinding.recyclerKalaMarjoee.setLayoutManager(new LinearLayoutManager(getContext()));
                viewBinding.recyclerKalaMarjoee.setItemAnimator(new DefaultItemAnimator());
                viewBinding.recyclerKalaMarjoee.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
                viewBinding.recyclerKalaMarjoee.setAdapter(adapter);
            } else {
                viewBinding.lyMarjoee.setVisibility(View.GONE);
            }
        } else {
            viewBinding.lyMarjoee.setVisibility(View.GONE);
        }
//
    }

    private void initTakhfif() {
        discountModelsGlobal = activity.getDiscountModelsGlobal();

        if (discountModelsGlobal != null) {
            if (discountModelsGlobal.size() > 0) {
                viewBinding.lyTakhfif.setVisibility(View.VISIBLE);
                TakhfifPishFaktorAdatper adapter = new TakhfifPishFaktorAdatper(getContext(), discountModelsGlobal, 1);
                viewBinding.recyclerTakhfif.setLayoutManager(new LinearLayoutManager(getContext()));
                viewBinding.recyclerTakhfif.setItemAnimator(new DefaultItemAnimator());
                viewBinding.recyclerTakhfif.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
                viewBinding.recyclerTakhfif.setAdapter(adapter);
            } else {
                viewBinding.lyTakhfif.setVisibility(View.GONE);
            }
        } else {
            viewBinding.lyTakhfif.setVisibility(View.GONE);
        }

    }

    private void initKala() {
        productModelGlobal = activity.getProductModelGlobal();

        if (productModelGlobal != null) {
            if (productModelGlobal.size() > 0) {
                viewBinding.lyKala.setVisibility(View.VISIBLE);
                productModelGlobal = io.reactivex.Observable.fromIterable(productModelGlobal).filter(it -> it.getOrderCount() > 0).toList().blockingGet();
                KalaPishFaktorAdapter adapter = new KalaPishFaktorAdapter(getContext(), productModelGlobal, 1);
                viewBinding.recyclerKalaInfo.setLayoutManager(new LinearLayoutManager(getContext()));
                viewBinding.recyclerKalaInfo.setItemAnimator(new DefaultItemAnimator());
                viewBinding.recyclerKalaInfo.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
                viewBinding.recyclerKalaInfo.setAdapter(adapter);
            } else {
                viewBinding.lyKala.setVisibility(View.GONE);
            }
        } else {
            viewBinding.lyKala.setVisibility(View.GONE);
        }

    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return null;
    }


    private void initialSignLayout() {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        drawingView = new DrawingView(getContext(), mPaint);
//        viewBinding.laySign.addView(drawingView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
