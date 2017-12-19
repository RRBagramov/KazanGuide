package ru.kpfu.kazanguide.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kpfu.kazanguide.GuideDetailActivity;
import ru.kpfu.kazanguide.R;
import ru.kpfu.kazanguide.dao.GuideDaoWebImpl;
import ru.kpfu.kazanguide.model.Guide;
import ru.kpfu.kazanguide.util.CaptionedImageAdapter;
import ru.kpfu.kazanguide.util.ObjectOutputCache;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteGuidesFragment extends Fragment {
    RecyclerView guideRecycleView;
    List<Guide> guides;

    //------------------------
    Guide testGuide;

    public FavoriteGuidesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        guides = new ArrayList<>();

        View drawer = inflater.inflate(R.layout.fragment_guides, container, false);
        guideRecycleView = (RecyclerView) drawer.findViewById(R.id.guide_recycler);

        if (guideRecycleView.getParent() != null) {
            ((ViewGroup) guideRecycleView.getParent()).removeView(guideRecycleView);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        guideRecycleView.setLayoutManager(layoutManager);

        CaptionedImageAdapter adapter = new CaptionedImageAdapter(guides, getActivity().getApplicationContext());
        guideRecycleView.setAdapter(adapter);

        GuideDaoWebImpl.getApi().getFavoriteGuides(1).enqueue(new Callback<List<Guide>>() {
            @Override
            public void onResponse(Call<List<Guide>> call, Response<List<Guide>> response) {
                if (response.body() != null) {
                    guides.addAll(response.body());
                    guideRecycleView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Guide>> call, Throwable t) {
//                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });

        ObjectOutputCache writer = new ObjectOutputCache("favoriteGuideList.ser", getActivity().getApplicationContext());
        writer.cacheWriter(guides);
        adapter.setListener(new CaptionedImageAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.EXTRA_GUIDENO, position);
                getActivity().startActivity(intent);
            }
        });

        return guideRecycleView;
    }

}
