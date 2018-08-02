package com.johnnylambada.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

import com.johnnylambada.acviewmodel.model.Repo;
import com.johnnylambada.acviewmodel.networking.RepoApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedRepoViewModel extends ViewModel {

    public static final String REPO_DETAILS = "repo_details";
    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();
    private Call<Repo> repoCall;


    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    public void setSelectedRepo(Repo repo) {
        selectedRepo.setValue(repo);
    }

    public void saveToBundle(Bundle outState) {
        if (selectedRepo.getValue() != null) {
            final String[] val = new String[]{selectedRepo.getValue().owner.login, selectedRepo.getValue().name};
            outState.putStringArray(REPO_DETAILS, val);
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState){
        if (selectedRepo.getValue()==null){
            // We're only doing this if we don't have a selected repo set
            if (savedInstanceState != null && savedInstanceState.containsKey(REPO_DETAILS)){
                final String[] vals = savedInstanceState.getStringArray(REPO_DETAILS);
                loadRepo(vals[0],vals[1]);
            }
        }
    }

    private void loadRepo(String owner, String name) {
        repoCall = RepoApi.getRepoService().getRepo(owner,name);
        repoCall.enqueue(new Callback<Repo>() {
            @Override public void onResponse(Call<Repo> call, Response<Repo> response) {
                selectedRepo.setValue(response.body());
                repoCall = null;
            }

            @Override public void onFailure(Call<Repo> call, Throwable t) {
                Log.e(getClass().getSimpleName(),"Error restoring state", t);
                repoCall = null;
            }
        });
    }

    @Override protected void onCleared() {
        if (repoCall!=null){
            repoCall.cancel();
            repoCall = null;
        }
    }
}
