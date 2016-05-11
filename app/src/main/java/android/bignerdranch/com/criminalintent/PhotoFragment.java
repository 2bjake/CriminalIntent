package android.bignerdranch.com.criminalintent;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

/**
 * Created by jakefost on 5/11/16.
 */
public class PhotoFragment extends DialogFragment {
    private static final String ARG_CRIME_ID = "id";
    ImageView mPhotoView;
    UUID mId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_photo, null);

        mPhotoView = (ImageView) v.findViewById(R.id.photo_view);
        updatePhotoView();

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    public static PhotoFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, id);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updatePhotoView() {
        CrimeLab cl = CrimeLab.get(getActivity().getApplicationContext());
        File file = cl.getPhotoFile(mId);
        if (file == null || !file.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    file.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
