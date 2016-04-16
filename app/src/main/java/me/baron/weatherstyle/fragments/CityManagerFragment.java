package me.baron.weatherstyle.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.baron.androidlibrary.fragment.BaseFragment;
import me.baron.weatherstyle.R;
import me.baron.weatherstyle.adapters.CityManagerAdapter;
import me.baron.weatherstyle.contract.CityManagerContract;
import me.baron.weatherstyle.models.style.Weather;
import me.baron.weatherstyle.presenter.CityManagerPresenter;

public class CityManagerFragment extends BaseFragment implements CityManagerContract.View {


    private static final String ARG_COLUMN_COUNT = "column-count";

    @Bind(R.id.rv_city_manager)
    RecyclerView cmRecyclerView;

    private int mColumnCount = 3;
    private List<Weather> weatherList;
    private CityManagerAdapter cityManagerAdapter;

    private CityManagerContract.Presenter presenter;

    public CityManagerFragment() {
    }

    public static CityManagerFragment newInstance(int columnCount) {
        CityManagerFragment fragment = new CityManagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city_manager, container, false);
        ButterKnife.bind(this, rootView);
        Context context = rootView.getContext();
        if (mColumnCount <= 1) {
            cmRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            cmRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        weatherList = new ArrayList<>();
        cityManagerAdapter = new CityManagerAdapter(weatherList);
        cityManagerAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        cmRecyclerView.setAdapter(cityManagerAdapter);

        presenter.start();

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void displaySavedCities(List<Weather> weatherList) {
        this.weatherList.addAll(weatherList);
        cityManagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(CityManagerPresenter presenter) {

        this.presenter = presenter;
    }
}