package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;


public class RequestCustomerListAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private static final int MOSHTARY_ZANGIREI = -1;
    public static final int MOSHTARY_GHEIRE_ZANGIREI = 1;
    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<MoshtaryModel> moshtaryModels;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private ArrayList<Integer> arrayListNoeMorajeh;
    private ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels;
    private boolean canUpdateCustomer;
    private int lastPosition = -1;

    public RequestCustomerListAdapter(Context context, ArrayList<MoshtaryModel> moshtaryModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModels, ArrayList<Integer> arrayListNoeMorajeh, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, boolean canUpdateCustomer, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.moshtaryModels = moshtaryModels;
        this.moshtaryAddressModels = moshtaryAddressModels;
        this.arrayListNoeMorajeh = arrayListNoeMorajeh;
        this.canUpdateCustomer = canUpdateCustomer;
        this.moshtaryGharardadModels = moshtaryGharardadModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

        switch (viewType) {
            case MOSHTARY_ZANGIREI:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_customer_zangirei_customlist, parent, false);
                new PubFunc().new FontUtils().setFont(((ViewGroup) view), font);
                return new View_Holder_Zangirei(view);


            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_customer_customlist, parent, false);
                new PubFunc().new FontUtils().setFont(((ViewGroup) view), font);
                return new View_Holder_Gheire_Zangireii(view);

        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MoshtaryAddressModel moshtaryAddressModel = moshtaryAddressModels.get(position);

