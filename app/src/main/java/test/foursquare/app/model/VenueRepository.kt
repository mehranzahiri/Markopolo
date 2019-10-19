package test.foursquare.app.model

import test.foursquare.app.model.localData.SharedPrefProvider

class VenueRepository (private val sharedPrefProvider: SharedPrefProvider) {

//    todo  instead of here, use singelton in kodein-> global activity
//    companion object {
//        @Volatile
//        private var instance: AvenueRepository? = null
//
//        fun getInstance(sharedPrefProvider: SharedPrefProvider) =
//            instance ?: synchronized(this) {
//                instance ?: AvenueRepository(sharedPrefProvider).also { instance = it }
//            }
//    }
}