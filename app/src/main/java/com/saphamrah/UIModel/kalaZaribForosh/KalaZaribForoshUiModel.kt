package com.saphamrah.UIModel.kalaZaribForosh

import kotlin.properties.Delegates

class KalaZaribForoshUiModel {
    var CodeKala: Int by Delegates.notNull()
    lateinit var NameKala: String
    var ZaribForosh: Int by Delegates.notNull()
}