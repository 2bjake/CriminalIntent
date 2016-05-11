package android.bignerdranch.com.criminalintent;

import android.bignerdranch.com.criminalintent.CrimeDbSchema.CrimeTable.Cols;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jakefost on 5/11/16.
 */
public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        long date = getLong(getColumnIndex(Cols.DATE));
        int isSolved = getInt(getColumnIndex(Cols.SOLVED));
        String suspect = getString(getColumnIndex(Cols.SUSPECT));

        return new Crime(
                UUID.fromString(uuidString),
                title,
                new Date(date),
                isSolved != 0,
                suspect);
    }
}
