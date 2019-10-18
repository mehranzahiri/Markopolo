package test.foursquare.app.model

import test.foursquare.app.model.localData.SharedPrefProvider

/** This repository provide any kind of information that related to app
like check update or save token ...
 **/

class GeneralRepository private constructor(private val sharedPrefProvider: SharedPrefProvider) {

    companion object {
        @Volatile
        private var instance: GeneralRepository? = null

        fun getInstance(sharedPrefProvider: SharedPrefProvider) =
            instance ?: synchronized(this) {
                instance ?: GeneralRepository(sharedPrefProvider)
                    .also { instance = it }
            }
    }

    fun setFirstArrival(isFirstArrival: Boolean) {
        sharedPrefProvider.setFirtArrival(isFirstArrival)
    }

    fun isFirstArrival(): Boolean {
        return sharedPrefProvider.isFirstArrival()
    }
}