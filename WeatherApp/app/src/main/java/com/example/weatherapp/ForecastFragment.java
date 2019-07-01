package com.example.weatherapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.Adapter.WeatherForecastAdapter;
import com.example.weatherapp.Common.Common;
import com.example.weatherapp.Model.WeatherForecastResult;
import com.example.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.weatherapp.Retrofit.RetrofitClient;

import java.util.function.Consumer;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_city_name, txt_geo_coord;
    RecyclerView recycler_forecast;

    static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if (instance == null)
            instance = new ForecastFragment();
        return instance;
    }

    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

       txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
       txt_geo_coord = (TextView)itemView.findViewById(R.id.txt_geo_coords);

       recycler_forecast = (RecyclerView)itemView.findViewById(R.id.recycler_forecast);
       recycler_forecast.setHasFixedSize(true);
       recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));

       getForecastWeatherInformation();

       return itemView;
    }
    @Override
    public void onDestroyView() {

        compositeDisposable.clear();
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }



    private void getForecastWeatherInformation() {
        compositeDisposable.add(mService.getForecastWeatherByLatLng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                        Common.APP_ID,
                        "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new io.reactivex.functions.Consumer<WeatherForecastResult>(){
                    @Override
                    public void accept (WeatherForecastResult weatherForecastResult) throws Exception{
                        displayForecastWeather (weatherForecastResult);

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        Log.d("Error", ""+ throwable.getMessage());

                    }
                      }
                )
        );

    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {

        txt_city_name.setText(new StringBuilder(weatherForecastResult.city.name));
        txt_geo_coord.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherForecastResult);
        recycler_forecast.setAdapter(adapter);

    }

}
