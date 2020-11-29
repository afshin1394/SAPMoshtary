package com.saphamrah.Adapter;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;

import com.ramotion.foldingcell.FoldingCell;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import java.util.ArrayList;
import java.util.HashSet;

public class GetProgramAdapter extends ArrayAdapter<ForoshandehMamorPakhshModel>
{

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //private View.OnClickListener defaultRequestBtnClickListener;
    private Activity activity;
    //private int selectedForoshandehIndex;

    public GetProgramAdapter(Activity activity, ArrayList<ForoshandehMamorPakhshModel> objects)
    {
        super(activity, 0, objects);
        this.activity = activity;
        //selectedForoshandehIndex = -1;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent)
    {
        // get item for selected view
        ForoshandehMamorPakhshModel item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        final Typeface font = Typeface.createFromAsset(getContext().getAssets() , getContext().getResources().getString(R.string.fontPath));
        if (cell == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.get_program_customlist, parent, false);
            // binding view parts to view holder
            //Title
            viewHolder.lblFullNameCodeForoshandeh = cell.findViewById(R.id.lblFullNameCodeForoshandeh);
            viewHolder.lblNameMarkazSazmanForosh = cell.findViewById(R.id.lblNameMarkazSazmanForosh);
            viewHolder.lblNoeForoshandeh = cell.findViewById(R.id.lblNoeForoshandeh);
            //Content
            viewHolder.lblFullNameCodeForoshandehChild = cell.findViewById(R.id.lblFullNameCodeForoshandehChild);
            viewHolder.lblNameMarkazSazmanForoshChild = cell.findViewById(R.id.lblNameMarkazSazmanForoshChild);
            viewHolder.lblNoeForoshandehChild = cell.findViewById(R.id.lblNoeForoshandehChild);
            viewHolder.imgProfile = cell.findViewById(R.id.imgProfile);
            viewHolder.layGetProgramDate = cell.findViewById(R.id.layGetProgramDate);
            viewHolder.lblGetProgramDate = cell.findViewById(R.id.lblDate);
            viewHolder.lblGetProgram = cell.findViewById(R.id.lblGetProgram);
            viewHolder.layOperations = cell.findViewById(R.id.laySlider);
            viewHolder.imgNext = cell.findViewById(R.id.imgNextSlide);
            viewHolder.imgPrevious = cell.findViewById(R.id.imgPreviousSlide);
            viewHolder.viewFlipper = cell.findViewById(R.id.viewFlipper);
            viewHolder.lblUpdateForoshandeh = cell.findViewById(R.id.lblUpdateForoshandeh);
            viewHolder.lblUpdateKalaModatVosol = cell.findViewById(R.id.lblUpdateKalaModatVosol);
            viewHolder.lblUpdateJayezehTakhfif = cell.findViewById(R.id.lblUpdateJayezehTakhfif);
            viewHolder.lblUpdateCustomers = cell.findViewById(R.id.lblUpdateCustomers);
            viewHolder.lblUpdateParameters = cell.findViewById(R.id.lblUpdateParameters);
            viewHolder.lblUpdateEtebarForoshandeh = cell.findViewById(R.id.lblUpdateEtebarForoshandeh);

            viewHolder.lblGetProgram.setTag(position);
            viewHolder.lblUpdateForoshandeh.setTag(position);
            viewHolder.lblUpdateKalaModatVosol.setTag(position);
            viewHolder.lblUpdateJayezehTakhfif.setTag(position);
            viewHolder.lblUpdateCustomers.setTag(position);
            viewHolder.lblUpdateParameters.setTag(position);
            viewHolder.lblUpdateEtebarForoshandeh.setTag(position);

            cell.setTag(viewHolder);
        }
        else
        {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position))
            {
                cell.unfold(true);
            }
            else
            {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
        {
            return cell;
        }

        PubFunc.DateUtils dateUtils = new PubFunc().new DateUtils();
        String today = dateUtils.todayDateWithSlash(getContext());
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(item);

        // bind data from selected element to view through view holder
        viewHolder.lblFullNameCodeForoshandeh.setText(String.format("%1$s - %2$s" , item.getCodeForoshandeh() , item.getFullName()));
        viewHolder.lblNoeForoshandeh.setText(item.getNameNoeForoshandehMamorPakhsh());
        viewHolder.lblFullNameCodeForoshandehChild.setText(String.format("%1$s - %2$s" , item.getCodeForoshandeh() , item.getFullName()));
        viewHolder.lblNoeForoshandehChild.setText(item.getNameNoeForoshandehMamorPakhsh());
        viewHolder.imgProfile.setImageResource(R.drawable.ic_account);
        viewHolder.lblGetProgramDate.setText(today);
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            viewHolder.lblNameMarkazSazmanForosh.setText(item.getNameMarkazAnbar());
            viewHolder.lblNameMarkazSazmanForoshChild.setText(item.getNameMarkazAnbar());
        }
        else
        {
            viewHolder.lblNameMarkazSazmanForosh.setText(String.format("%1$s - %2$s" , item.getNameMarkazForosh() , item.getNameSazmanForosh()));
            viewHolder.lblNameMarkazSazmanForoshChild.setText(String.format("%1$s - %2$s" , item.getNameMarkazForosh() , item.getNameSazmanForosh()));
        }

