package com.saphamrah.customer.presentation.createRequest.verifyRequest.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.databinding.FragmentVerifyRequestBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor.VerifyRequestInteractor;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.presenter.VerifyRequestPresenter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.JayezehPishFaktorAdapter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.KalaPishFaktorAdapter;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter.TakhfifPishFaktorAdatper;
import com.saphamrah.customer.utils.customViews.DrawingView;

import java.util.List;

public class VerifyRequestFragment extends BaseFragment<VerifyRequestInteractor.PresenterOps, FragmentVerifyRequestBinding, CreateRequestActivity> implements VerifyRequestInteractor.RequiredViewOps {

    private List<ProductModel> productModelGlobal;
//    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelsGlobal;
    private List<BonusModel> bonusModelsGlobal;
    private List<DiscountModel> discountModelsGlobal;

    private DrawingView drawingView;

    public VerifyRequestFragment() {
        super(R.layout.fragment_verify_request);
    }

    @Override
    protected void onBackPressed() {
    }

    @Override
    protected void initViews() {
        initialSignLayout();

        initCustomerInfo();

        initKala();
        initTakhfif();
        initMarjoee();
        initJayezeh();

        viewBinding.imgClearSign.setOnClickListener(v -> drawingView.clearCanvas());
        viewBinding.btnApply.setOnClickListener(v -> checkSaveSignPic());

    }

    private void checkSaveSignPic() {
        String description = viewBinding.txtDesc.getText().toString();

       /* if (enableEmzaMoshtary.equals("0")) {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            Bitmap bmp = Bitmap.createBitmap(1, 1, conf);
            mPresenter.checkSaveBitmap(description, ccMoshtary, new PubFunc().new ImageUtils().convertBitmapToByteArray(VerifyCustomerRequestActivity2.this, bmp, 100));
            bmp.recycle();
        } else if (enableEmzaMoshtary.equals("1")) {
            Bitmap bitmap = drawingView.getBitmap();
            Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            emptyBitmap.eraseColor(getResources().getColor(R.color.colorWhite));

            if (bitmap.sameAs(emptyBitmap)) {
                showToast(R.string.errorEmptyEmzaMoshtary, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            } else {
                mPresenter.checkSaveBitmap(description, ccMoshtary, new PubFunc().new ImageUtils().convertBitmapToByteArray(VerifyCustomerRequestActivity2.this, bitmap, 100));
                bitmap.recycle();
                emptyBitmap.recycle();
            }
        }*/

        Bitmap bitmap = drawingView.getBitmap();
        Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        emptyBitmap.eraseColor(getResources().getColor(R.color.colorWhite));

        if (bitmap.sameAs(emptyBitmap)) {
//            showToast(R.string.errorEmptyEmzaMoshtary, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        } else {
//            mPresenter.checkSaveBitmap(description, ccMoshtary, new PubFunc().new ImageUtils().convertBitmapToByteArray(VerifyCustomerRequestActivity2.this, bitmap, 100));
            bitmap.recycle();
            emptyBitmap.recycle();
        }
    }

    private void initCustomerInfo() {
        String address = activity.getAddress();
//        String receipt = activity.getReceipt();

        viewBinding.txtAddress.setText(address);
//        viewBinding.txtNoeVosol.setText(receipt);

    }

    private void initJayezeh() {
        bonusModelsGlobal = activity.getBonusModelsGlobal();

        if (bonusModelsGlobal.size() > 0) {
//            layKala.setVisibility(View.VISIBLE);
            JayezehPishFaktorAdapter adapter = new JayezehPishFaktorAdapter(getContext(), bonusModelsGlobal, 1);
            viewBinding.recyclerKalaJayezeh.setLayoutManager(new LinearLayoutManager(getContext()));
            viewBinding.recyclerKalaJayezeh.setItemAnimator(new DefaultItemAnimator());
            viewBinding.recyclerKalaJayezeh.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
            viewBinding.recyclerKalaJayezeh.setAdapter(adapter);
        } else {
//            layKala.setVisibility(View.GONE);
        }
    }

    private void initMarjoee() {

    }

    private void initTakhfif() {
        discountModelsGlobal = activity.getDiscountModelsGlobal();

        if (discountModelsGlobal.size() > 0) {
            //            layKala.setVisibility(View.VISIBLE);
            TakhfifPishFaktorAdatper adapter = new TakhfifPishFaktorAdatper(getContext(), discountModelsGlobal, 1);
            viewBinding.recyclerTakhfif.setLayoutManager(new LinearLayoutManager(getContext()));
            viewBinding.recyclerTakhfif.setItemAnimator(new DefaultItemAnimator());
            viewBinding.recyclerTakhfif.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
            viewBinding.recyclerTakhfif.setAdapter(adapter);
        } else {
//            layKala.setVisibility(View.GONE);
        }
    }

    private void initKala() {
        productModelGlobal = activity.getProductModelGlobal();

        if (productModelGlobal.size() > 0) {
//            layKala.setVisibility(View.VISIBLE);
            KalaPishFaktorAdapter adapter = new KalaPishFaktorAdapter(getContext(), productModelGlobal, 1);
            viewBinding.recyclerKalaInfo.setLayoutManager(new LinearLayoutManager(getContext()));
            viewBinding.recyclerKalaInfo.setItemAnimator(new DefaultItemAnimator());
            viewBinding.recyclerKalaInfo.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
            viewBinding.recyclerKalaInfo.setAdapter(adapter);
        } else {
//            layKala.setVisibility(View.GONE);
        }
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
        viewBinding.laySign.addView(drawingView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
