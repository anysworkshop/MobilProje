package tr.edu.yildiz.mywardrobe;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String surName;
    private String email;
    private String password;
    private Bitmap image;
    private int userID;

    public User(){

    }

    public User(String name, String surName, String email, String password, Bitmap image) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(String name, String surName, String email, String password, Bitmap image, int userID) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.image = image;
        this.userID = userID;
    }

    protected User(Parcel in) {
        name = in.readString();
        surName = in.readString();
        email = in.readString();
        password = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(surName);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeParcelable(image, i);
        parcel.writeInt(userID);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
