package com.ksy.serversetting.controller.converter;

import com.ksy.serversetting.domain.HotelRoomNumber;
import org.springframework.core.convert.converter.Converter;

//문자열을 객체로 변경
public class HotelRoomNumberConverter implements Converter<String, HotelRoomNumber> {

   @Override
    public HotelRoomNumber convert(String source) {
       return HotelRoomNumber.parse(source);
   }
}
