package ru.mail.park.myapplication.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.mail.park.myapplication.model.CoinInfo;

import java.util.List;

public interface Api {

    @GET("ticker/?limit=20")
    Call<List<CoinInfo>> getCoinsList();
}
