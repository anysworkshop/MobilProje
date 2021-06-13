package tr.edu.yildiz.mywardrobe;

import android.os.Parcel;
import android.os.Parcelable;

public class Combination implements Parcelable {
    private int combinationID;
    private int userID;
    private String name;
    private int head;
    private int face;
    private int top;
    private int bottom;
    private int footwear;

    public Combination(){

    }

    public Combination(int combinationID, int userID, String name, int head, int face, int top, int bottom, int footwear) {
        this.combinationID = combinationID;
        this.userID = userID;
        this.name = name;
        this.head = head;
        this.face = face;
        this.top = top;
        this.bottom = bottom;
        this.footwear = footwear;
    }

    protected Combination(Parcel in) {
        combinationID = in.readInt();
        userID = in.readInt();
        name = in.readString();
        head = in.readInt();
        face = in.readInt();
        top = in.readInt();
        bottom = in.readInt();
        footwear = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(combinationID);
        dest.writeInt(userID);
        dest.writeString(name);
        dest.writeInt(head);
        dest.writeInt(face);
        dest.writeInt(top);
        dest.writeInt(bottom);
        dest.writeInt(footwear);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Combination> CREATOR = new Creator<Combination>() {
        @Override
        public Combination createFromParcel(Parcel in) {
            return new Combination(in);
        }

        @Override
        public Combination[] newArray(int size) {
            return new Combination[size];
        }
    };

    public int getCombinationID() {
        return combinationID;
    }

    public void setCombinationID(int combinationID) {
        this.combinationID = combinationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getFootwear() {
        return footwear;
    }

    public void setFootwear(int footwear) {
        this.footwear = footwear;
    }
}
