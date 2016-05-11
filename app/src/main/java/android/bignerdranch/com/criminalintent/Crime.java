package android.bignerdranch.com.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jakefost on 5/10/16.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private String mSuspect;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public Crime(UUID id, String title, Date date, boolean isSolved, String suspect) {
        mId = id;
        mTitle = title;
        mDate = date;
        mSolved = isSolved;
        mSuspect = suspect;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
