package com.saphamrah.customer.utils



import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saphamrah.customer.base.BasePreferences
import java.io.File
import java.lang.reflect.Type


//class PreferencesImpl(name: String) : BasePreferences {
//
//    private val file = File(name).canonicalFile
//    private val mapType = object : TypeToken<HashMap<String, String>>() {}.type
//
//    override fun putString(key: String, value: String) {
//
//        val data: HashMap<String, String>
//        val readed = FileManager.readFileContent(file)
//        if (readed == "") {
//            data = hashMapOf(
//                Pair(key, value)
//            )
//        } else {
//            data = readed.fromJson(mapType)
//            data[key] = value
//        }
//
//        FileManager.writeToFile(file, data.toJson())
//
//    }
//
//    override fun getString(key: String, default: String): String {
//
//        val readed = FileManager.readFileContent(file)
//        val data: HashMap<String, String>
//        return try {
//            data = readed.fromJson(mapType)
//            data[key] ?: default
//        } catch (ex: Exception) {
//            default
//        }
//
//    }
//
//    override fun putInt(key: String, value: Int) {
//        val data: HashMap<String, Int>
//        val readed = FileManager.readFileContent(file)
//        if (readed == "") {
//            data = hashMapOf(
//                Pair(key, value)
//            )
//        } else {
//            data = readed.fromJson(mapType)
//            data[key] = value
//        }
//
//        FileManager.writeToFile(file, data.toJson())
//    }
//
//    override fun getInt(key: String, value: Int): Int {
//        val readed = FileManager.readFileContent(file)
//        val data: HashMap<String, Int>
//        return try {
//            data = readed.fromJson(mapType)
//            data[key] ?: value
//        } catch (ex: Exception) {
//            value
//        }
//    }
//
//    override fun clear() {
//
//        FileManager.writeToFile(file, "")
//
//    }
//
//     private fun <T> String.fromJson(type: Type): T {
//        return Gson().fromJson(this, type)
//    }
//
//     private fun Any.toJson(): String {
//        return Gson().toJson(this)
//    }
//}
