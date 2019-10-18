package test.foursquare.app.ui.splash

import androidx.lifecycle.ViewModel
import test.foursquare.app.model.GeneralRepository

class SplashViewModel(private val generalRepository: GeneralRepository) : ViewModel() {

    fun isFirstArrival(): Boolean {
        return generalRepository.isFirstArrival()
    }

    fun setFirstArrival(isFirstArrival: Boolean) {
        generalRepository.setFirstArrival(isFirstArrival)
    }
}