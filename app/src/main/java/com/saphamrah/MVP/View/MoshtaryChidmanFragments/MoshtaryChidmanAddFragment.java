package com.saphamrah.MVP.View.MoshtaryChidmanFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.saphamrah.MVP.View.MoshtaryChidmanActivity;
import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;

import org.json.JSONObject;

import java.lang.reflect.Array;


public class MoshtaryChidmanAddFragment extends Fragment {


     private MoshtaryChidmanModel moshtaryChidmanModel;
     private EditText chidmanText;
     private ImageView chidmanImg;
     private Context context;
     private AppCompatButton btnApply;
     private MoshtaryChidmanActivity.Action action;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public MoshtaryChidmanAddFragment() {
        // Required empty public constructor}
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
          moshtaryChidmanModel = null;
          moshtaryChidmanModel = getArguments().getParcelable("moshtaryChidmanModel");
          action =(MoshtaryChidmanActivity.Action) getArguments().get("Action");
        }
    }

    private void findViews(View view) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
        chidmanImg = view.findViewById(R.id.chidmanImg);
        chidmanText = view.findViewById(R.id.chidmanDetails);
        chidmanText.setTypeface(font);
        btnApply = view.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(v -> {



            moshtaryChidmanModel.setDescription(chidmanText.getText().toString());
            if (moshtaryChidmanModel.getImage()!=null)
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(moshtaryChidmanModel.getImage(), 0, moshtaryChidmanModel.getImage().length);
                byte[] image = new ImageUtils().convertBitmapToByteArray(context, bitmap, 70, Bitmap.CompressFormat.JPEG);
                moshtaryChidmanModel.setImage(image);
            }

            if (action.equals(MoshtaryChidmanActivity.Action.UPDATE))
            ((MoshtaryChidmanActivity)getActivity()).getPresenter().updateMoshtaryChidman(moshtaryChidmanModel);

            else
            ((MoshtaryChidmanActivity)getActivity()).getPresenter().insertMoshtaryChidman(moshtaryChidmanModel);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moshtary_chidman_add, container, false);
        findViews(view);
        setValues();


        return view;
    }

    private void setValues() {

        if (moshtaryChidmanModel.getImage() != null)
            chidmanImg.setImageBitmap(new ImageUtils().convertByteArrayToBitmap(context,moshtaryChidmanModel.getImage()));
        else
            chidmanImg.setImageResource(R.drawable.ic_stand_foreground);

        if (moshtaryChidmanModel.getDescription()!=null)
            chidmanText.setText(moshtaryChidmanModel.getDescription());
    }



}
