package com.itstime.allpasstival.domain.dto;

public class FestivalDto {
    private Integer festivalID;
    private String festivalName;//축제이름VARCHAR
    private String holdingVenue;//개최장소VARCHAR
    private String startDate;//시작일시DATE
    private String finishDate;//종료일시DATE
    private String hostInst;//주관기관VARCHAR
    private String hostOrg;//주최기관VARCHAR
    private String telNum;//전화번호VARCHAR
    private String homepAddr;//홈페이지 주소VARCHAR
    private String streetAddr;//도로명 주소VARCHAR
    private Integer view;//조회수
    private String etc;//비고TEXT
    private String author;
}
