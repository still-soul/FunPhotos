package ztk.com.demo.funphotos.bean;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class Photo implements Serializable {
    private String mediaName;
    private String thumbMagic;
    private String mediaPath;
    private String time;
    private int isGif;
    private boolean isCheck;//可选

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
                ", isCheck=" + isCheck +
                '}';
    }
}
