package ua.opu.pnit.mynotepad.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ua.opu.pnit.mynotepad.repository.AppRepository;

public class SplashViewModel extends AndroidViewModel {

    private AppRepository repository;

    private MutableLiveData<Boolean> authResult = new MutableLiveData<>();

    public LiveData<Boolean> getAuthResultLiveData() {
        return authResult;
    }

    public SplashViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application);
    }

    public void checkToken() {
        repository.checkToken(authResult);
    }
}
