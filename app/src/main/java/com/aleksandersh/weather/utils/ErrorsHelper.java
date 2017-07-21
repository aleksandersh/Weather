package com.aleksandersh.weather.utils;

import android.support.annotation.StringRes;

import com.aleksandersh.weather.R;

/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Помогает передавать ошибки в приложении через коды.
 */

public class ErrorsHelper {
    /**
     * Нет соединения с интернетом.
     */
    public static final int ERROR_INTERNET_DISCONNECTED = 20;
    /**
     * Ошибка при обращении к серверу.
     */
    public static final int ERROR_HTTP_CONTACTING_SERVER = 21;
    /**
     * Ошибка при отправке запроса к серверу.
     */
    public static final int ERROR_HTTP_REQUEST_FAILED = 22;

    /**
     * Получает по коду ошибки идентификатор ресурса с ее описанием.
     *
     * @param code Код ошибки.
     * @return Идентификатор строкового ресурса. {@code 0}, если ресурс не определен.
     */
    @StringRes
    public static int getStringResourceByErrorCode(int code) {
        switch (code) {
            case 20:
                return R.string.error_internet_disconnected;
            case 21:
                return R.string.error_http_contacting_server;
            case 22:
                return R.string.error_http_request_failed;
        }
        return 0;
    }
}
