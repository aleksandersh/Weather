package com.aleksandersh.weather.network;


/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Обертка для возвращаемых из http-клиента моделей для возможности получения данных об ошибке.
 */

public class HttpClientResponse<T> {

    private T model;

    private boolean successful;

    private int errorCode;

    /**
     * Получение обработанных данных из сервиса.
     *
     * @return Данные из сервиса. Если при работе с сервисом возникли ошибки и данные получить
     * не удалось - возвращает {@code null}.
     */
    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    /**
     * Флаг обозначает успешность операции.
     *
     * @return Если операция выполнена успешно и модель можно получить возвращает {@code true},
     * иначе {@code false}.
     */
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
       this.successful = successful;
    }

    /**
     * Получить код ошибки.
     *
     * @return Код ошибки.
     */
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
       this.errorCode = errorCode;
    }

}
