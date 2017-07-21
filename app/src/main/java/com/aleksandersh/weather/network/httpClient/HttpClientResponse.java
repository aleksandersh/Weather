package com.aleksandersh.weather.network.httpClient;

/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Обертка для возвращаемых из http-клиента моделей для возможности получения данных об ошибке.
 */

public class HttpClientResponse<T> {
    private T mModel;
    private boolean mSuccessful;
    private int mErrorCode;

    /**
     * Получение обработанных данных из сервиса.
     *
     * @return Данные из сервиса. Если при работе с сервисом возникли ошибки и данные получить
     * не удалось - возвращает {@code null}.
     */
    public T getModel() {
        return mModel;
    }

    public void setModel(T model) {
        mModel = model;
    }

    /**
     * Флаг обозначает успешность операции.
     *
     * @return Если операция выполнена успешно и модель можно получить возвращает {@code true},
     * иначе {@code false}.
     */
    public boolean isSuccessful() {
        return mSuccessful;
    }

    public void setSuccessful(boolean successful) {
        mSuccessful = successful;
    }

    /**
     * Получить код ошибки.
     *
     * @return Код ошибки.
     */
    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }
}
