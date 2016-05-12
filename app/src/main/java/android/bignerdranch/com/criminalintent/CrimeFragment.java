package android.bignerdranch.com.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jakefost on 5/10/16.
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;
    
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDateButton();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.crime_time);
        updateTimeButton();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_DATE) {
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(copyDatePortion(date, mCrime.getDate()));
                updateDateButton();
            } else if (requestCode == REQUEST_TIME) {
                int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, -1);
                int minute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE, -1);
                if (hour != -1 && minute != -1) {
                    mCrime.setDate(setTimePortion(hour, minute, mCrime.getDate()));
                    updateTimeButton();
                }
            }
        }
    }

    private void updateDateButton() {
        String dateStr = DateFormat.getLongDateFormat(getActivity()).format(mCrime.getDate());
        mDateButton.setText(dateStr);
    }

    private void updateTimeButton() {
        String timeStr = DateFormat.getTimeFormat(getActivity()).format(mCrime.getDate());
        mTimeButton.setText(timeStr);
    }

    private Date copyDatePortion(Date src, Date dest) {

        Calendar srcCal = Calendar.getInstance();
        srcCal.setTime(src);
        int year = srcCal.get(Calendar.YEAR);
        int month = srcCal.get(Calendar.MONTH);
        int day = srcCal.get(Calendar.DAY_OF_MONTH);

        Calendar destCal = Calendar.getInstance();
        destCal.setTime(dest);
        destCal.set(Calendar.YEAR, year);
        destCal.set(Calendar.MONTH, month);
        destCal.set(Calendar.DAY_OF_MONTH, day);
        return destCal.getTime();
    }

    private Date setTimePortion(int hour, int minute, Date dest) {
        Calendar destCal = Calendar.getInstance();
        destCal.setTime(dest);
        destCal.set(Calendar.HOUR_OF_DAY, hour);
        destCal.set(Calendar.MINUTE, minute);
        return destCal.getTime();
    }
}
