package CoreClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class BasicDetails implements Parcelable {
    private int idUser;
    private String name;
    private int score;
    private String profilePic;




    public BasicDetails(int idUser,String name, int score,String profilePic)
    {
        this.idUser = idUser;
        this.name = name;
        this.score = score;
        this.profilePic = profilePic;
    }

    public BasicDetails(String name,String profilePic)
    {
        this.name = name;
        this.profilePic = profilePic;
    }

    public BasicDetails(Parcel in)
    {

        idUser = in.readInt();
        name = in.readString();
        score = in.readInt();
        profilePic = in.readString();

    }

    public static final Creator<BasicDetails> CREATOR = new Creator<BasicDetails>() {
        @Override
        public BasicDetails createFromParcel(Parcel in) {
            return new BasicDetails(in);
        }

        @Override
        public BasicDetails[] newArray(int size) {
            return new BasicDetails[size];
        }
    };

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getScoreString(){
        String temp="";
        temp+=this.score;
        return temp;
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
        parcel.writeInt(score);
        parcel.writeString(profilePic);
    }
}
