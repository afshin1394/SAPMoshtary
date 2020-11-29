package com.saphamrah.PubFunc;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.R;

public class ForoshandehMamorPakhshUtils
{

    public static final int FOROSHANDEH_SARD = 1;
    public static final int FOROSHANDEH_GARM = 2;
    public static final int FOROSHANDEH_SMART = 3;
    public static final int MAMOR_PAKHSH_SARD = 4;
    public static final int MAMOR_PAKHSH_SMART = 5;
    public static final int SARPARAST = 6;
    public static final int AMARGAR = 7;
    public static final int MODIR = 8;

    public int getNoeMasouliat(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        if (foroshandehMamorPakhshModel != null)
        {
            int AfradNoeMasouliat = foroshandehMamorPakhshModel.getNoeMasouliat();
            int ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
            int ccAfradModir = foroshandehMamorPakhshModel.getCcAfradModir();
            int NoeForoshandeh = foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh();
            String CodeForoshandeh = foroshandehMamorPakhshModel.getCodeForoshandeh();
            int NoeAfrad =0; // 1-Foroshandeh-Sard 2-Foroshandeh-Garm 3-Foroshandeh-Smart 4-MamorPakhsh-Sard 5-MamorPakhsh-Smart 6-SarparastForoshandeh(0098) 7-Amargar 8-Rees(0090)
            if(AfradNoeMasouliat == 1 & NoeForoshandeh==1)
                NoeAfrad=1;
            if(AfradNoeMasouliat == 1 & NoeForoshandeh==2)
                NoeAfrad=2;
            if(AfradNoeMasouliat == 1 & NoeForoshandeh==3)
                NoeAfrad=3;
            if(AfradNoeMasouliat == 2 & NoeForoshandeh==1)
                NoeAfrad=4;
            if(AfradNoeMasouliat == 2 & NoeForoshandeh==3)
                NoeAfrad=5;
           // if(AfradNoeMasouliat==1 & ccAfrad==ccAfradModir & CodeForoshandeh.substring(2).equals("98"))
            if(AfradNoeMasouliat == 1 && NoeForoshandeh == 5)
                NoeAfrad=6;
            if(AfradNoeMasouliat==3 & NoeForoshandeh==1)
                NoeAfrad=7;
            if (AfradNoeMasouliat == 1 && NoeForoshandeh == 6)
                NoeAfrad = 8;

            return NoeAfrad;
        }
        else
        {
            return -1;
        }
    }


    public int getNoeForoshandehMamorPakhsh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int NoeForoshandehMamorPakhsh = getNoeMasouliat(foroshandehMamorPakhshModel);
        switch(NoeForoshandehMamorPakhsh)
        {
            case 1:
                return R.string.foroshandehSard;
            case 2:
                return R.string.foroshandehGarm;
            case 3:
                return R.string.foroshandehHoshmand;
            case 4:
                return R.string.mamorPakhshSard;
            case 5:
                return R.string.mamorPakhshHoshmand;
            case 6:
                return R.string.sarparast;
            case 7:
                return R.string.amargar;
            case 8:
                return R.string.modir;
            default:
                return -1;
        }
    }

}
