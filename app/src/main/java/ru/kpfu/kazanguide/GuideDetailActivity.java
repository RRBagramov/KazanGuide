package ru.kpfu.kazanguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.kazanguide.model.Guide;
import ru.kpfu.kazanguide.util.ObjectInputFromCache;

public class GuideDetailActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;
    public static final String EXTRA_GUIDENO = "guideNo";
    private List<Guide> guides;
    private ObjectInputFromCache reader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideDetailActivity.this, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Вывод подробной информации о маршруте
        int guideNo = (Integer) getIntent().getExtras().get(EXTRA_GUIDENO);
        reader = new ObjectInputFromCache("guideList.ser", getApplicationContext());
        guides = (ArrayList<Guide>) reader.cacheReader();

       // TextView textView = (TextView) findViewById(R.id.guide_text);
      //  textView.setText(guides.get(guideNo).getName());

        setTitle(guides.get(guideNo).getName());

        TextView textView = (TextView) findViewById(R.id.guide_description);
        textView.setText(guides.get(guideNo).getDescription());

        // TODO: 15.11.2017 заменить error image
        ImageView imageView = (ImageView) findViewById(R.id.guide_image);
        Picasso.with(getApplicationContext())
                .load(guides.get(guideNo).getPhotoLink()).error(R.drawable.bn_user)
                .into(imageView);

    }
}
