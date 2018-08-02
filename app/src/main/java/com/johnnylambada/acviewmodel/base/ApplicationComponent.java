package com.johnnylambada.acviewmodel.base;

import com.johnnylambada.acviewmodel.details.DetailsFragment;
import com.johnnylambada.acviewmodel.home.ListFragment;
import com.johnnylambada.acviewmodel.networking.NetworkModule;
import com.johnnylambada.acviewmodel.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component( modules = {
        NetworkModule.class,
        ViewModelModule.class,
})
public interface ApplicationComponent {
    void inject(ListFragment listFragment);

    void inject(DetailsFragment detailsFragment);
}
