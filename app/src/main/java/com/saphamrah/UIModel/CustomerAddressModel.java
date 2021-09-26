package com.saphamrah.UIModel;

import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;

import java.util.ArrayList;

public class CustomerAddressModel
{

    private MoshtaryModel moshtaryModel;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel;
    private MoshtaryGharardadModel moshtaryGharardadModel;


    public MoshtaryModel getMoshtaryModel() {
        return moshtaryModel;
    }

    public void setMoshtaryModel(MoshtaryModel moshtaryModel) {
        this.moshtaryModel = moshtaryModel;
    }

    public ArrayList<MoshtaryAddressModel> getMoshtaryAddressModels() {
        return moshtaryAddressModels;
    }

    public void setMoshtaryAddressModels(ArrayList<MoshtaryAddressModel> moshtaryAddressModels) {
        this.moshtaryAddressModels = moshtaryAddressModels;
    }


    public MoshtaryMorajehShodehRoozModel getMoshtaryMorajehShodehRoozModel() {
        return moshtaryMorajehShodehRoozModel;
    }

    public void setMoshtaryMorajehShodehRoozModel(MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel) {
        this.moshtaryMorajehShodehRoozModel = moshtaryMorajehShodehRoozModel;
    }

    public MoshtaryGharardadModel getMoshtaryGharardadModel() {
        return moshtaryGharardadModel;
    }

    public void setMoshtaryGharardadModel(MoshtaryGharardadModel moshtaryGharardadModel) {
        this.moshtaryGharardadModel = moshtaryGharardadModel;
    }

    @Override
    public String toString() {
        return "CustomerAddressModel{" +
                "moshtaryModel=" + moshtaryModel +
                ", moshtaryAddressModels=" + moshtaryAddressModels +
                ", moshtaryMorajehShodehRoozModel=" + moshtaryMorajehShodehRoozModel +
                ", moshtaryGharardadModel=" + moshtaryGharardadModel +
                '}';
    }
}
