package android.bignerdranch.com.criminalintent;

import java.util.UUID;

/**
 * Created by jakefost on 5/10/16.
 */
public class Crime {
    private UUID mId;
    private String mTitle;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Crime() {
        mId = UUID.randomUUID();

    }
}
