package com.johnnylambada.acviewmodel.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.johnnylambada.acviewmodel.R;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.screen_container,new ListFragment())
                    .commit();
        }
    }
}
