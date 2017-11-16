package ru.kpfu.kazanguide.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
public class GuidesListFragment extends Fragment {
    RecyclerView guideRecycleView;
    List<Guide> guides;

    //------------------------
    Guide testGuide;
    //------------------------

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        guides = new ArrayList<>();
        //------------------------
        testGuide = new Guide();
        testGuide.setName("Улица Баумана");
        testGuide.setPhotoLink("http://www.kazantur.ru/pic/do926.jpg");
        testGuide.setDescription("Улица Баумана — одна из самых старых улиц Казани. В эпоху Казанского ханства она называлась Ногайской дорогой. В 1552 году, во время штурма Казанского Кремля московскими войсками Ивана Грозного, обе его стены южнее и севернее улицы были проломлены взрывами, а улица была названа сначала Проломной, а затем Большой Проломной. В XVI веке, продолжая уже имевшуюся застройку северной части улицы, в её южной части возникла Новая слобода, получившая позже название Богоявленской по названию сооружённой здесь церкви. В 1930 году, улица была переименована в честь выходца из Казани революционера Баумана. Это название сохранилось до настоящего времени, хотя в постсоветское время предлагалось её переименовать в улицу Шаляпина.\n" +
                "Улица была красиво благоустроена и декорирована в начале 1990-х годов, а первой в городе пешеходной зоной стала в 1986 году. До этого по улице по проложенной в 1948 году первой в городе линии троллейбуса ходили его маршруты № 1,4 (ранее также 7) и несколько маршрутов автобуса, до 1935 года — трамвай, до 1899 года — конка. Транспортная доступность улицы осуществляется с расположенных в её начале и конце двух станций метро, а также с площади Тукая (троллейбус и автобус), с соседних улиц Островского и Кремлёвская (автобус), Право-Булачная (троллейбус и автобус).\n" +
                "На протяжении квартала в северной части улицы сооружена в конструкциях, но пока не отделана и не открыта (ввиду организационно-технических проблем с реконструкцией примыкающего здания гостиницы «Казань») торгово-развлекательная подземная галерея, из которой есть несколько отдельных выходов на улицу, а также предусмотрен выход в южный вестибюль станции метро «Кремлёвская».");
        guides.add(testGuide);
        //------------------------

        View drawer = inflater.inflate(R.layout.fragment_guides, container, false);
        guideRecycleView = (RecyclerView) drawer.findViewById(R.id.guide_recycler);

        if (guideRecycleView.getParent() != null) {
            ((ViewGroup) guideRecycleView.getParent()).removeView(guideRecycleView);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        guideRecycleView.setLayoutManager(layoutManager);

        CaptionedImageAdapter adapter = new CaptionedImageAdapter(guides, getActivity().getApplicationContext());
        guideRecycleView.setAdapter(adapter);

        GuideDaoWebImpl.getApi().getAll().enqueue(new Callback<List<Guide>>() {
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

        ObjectOutputCache writer = new ObjectOutputCache("guideList.ser", getActivity().getApplicationContext());
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
