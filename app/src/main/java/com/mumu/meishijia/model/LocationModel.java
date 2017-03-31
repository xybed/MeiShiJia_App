package com.mumu.meishijia.model;

/**
 * 定位的model，数据从定位后的BDLocation中获得
 * Created by 7mu on 15/8/18.
 */
public class LocationModel {

    private String dataProvide="";//数据提供者
    private String city="";//城市
    private double lat;//纬度
    private double lng;//经度
    private String province="";//省份
    private float radius;//定位精度
    private float direction;//行进方向
    private String addrStr;//详细地址信息
    private String buildingName;//建筑物名字，目前只在百度支持室内定位的地方有返回，默认null
    private String country;//国家
    private String district;//区/县信息
    private String floor;//楼层信息，目前只在百度支持室内定位的地方有返回，默认null
    private String street;//街道信息
    private String streetNumber;//街道号码


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDataProvide() {
        return dataProvide;
    }

    public void setDataProvide(String dataProvide) {
        this.dataProvide = dataProvide;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
