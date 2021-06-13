package tr.edu.yildiz.mywardrobe;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;

public class Clothes implements Parcelable {
    private int clothesID;
    private int part;
    private String type;
    private String color;
    private String pattern;
    private String date;
    private int price;
    private byte[] byteArrays;
    private int drawerID;

    public Clothes(){

    }

    public Clothes(int part, String type, String color, String pattern, String date, int price, byte[] byteArrays, int drawerID) {
        this.part = part;
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.date = date;
        this.price = price;
        this.byteArrays = byteArrays;
        this.drawerID = drawerID;
    }

    public Clothes(int clothesID, String part, String type, String color, String pattern, String date, int price, byte[] byteArrays, int drawerID) {
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.date = date;
        this.price = price;
        this.byteArrays = byteArrays;
        this.drawerID = drawerID;
        this.clothesID = clothesID;
    }

    protected Clothes(Parcel in) {
        clothesID = in.readInt();
        part = in.readInt();
        type = in.readString();
        color = in.readString();
        pattern = in.readString();
        date = in.readString();
        price = in.readInt();
        byteArrays = in.createByteArray();
        drawerID = in.readInt();
    }

    public static final Creator<Clothes> CREATOR = new Creator<Clothes>() {
        @Override
        public Clothes createFromParcel(Parcel in) {
            return new Clothes(in);
        }

        @Override
        public Clothes[] newArray(int size) {
            return new Clothes[size];
        }
    };

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getByteArrays() {
        return byteArrays;
    }

    public void setByteArrays(byte[] byteArrays) {
        this.byteArrays = byteArrays;
    }

    public int getDrawerID() {
        return drawerID;
    }

    public void setDrawerID(int drawerID) {
        this.drawerID = drawerID;
    }

    public int getClothesID() {
        return clothesID;
    }

    public void setClothesID(int clothesID) {
        this.clothesID = clothesID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(clothesID);
        parcel.writeInt(part);
        parcel.writeString(type);
        parcel.writeString(color);
        parcel.writeString(pattern);
        parcel.writeString(date);
        parcel.writeInt(price);
        parcel.writeByteArray(byteArrays);
        parcel.writeInt(drawerID);
    }
}
