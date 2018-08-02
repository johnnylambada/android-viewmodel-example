package com.johnnylambada.acviewmodel.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.johnnylambada.acviewmodel.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListFragment extends Fragment {

    @BindView(R.id.list_view) RecyclerView listView;
    @BindView(R.id.tv_error) TextView errorTextView;
    @BindView(R.id.loading_view) ProgressBar loadingView;

    private Unbinder unbinder;
    private ListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(viewModel, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getRepos().observe(this, repos-> {
            if (repos!=null){
                listView.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getRepoLoadError().observe(this, isError -> {
            //noinspection ConstantConditions
            if (isError) {
                listView.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(R.string.api_error_repos);
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });
        viewModel.getRepoLoadError().observe(this, isLoading ->{
            //noinspection ConstantConditions
            loadingView.setVisibility(isLoading?View.VISIBLE:View.GONE);
            if (isLoading){
                errorTextView.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
