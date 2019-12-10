package ztk.com.demo.funphotos.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhaotk
 */
public class Photo implements Parcelable {
    private String mediaName;
    private String thumbMagic;
    private String mediaPath;
    private String time;
    private int isGif;
    private boolean isCheck;
    private boolean isSmallCheck;


    public Photo() {
    }

    protected Photo(Parcel in) {
        mediaName = in.readString();
        thumbMagic = in.readString();
        mediaPath = in.readString();
        time = in.readString();
        isGif = in.readInt();
        isCheck = in.readByte() != 0;
        isSmallCheck = in.readByte() != 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public boolean isSmallCheck() {
        return isSmallCheck;
    }

    public void setSmallCheck(boolean smallCheck) {
        isSmallCheck = smallCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getThumbMagic() {
        return thumbMagic;
    }

    public void setThumbMagic(String thumbMagic) {
        this.thumbMagic = thumbMagic;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsGif() {
        return isGif;
    }

    public void setIsGif(int isGif) {
        this.isGif = isGif;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mediaName='" + mediaName + '\'' +
                ", thumbMagic='" + thumbMagic + '\'' +
                ", mediaPath='" + mediaPath + '\'' +
                ", time='" + time + '\'' +
                ", isGif=" + isGif +
                ", isSmallCheck=" + isSmallCheck +
                ", isCheck=" + isCheck +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mediaName);
        parcel.writeString(thumbMagic);
        parcel.writeString(mediaPath);
        parcel.writeString(time);
        parcel.writeInt(isGif);
        parcel.writeByte((byte) (isCheck ? 1 : 0));
        parcel.writeByte((byte) (isSmallCheck ? 1 : 0));
    }
}
