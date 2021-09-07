package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ElamMarjoeeForoshandehDAO;
import com.saphamrah.DAO.ElamMarjoeePPCDAO;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.Model.ElamMarjoeePPCModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElamMarjoeeForoshandehRepository {

    Context context;
    ElamMarjoeeForoshandehDAO elamMarjoeeForoshandehDAO;


    public ElamMarjoeeForoshandehRepository(Context context) {
        this.context = context;
        elamMarjoeeForoshandehDAO = new ElamMarjoeeForoshandehDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> elamMarjoeeForoshandehDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        return () -> elamMarjoeeForoshandehDAO.insertGroup(elamMarjoeeForoshandehModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        return RxAsync.makeObservable(insertGroupCallable(elamMarjoeeForoshandehModels))
                .subscribeOn(Schedulers.io());
    }
}
