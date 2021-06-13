package tr.edu.yildiz.mywardrobe;

import android.os.Parcel;
import android.os.Parcelable;

public class Drawer implements Parcelable {
    private int DrawerID;
    private int UserID;
    private String name;

    public Drawer() {
    }

    public Drawer(int userID, String name) {
        UserID = userID;
        this.name = name;
    }

    public Drawer(int drawerID, int userID, String name) {
        DrawerID = drawerID;
        UserID = userID;
        this.name = name;
    }

    protected Drawer(Parcel in) {
        DrawerID = in.readInt();
        UserID = in.readInt();
        name = in.readString();
    }

    public static final Creator<Drawer> CREATOR = new Creator<Drawer>() {
        @Override
        public Drawer createFromParcel(Parcel in) {
            return new Drawer(in);
        }

        @Override
        public Drawer[] newArray(int size) {
            return new Drawer[size];
        }
    };

    public int getDrawerID() {
        return DrawerID;
    }

    public void setDrawerID(int drawerID) {
        DrawerID = drawerID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(DrawerID);
        parcel.writeInt(UserID);
        parcel.writeString(name);
    }
}
