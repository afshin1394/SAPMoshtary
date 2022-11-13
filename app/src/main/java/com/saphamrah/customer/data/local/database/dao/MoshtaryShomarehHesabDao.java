package com.saphamrah.customer.data.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.saphamrah.customer.data.local.database.entity.MoshtaryShomarehHesabTable;
import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;
import java.util.List;
import io.reactivex.Completable;

@Dao
public interface MoshtaryShomarehHesabDao {

    @Query("SELECT * FROM MoshtaryShomarehHesabTable")
    List<MoshtaryShomarehHesabTable> getAll();

    @Insert
    Completable insert(List<MoshtaryShomarehHesabTable> moshtary);


}
