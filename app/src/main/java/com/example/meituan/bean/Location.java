package com.example.meituan.bean;

import java.util.List;

public class Location {
    private List<hotCity> hotCityList;
    private List<city> cityList;
    private List<String> pos;
    private String strategy;

    public List<hotCity> getHotCityList() {
        return hotCityList;
    }

    public void setHotCityList(List<hotCity> hotCityList) {
        this.hotCityList = hotCityList;
    }

    public List<city> getCityList() {
        return cityList;
    }

    public void setCityList(List<city> cityList) {
        this.cityList = cityList;
    }

    public List<String> getPos() {
        return pos;
    }

    public void setPos(List<String> pos) {
        this.pos = pos;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public static class hotCity {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class city {
        private int id;
        private String name;
        private String rank;
        private String pinyin;
        private double lng;
        private double lat;
        private String divisionStr;
        private boolean isOpen;
        private boolean weather;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getDivisionStr() {
            return divisionStr;
        }

        public void setDivisionStr(String divisionStr) {
            this.divisionStr = divisionStr;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public boolean isWeather() {
            return weather;
        }

        public void setWeather(boolean weather) {
            this.weather = weather;
        }

        public int getType() {
            return 0;
        }
    }
}