        viewHolder.lblFullNameCodeForoshandeh.setTypeface(font);
        viewHolder.lblNoeForoshandeh.setTypeface(font);
        viewHolder.lblNameMarkazSazmanForosh.setTypeface(font);
        viewHolder.lblFullNameCodeForoshandehChild.setTypeface(font);
        viewHolder.lblNoeForoshandehChild.setTypeface(font);
        viewHolder.lblNameMarkazSazmanForoshChild.setTypeface(font);
        viewHolder.lblGetProgramDate.setTypeface(font);
        viewHolder.lblGetProgram.setTypeface(font);
        viewHolder.lblUpdateForoshandeh.setTypeface(font);
        viewHolder.lblUpdateKalaModatVosol.setTypeface(font);
        viewHolder.lblUpdateJayezehTakhfif.setTypeface(font);
        viewHolder.lblUpdateCustomers.setTypeface(font);
        viewHolder.lblUpdateParameters.setTypeface(font);
        viewHolder.lblUpdateEtebarForoshandeh.setTypeface(font);

        setSliderBackgroundColor(viewHolder);


        viewHolder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.viewFlipper.setInAnimation(getContext(), R.anim.left_to_center);
                viewHolder.viewFlipper.setOutAnimation(getContext(), R.anim.center_to_right);
                viewHolder.viewFlipper.showNext();
                setSliderBackgroundColor(viewHolder);
            }
        });


        viewHolder.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.viewFlipper.setInAnimation(getContext(), R.anim.right_to_center);
                viewHolder.viewFlipper.setOutAnimation(getContext(), R.anim.center_to_left);
                viewHolder.viewFlipper.showPrevious();
                setSliderBackgroundColor(viewHolder);
            }
        });


        // set custom btn handler for list item from that item
        if (item.getBtnGetProgramClickListener() != null)
        {
            viewHolder.lblGetProgram.setOnClickListener(item.getBtnGetProgramClickListener());
        }

        if (item.getbtnShowDateAlertClickListener() != null)
        {
            viewHolder.layGetProgramDate.setOnClickListener(item.getbtnShowDateAlertClickListener());
        }

        if (item.getBtnUpdateForoshandehClickListener() != null)
        {
            viewHolder.lblUpdateForoshandeh.setOnClickListener(item.getBtnUpdateForoshandehClickListener());
        }

        if (item.getBtnUpdateKalaModatVosolClickListener() != null)
        {
            viewHolder.lblUpdateKalaModatVosol.setOnClickListener(item.getBtnUpdateKalaModatVosolClickListener());
        }

        if (item.getBtnUpdateJayezehTakhfifClickListener() != null)
        {
            viewHolder.lblUpdateJayezehTakhfif.setOnClickListener(item.getBtnUpdateJayezehTakhfifClickListener());
        }

        if (item.getBtnUpdateCustomersClickListener() != null)
        {
            viewHolder.lblUpdateCustomers.setOnClickListener(item.getBtnUpdateCustomersClickListener());
        }

        if (item.getBtnUpdateParametersClickListener() != null)
        {
            viewHolder.lblUpdateParameters.setOnClickListener(item.getBtnUpdateParametersClickListener());
        }

        if (item.getBtnUpdateEtebarForoshandehClickListener() != null)
        {
            viewHolder.lblUpdateEtebarForoshandeh.setOnClickListener(item.getBtnUpdateEtebarForoshandehClickListener());
        }

        return cell;
    }


    private void setSliderBackgroundColor(ViewHolder viewHolder)
    {
        switch (viewHolder.viewFlipper.getDisplayedChild())
        {
            case 0:
                viewHolder.layOperations.setBackgroundColor(activity.getResources().getColor(R.color.colorSliderForoshandeh));
                break;
            case 1:
                viewHolder.layOperations.setBackgroundColor(activity.getResources().getColor(R.color.colorSliderKalaModatVosol));
                break;
            case 2:
                viewHolder.layOperations.setBackgroundColor(activity.getResources().getColor(R.color.colorSliderJayezehTakhfif));
                break;
            case 3:
                viewHolder.layOperations.setBackgroundColor(activity.getResources().getColor(R.color.colorSliderCustomers));
                break;
            case 4:
                viewHolder.layOperations.setBackgroundColor(activity.getResources().getColor(R.color.colorSliderParameters));
                break;
            case 5:
                viewHolder.layOperations.setBackgroundColor(activity.getResources().getColor(R.color.colorSliderEtebarForoshandeh));
                break;
            default:
                viewHolder.layOperations.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }

    public void registerFold(int position)
    {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position)
    {
        unfoldedIndexes.add(position);
    }

    public HashSet<Integer> getUnfoldedIndexes()
    {
        return unfoldedIndexes;
    }



    // View lookup cache
    private static class ViewHolder
    {
        TextView lblFullNameCodeForoshandeh;
        TextView lblNameMarkazSazmanForosh;
        TextView lblNoeForoshandeh;
        TextView lblFullNameCodeForoshandehChild;
        TextView lblNameMarkazSazmanForoshChild;
        TextView lblNoeForoshandehChild;
        ImageView imgProfile;
        LinearLayout layGetProgramDate;
        TextView lblGetProgramDate;
        TextView lblGetProgram;
        RelativeLayout layOperations;
        ImageView imgNext;
        ImageView imgPrevious;
        ViewFlipper viewFlipper;
        TextView lblUpdateForoshandeh;
        TextView lblUpdateKalaModatVosol;
        TextView lblUpdateJayezehTakhfif;
        TextView lblUpdateCustomers;
        TextView lblUpdateParameters;
        TextView lblUpdateEtebarForoshandeh;
    }



}
