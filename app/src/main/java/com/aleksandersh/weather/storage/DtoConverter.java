package com.aleksandersh.weather.storage;


/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Интерфейс конвертеров объектов передачи данных в используемую в приложении модель.
 */

public interface DtoConverter<TRANSFERABLE extends Dto, STORABLE> {

    STORABLE convert(TRANSFERABLE dto);

}
