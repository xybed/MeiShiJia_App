package com.mumu.meishijia.model.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 77 on 2018/10/22 0022.
 * 收货地址实体类
 */

public class ReceivingAddress implements Parcelable{
    private Integer id;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String address;

    private Integer type;

    protected ReceivingAddress(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        phone = in.readString();
        province = in.readString();
        city = in.readString();
        address = in.readString();
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
    }

    public static final Creator<ReceivingAddress> CREATOR = new Creator<ReceivingAddress>() {
        @Override
        public ReceivingAddress createFromParcel(Parcel in) {
            return new ReceivingAddress(in);
        }

        @Override
        public ReceivingAddress[] newArray(int size) {
            return new ReceivingAddress[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(address);
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
    }
}
