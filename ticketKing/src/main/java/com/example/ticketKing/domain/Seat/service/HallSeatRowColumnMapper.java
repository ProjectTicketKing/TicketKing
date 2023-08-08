package com.example.ticketKing.domain.Seat.service;

import java.util.HashMap;
import java.util.Map;

public class HallSeatRowColumnMapper {
    private static final Map<String, Map<String, Integer>> hallTypeRowMap = new HashMap<>();
    private static final Map<String, Map<String, Integer>> hallTypeColumnMap = new HashMap<>();

    static {
        // 각 행사장과 타입에 따른 로우 값과 컬럼 값을 맵에 저장
        Map<String, Integer> kspoRowMap = new HashMap<>();
        kspoRowMap.put("VIP", 24);
        kspoRowMap.put("R", 22);
        kspoRowMap.put("A", 20);

        Map<String, Integer> kspoColumnMap = new HashMap<>();
        kspoColumnMap.put("VIP", 20);
        kspoColumnMap.put("R", 20);
        kspoColumnMap.put("A", 26);

        Map<String, Integer> olysdmRowMap = new HashMap<>();
        olysdmRowMap.put("VIP", 25);
        olysdmRowMap.put("S", 20);
        olysdmRowMap.put("G", 25);

        Map<String, Integer> olysdmColumnMap = new HashMap<>();
        olysdmColumnMap.put("VIP", 35);
        olysdmColumnMap.put("S", 25);
        olysdmColumnMap.put("G", 35);

        // 행사장별 맵을 전체 맵에 저장
        hallTypeRowMap.put("KSPO", kspoRowMap);
        hallTypeRowMap.put("OLYSDM", olysdmRowMap);

        hallTypeColumnMap.put("KSPO", kspoColumnMap);
        hallTypeColumnMap.put("OLYSDM", olysdmColumnMap);
    }

    public static int getRow(String hall, String type) {
        // 맵에서 해당 행사장과 타입에 맞는 로우 값을 조회
        Map<String, Integer> rowMap = hallTypeRowMap.get(hall);
        if (rowMap != null) {
            Integer row = rowMap.get(type);
            if (row != null) {
                return row;
            }
        }
        // 일치하는 값이 없으면 기본값 10을 반환
        return 10;
    }

    public static int getColumn(String hall, String type) {
        // 맵에서 해당 행사장과 타입에 맞는 컬럼 값을 조회
        Map<String, Integer> columnMap = hallTypeColumnMap.get(hall);
        if (columnMap != null) {
            Integer column = columnMap.get(type);
            if (column != null) {
                return column;
            }
        }
        // 일치하는 값이 없으면 기본값 10을 반환
        return 10;
    }
}
