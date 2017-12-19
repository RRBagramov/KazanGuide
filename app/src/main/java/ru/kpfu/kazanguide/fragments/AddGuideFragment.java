package ru.kpfu.kazanguide.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import ru.kpfu.kazanguide.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddGuideFragment extends Fragment {

    static final int GALLERY_REQUEST = 1;
    final static int RQS_RECORDING = 2;
    Uri savedUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_guide, container, false);
        Button button = (Button) view.findViewById(R.id.btn_new_guide_photo_add);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        Button recordButton = (Button) view.findViewById(R.id.btn_new_guide_record_add);
        recordButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, RQS_RECORDING);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.new_guide_photo_miniature);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                }
            case RQS_RECORDING:
                try {
                    savedUri = data.getData();
                    Toast.makeText(getActivity(),
                            "Сохранено: " + savedUri.getPath(), Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(),
                            "Вы не выбрали запись", Toast.LENGTH_LONG).show();
                }


        }


    }
}
