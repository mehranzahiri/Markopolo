package test.foursquare.app.model

import test.foursquare.app.model.localData.SharedPrefProvider

class AvenueRepository private constructor(private val sharedPrefProvider: SharedPrefProvider) {

    companion object {
        @Volatile
        private var instance: AvenueRepository? = null

        fun getInstance(sharedPrefProvider: SharedPrefProvider) =
            instance ?: synchronized(this) {
                instance ?: AvenueRepository(sharedPrefProvider).also { instance = it }
            }
    }
}