//        Log.i("onBindViewHolder", "onBindViewHolder: "+position+ "\n "+ getItemViewType(position)+"\n"+moshtaryGharardadModels.get(position).getCcMoshtaryGharardad() +moshtaryGharardadModels.get(position).getNameSazmanForosh());
        switch (holder.getItemViewType()){
            case MOSHTARY_ZANGIREI:

                ((View_Holder_Zangirei) holder).bindZangireiMoshtary(moshtaryAddressModel,position,listener);
                break;

            case MOSHTARY_GHEIRE_ZANGIREI:

                ((View_Holder_Gheire_Zangireii) holder).bindGheireZangireii(moshtaryAddressModel,position,listener);
                break;

        }
    }





    @Override
    public int getItemCount() {
        return moshtaryAddressModels.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (moshtaryGharardadModels.get(position).getCcMoshtaryGharardad()!= 0) {
            return MOSHTARY_ZANGIREI;
        } else {
            return MOSHTARY_GHEIRE_ZANGIREI;
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public class View_Holder_Gheire_Zangireii extends RecyclerView.ViewHolder {
        private SwipeLayout swipeLayout;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private RelativeLayout layShowLocation;
        private RelativeLayout layChangeLocation;
        private RelativeLayout layChangeCustomerInfo;
        private RelativeLayout layShowCustomerInfo;
        private RelativeLayout layUpdateEtebar;
        private TextView lblRadif;
        private TextView lblCustomerFullNameCode;
        private TextView lblCustomerAddress;

        public View_Holder_Gheire_Zangireii(View view) {
            super(view);
            swipeLayout = itemView.findViewById(R.id.swipe);
            layStatusRight = view.findViewById(R.id.layStatusRight);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);
            layChangeCustomerInfo = view.findViewById(R.id.layChangeCustomerInfo);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerFullNameCode = view.findViewById(R.id.lblCustomerFullNameCode);
            lblCustomerAddress = view.findViewById(R.id.lblAddress);

            layShowLocation = view.findViewById(R.id.layShowLocation);
            layChangeLocation = view.findViewById(R.id.layChangeLocation);
            layChangeCustomerInfo = view.findViewById(R.id.layChangeCustomerInfo);
            layShowCustomerInfo = view.findViewById(R.id.layShowCustomerInfo);
            layUpdateEtebar = view.findViewById(R.id.layUpdateEtebar);



        }




        public void bindGheireZangireii(MoshtaryAddressModel moshtaryAddressModel,int position,final OnItemClickListener listener) {

            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left,itemView.findViewById(R.id.layLeft));
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right,itemView.findViewById(R.id.layRight));

            String address = "";
            if (moshtaryAddressModels.get(position).getAddress() != null && !moshtaryAddressModels.get(position).getAddress().trim().equals("")) {
                address = moshtaryAddressModels.get(position).getAddress();
            }
            lblCustomerFullNameCode.setText(String.format("%1$s - %2$s", moshtaryModels.get(position).getCodeMoshtary(), moshtaryModels.get(position).getNameMoshtary()));
            lblCustomerAddress.setText(address);
            lblRadif.setText(String.valueOf(position + 1));

            //TODO
            layChangeLocation.setVisibility(moshtaryAddressModel.isExtraProp_HasLocation()==0?View.VISIBLE:View.GONE);
            Log.i("VisibilityGetttt", "bindGheireZangireii: "+layChangeLocation.getVisibility());

            if (arrayListNoeMorajeh.get(position) != 0) {
                if (arrayListNoeMorajeh.get(position) == 1) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                } else if (arrayListNoeMorajeh.get(position) == 2) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                } else if (arrayListNoeMorajeh.get(position) == 3) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                } else if (arrayListNoeMorajeh.get(position) == 4) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
                } else {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                }
            } else {
                layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
            }
            if (canUpdateCustomer) {
                layChangeCustomerInfo.setVisibility(View.VISIBLE);
            } else {
                layChangeCustomerInfo.setVisibility(View.GONE);
            }

            setAnimation(itemView, position);
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SELECT_CUSTOMER(), position);
                }
            });

            layShowLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SHOW_LOCATION(), position);
                    swipeLayout.close(true);
                }
            });

            layChangeLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_CHANGE_LOCATION(), position);
                    swipeLayout.close(true);
                }
            });

            layShowCustomerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO(), position);
                    swipeLayout.close(true);
                }
            });

            layChangeCustomerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO(), position);
                    swipeLayout.close(true);
                }
            });

            layUpdateEtebar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_UPDATE_CREDIT(), position);
                    swipeLayout.close(true);
                }
            });

        }
    }

    public class View_Holder_Zangirei extends RecyclerView.ViewHolder  {
        private SwipeLayout swipeLayout;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private RelativeLayout layShowLocation;
        private RelativeLayout layChangeLocation;
        private RelativeLayout layChangeCustomerInfo;
        private RelativeLayout layShowCustomerInfo;
        private RelativeLayout layUpdateEtebar;
        private TextView lblRadif;
        private TextView lblCustomerFullNameCode;
        private TextView lblCustomerAddress;

        private TextView txtShomarehGharardad;
        private TextView txtNameSazmanForosh;

        public View_Holder_Zangirei(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            layStatusRight = itemView.findViewById(R.id.layStatusRight);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);
            layChangeCustomerInfo = itemView.findViewById(R.id.layChangeCustomerInfo);
            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerFullNameCode = itemView.findViewById(R.id.lblCustomerFullNameCode);
            lblCustomerAddress = itemView.findViewById(R.id.lblAddress);

            layShowLocation = itemView.findViewById(R.id.layShowLocation);
            layChangeLocation = itemView.findViewById(R.id.layChangeLocation);
            layChangeCustomerInfo = itemView.findViewById(R.id.layChangeCustomerInfo);
            layShowCustomerInfo = itemView.findViewById(R.id.layShowCustomerInfo);
            layUpdateEtebar = itemView.findViewById(R.id.layUpdateEtebar);


            txtShomarehGharardad =itemView .findViewById(R.id.txtShomareGharardad);
            txtNameSazmanForosh=itemView .findViewById(R.id.txtNameSazmanForosh);


        }



        public void bindZangireiMoshtary(MoshtaryAddressModel moshtaryAddressModel,int position,final OnItemClickListener listener) {

            Log.i("moshtaryGharardadModels", "bindZangireiMoshtary: "+moshtaryGharardadModels.get(position).toString() +txtShomarehGharardad);
            txtShomarehGharardad.setText(moshtaryGharardadModels.get(position).getShomarehGharardad());
            txtNameSazmanForosh.setText(moshtaryGharardadModels.get(position).getNameSazmanForosh());

            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left,itemView.findViewById(R.id.layLeft));
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right,itemView.findViewById(R.id.layRight));

            String address = "";
            if (moshtaryAddressModels.get(position).getAddress() != null && !moshtaryAddressModels.get(position).getAddress().trim().equals("")) {
                address = moshtaryAddressModels.get(position).getAddress();
            }
            lblCustomerFullNameCode.setText(String.format("%1$s - %2$s", moshtaryModels.get(position).getCodeMoshtary(), moshtaryModels.get(position).getNameMoshtary()));
            lblCustomerAddress.setText(address);
            lblRadif.setText(String.valueOf(position + 1));

            //TODO
            layChangeLocation.setVisibility(moshtaryAddressModel.isExtraProp_HasLocation()==1?View.VISIBLE:View.GONE);



            if (arrayListNoeMorajeh.get(position) != 0) {
                if (arrayListNoeMorajeh.get(position) == 1) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                } else if (arrayListNoeMorajeh.get(position) == 2) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                } else if (arrayListNoeMorajeh.get(position) == 3) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                } else if (arrayListNoeMorajeh.get(position) == 4) {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
                } else {
                    layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                    layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                }
            } else {
                layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
            }
            if (canUpdateCustomer) {
                layChangeCustomerInfo.setVisibility(View.VISIBLE);
            } else {
                layChangeCustomerInfo.setVisibility(View.GONE);
            }


            setAnimation(itemView, position);
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "onClick: Custo");
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SELECT_CUSTOMER(), getAdapterPosition());
                }
            });

            layShowLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SHOW_LOCATION(),getAdapterPosition());
                    swipeLayout.close(true);
                }
            });

            layChangeLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_CHANGE_LOCATION(), getAdapterPosition());
                    swipeLayout.close(true);
                }
            });

            layShowCustomerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO(), getAdapterPosition());
                    swipeLayout.close(true);
                }
            });

            layChangeCustomerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO(), getAdapterPosition());
                    swipeLayout.close(true);
                }
            });

            layUpdateEtebar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_UPDATE_CREDIT(), getAdapterPosition());
                    swipeLayout.close(true);
                }
            });

        }
    }


    public interface OnItemClickListener {
        void onItemClick(int operation, int position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
