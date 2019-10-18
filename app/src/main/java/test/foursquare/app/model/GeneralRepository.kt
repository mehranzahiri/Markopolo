package test.foursquare.app.model

import test.foursquare.app.model.localData.SharedPrefProvider

/** This repository provide any kind of information that related to app
like check update or save token ...
 **/

class GeneralRepository (private val sharedPrefProvider: SharedPrefProvider) {

    fun setFirstArrival(isFirstArrival: Boolean) {
        sharedPrefProvider.setFirstArrival(isFirstArrival)
    }

    fun isFirstArrival(): Boolean {
        return sharedPrefProvider.isFirstArrival()
    }

//    todo check for update in next version
//    fun checkVersion{...}

//    todo save token in next version
//    fun saveToken{...}


}