package ru.mail.park.myapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mail.park.myapplication.model.CoinInfo;
import ru.mail.park.myapplication.utils.AppDelegate;

public class MainViewModel {
    private final MutableLiveData<Boolean> loadingData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private final MutableLiveData<List<CoinInfo>> coinsList = new MutableLiveData<>();

    public void onRefreshButtonClicked() {
        loadingData.setValue(true);
        isError.setValue(false);
        AppDelegate.getInstance().getApiService()
                .getCoinsList()
                .enqueue(new Callback<List<CoinInfo>>() {
                    @Override
                    public void onResponse(Call<List<CoinInfo>> call, Response<List<CoinInfo>> response) {
                        coinsList.setValue(response.body());
                        }
                        @Override
                        public void onFailure(Call<List<CoinInfo>> call, Throwable t) {
                            isError.setValue(true);
                        }
                });
    }


    public LiveData<Boolean> getLoadingData() {
        return loadingData;
    }

    public LiveData<List<CoinInfo>> getCoinsList() {
        return coinsList;
    }

    public MutableLiveData<Boolean> getIsError() {
        return isError;
    }
}
