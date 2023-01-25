package com.ksy.serversetting.domain;

import lombok.Getter;

@Getter
public class HotelRoomNumber {

    // ex 'ksy-123' = 'code-roomNo'
    private static final String DELIMITER = "-";

    private String buildingCode;
    private Long roomNumber;

    public HotelRoomNumber(String buildingCode, Long roomNumber) {
        this.buildingCode = buildingCode;
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "HotelRoomNumber{" +
                "buildingCode='" + buildingCode + '\'' +
                ", roomNumber=" + roomNumber +
                '}';
    }

    public static final HotelRoomNumber parse(String roomNumberId) {

        String[] tokens = roomNumberId.split(DELIMITER); // [code,roomNumber]

        if(tokens.length != 2) {
            throw new IllegalArgumentException("invalid roomNo format");
        }

        return new HotelRoomNumber(tokens[0], Long.parseLong(tokens[1]));
    }
}
