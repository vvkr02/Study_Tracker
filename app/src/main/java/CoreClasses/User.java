package CoreClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int idUser;
    private String username;
    private String password;
    private String name;
    private int score;
    private String profilePic;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }


    public User(int idUser,String username,String password,String name, int score,String profilePic)
    {
        this.idUser = idUser;
        this.name = name;
        this.username = username;
        this.password = password;
        this.score = score;
        this.profilePic = profilePic;
    }

    public User(String username,String password,String name,String profilePic)
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
    }

    public User(Parcel in)
    {

        idUser = in.readInt();
        name = in.readString();
        username = in.readString();
        password = in.readString();
        score = in.readInt();
        profilePic = in.readString();

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(idUser);
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeInt(score);
        parcel.writeString(profilePic);
    }
}
