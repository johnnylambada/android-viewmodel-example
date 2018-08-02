package com.johnnylambada.acviewmodel.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.johnnylambada.acviewmodel.home.ListViewModel;
import com.johnnylambada.acviewmodel.home.SelectedRepoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindsListViewModel(ListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindsSelectedRepoViewModel(SelectedRepoViewModel viewModel);
}
