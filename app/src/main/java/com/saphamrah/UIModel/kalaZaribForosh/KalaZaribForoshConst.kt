package com.saphamrah.UIModel.kalaZaribForosh

import android.database.Cursor
import java.util.ArrayList

const val CodeKala = "CodeKala"
const val NameKala = "NameKala"
const val ZaribForosh = "ZaribForosh"

 fun cursorToModel(cursor: Cursor): ArrayList<KalaZaribForoshUiModel> {
    val models = ArrayList<KalaZaribForoshUiModel>()
    cursor.moveToFirst()
    while (!cursor.isAfterLast) {
        val model = KalaZaribForoshUiModel()
        model.CodeKala = cursor.getInt(cursor.getColumnIndex(CodeKala))
        model.NameKala = cursor.getString(cursor.getColumnIndex(NameKala))
        model.ZaribForosh = cursor.getInt(cursor.getColumnIndex(ZaribForosh))
        models.add(model)
        cursor.moveToNext()
    }
    return models
}