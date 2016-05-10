package android.bignerdranch.com.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jakefost on 5/10/16.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
