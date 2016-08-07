package com.compiler.abohaoya.service;

import com.compiler.abohaoya.pojo.CurrentWeatherResponse;
import com.compiler.abohaoya.pojo.WeatherForecaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by User on 8/4/2016.
 */
public interface WeatherServiceApi {
    @GET
    Call<CurrentWeatherResponse> getAllWeather(@Url String userUrl);
    @GET
    Call<WeatherForecaseResponse> getAllWeatherForecast(@Url String url);
}
