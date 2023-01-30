package com.ksy.serversetting.controller.formatter;

import com.ksy.serversetting.domain.HotelRoomNumber;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class HotelRoomNumberFormatter implements Formatter<HotelRoomNumber> {

    @Override
    public HotelRoomNumber parse(String text, Locale locale) throws ParseException {
        return HotelRoomNumber.parse(text);
    }

    @Override
    public String print(HotelRoomNumber object, Locale locale) {
        return object.toString();
    }
}
