package com.aleksandersh.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.preference.PreferenceManager;

import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.model.WeatherRequest;
import com.aleksandersh.weather.network.httpClient.HttpClientResponse;
import com.aleksandersh.weather.network.httpClient.OpenWeatherMapHttpClient;
import com.aleksandersh.weather.network.httpClient.WeatherHttpClient;
import com.aleksandersh.weather.storage.CurrentWeatherStorage;
import com.aleksandersh.weather.storage.WeatherStorableState;
import com.aleksandersh.weather.storage.WeatherStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class WeatherProvider {
    private static final String TAG = "WeatherProvider";
    private static WeatherProvider weatherProvider;

    private List<WeatherSubscriber> mSubscribers = new ArrayList<>();
    private Context mContext;
    private WeatherHttpClient mHttpClient;
    private WeatherStorage mStorage;
    private UpdatingWeatherTask mUpdatingWeatherTask;
    private LoadingStoredStateTask mLoadingStoredStateTask;

    public static WeatherProvider get(Context context) {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
            weatherProvider.mContext = context;
            weatherProvider.mStorage = new CurrentWeatherStorage(context);
            weatherProvider.mHttpClient = new OpenWeatherMapHttpClient();
        }
        return weatherProvider;
    }

    private WeatherProvider() {
    }

    /**
     * Подписка на обновление погоды.
     *
     * @param subscriber Объект, которому будут передаваться обновления.
     */
    public void subscribe(WeatherSubscriber subscriber) {
        mSubscribers.add(subscriber);
    }

    /**
     * Отписка от обновлений погоды. Удаляет ссылки на подписчика.
     *
     * @param subscriber Отписываемый объект.
     */
    public void unsubscribe(WeatherSubscriber subscriber) {
        mSubscribers.remove(subscriber);
    }

    public void requestStoredState() {
        if (mLoadingStoredStateTask != null &&
                mLoadingStoredStateTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            return;
        }
        mLoadingStoredStateTask = new LoadingStoredStateTask();
        mLoadingStoredStateTask.execute();
    }

    /**
     * Запрашивает обновление погоды. По результату у подписчиков вызывается метод
     * {@link WeatherSubscriber#onWeatherUpdated(Weather, WeatherRequest)} в случае успеха или
     * {@link WeatherSubscriber#onErrorUpdating(String)} в случае ошибки. Метод предусмотрен для компонентов,
     * не имеющих информации о местоположении, например сервисов.
     */
    public void requestWeatherUpdate() {
        requestWeather(getCityIdPreference());
    }

    /**
     * Запрашивает погоду по идентификатору города. По результату у подписчиков вызывается метод
     * {@link WeatherSubscriber#onWeatherUpdated(Weather, WeatherRequest)} в случае успеха или
     * {@link WeatherSubscriber#onErrorUpdating(String)} в случае ошибки. Метод предусмотрен для компонентов,
     * имеющих информацию о местоположении.
     *
     * @param cityId Идентификатор города.
     */
    public void requestWeather(long cityId) {
        WeatherRequest request = obtainRequest(cityId);
        requestWeatherByQuery(request);
    }

    private void requestWeatherByQuery(WeatherRequest request) {
        if (mUpdatingWeatherTask != null &&
                mUpdatingWeatherTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            WeatherRequest processRequest = mUpdatingWeatherTask.getRequest();
            if (request.equals(processRequest)) {
                // Если выполняется актуальная задача, новую можно не запускать.
                return;
            } else {
                // Если задача неактуальна, ее можно отменить.
                mUpdatingWeatherTask.cancel(true);
            }
        }

        mUpdatingWeatherTask = new UpdatingWeatherTask(request);
        mUpdatingWeatherTask.execute();
    }

    private void onWeatherUpdated(Weather weather, WeatherRequest request) {
        WeatherStorableState storableState = new WeatherStorableState(weather, request, new Date());

        // Оповещение подписчиков.
        for (WeatherSubscriber subscriber : mSubscribers) {
            subscriber.onWeatherUpdated(storableState);
        }

        // Запуск задачи на сохранение данных.
        SavingStateTask savingStateTask = new SavingStateTask();
        savingStateTask.execute(storableState);
    }

    private void onErrorUpdating(String errorDescription) {
        for (WeatherSubscriber subscriber : mSubscribers) {
            subscriber.onErrorUpdating(errorDescription);
        }
    }

    private void onStoredStateLoaded(WeatherStorableState storableState) {
        WeatherRequest request = obtainRequest(getCityIdPreference());
        if (request.equals(storableState.getRequest())) {
            for (WeatherSubscriber subscriber : mSubscribers) {
                subscriber.onStoredStateLoaded(storableState);
            }
        }
    }

    private void onErrorStoredStateLoading() {
        for (WeatherSubscriber subscriber : mSubscribers) {
            subscriber.onErrorStoredStateLoading();
        }
    }

    private WeatherRequest obtainRequest(long cityId) {
        String lang = getLanguageValue();
        String units = getUnitsPreference();
        return new WeatherRequest(units, lang, cityId);
    }

    private String getLanguageValue() {
        return mContext.getString(R.string.api_language_value);
    }

    private String getUnitsPreference() {
        String unitsKey = mContext.getString(R.string.pref_units_key);
        String unitsDefault = mContext.getString(R.string.pref_units_default);
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(unitsKey, unitsDefault);
    }

    private long getCityIdPreference() {
        String cityIdKey = mContext.getString(R.string.pref_last_city_id_key);
        long cityIdDefault = Long.parseLong(mContext.getString(R.string.pref_last_city_id_default));
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getLong(cityIdKey, cityIdDefault);
    }

    private class UpdatingWeatherTask extends AsyncTask<Void, Void, HttpClientResponse<Weather>> {
        private final WeatherRequest mRequest;

        public UpdatingWeatherTask(WeatherRequest request) {
            mRequest = request;
        }

        @Override
        protected HttpClientResponse<Weather> doInBackground(Void... voids) {
            HttpClientResponse<Weather> response;
            if (isNetworkAvailable()) {
                response = mHttpClient.getCurrentWeatherByCityId(
                        mRequest.getLang(),
                        mRequest.getUnits(),
                        mRequest.getCityId());
            } else {
                response = new HttpClientResponse<>();
                response.setSuccessful(false);
                response.setErrorDescription("Network not available.");
            }

            return response;
        }

        @Override
        protected void onPostExecute(HttpClientResponse<Weather> weatherHttpClientResponse) {
            if (weatherHttpClientResponse.isSuccessful()) {
                onWeatherUpdated(weatherHttpClientResponse.getModel(), mRequest);
            } else {
                onErrorUpdating(weatherHttpClientResponse.getErrorDescription());
            }
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager manager = (ConnectivityManager)
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manager.getActiveNetworkInfo() != null
                    && manager.getActiveNetworkInfo().isConnected();
        }

        public WeatherRequest getRequest() {
            return mRequest;
        }
    }

    private class SavingStateTask extends AsyncTask<WeatherStorableState, Void, Void> {
        @Override
        protected Void doInBackground(WeatherStorableState... storedStates) {
            if (storedStates.length == 0) {
                throw new IllegalArgumentException("There is no state to save.");
            }

            mStorage.saveState(storedStates[0]);

            return null;
        }
    }

    private class LoadingStoredStateTask extends AsyncTask<Void, Void, WeatherStorableState> {
        @Override
        protected WeatherStorableState doInBackground(Void... voids) {
            return mStorage.loadState();
        }

        @Override
        protected void onPostExecute(WeatherStorableState storableState) {
            if (storableState != null) {
                onStoredStateLoaded(storableState);
            } else {
                onErrorStoredStateLoading();
            }
        }
    }

    public interface WeatherSubscriber {
        void onWeatherUpdated(WeatherStorableState storableState);

        void onStoredStateLoaded(WeatherStorableState storableState);

        void onErrorUpdating(String errorDescription);

        void onErrorStoredStateLoading();
    }
}
