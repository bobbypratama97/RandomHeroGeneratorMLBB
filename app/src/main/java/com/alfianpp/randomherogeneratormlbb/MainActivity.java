package com.alfianpp.randomherogeneratormlbb;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alfianpp.randomherogeneratormlbb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private AdView mAdView;

    private ArrayList<String> all_heroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            all_heroes = bundle.getStringArrayList("all_heroes");
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_random_all);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, "ca-app-pub-7582061309581400~2116909334");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        doAllRandom();

        binding.btnAllRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAllRandom();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_random_by_role: {
                Intent intent = new Intent(MainActivity.this, RandomByRoleActivity.class);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("all_heroes", all_heroes);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.nav_random_by_speciality: {
                Intent intent = new Intent(MainActivity.this, RandomBySpecialityActivity.class);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("all_heroes", all_heroes);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<String> getSelectedHeroData(String selected_hero){
        List<String> list = new ArrayList<>();

        String[] hero_data = selected_hero.split(",");
        for(int i=0; i<hero_data.length; i++){
            list.add(hero_data[i]);
        }

        return list;
    }

    private String getRandomHero(List<String> list){
        Random random = new Random();

        return list.get(random.nextInt(list.size()));
    }

    private void showSelectedHero(List<String> selected_hero){
        binding.imgHeroHead.setImageResource(binding.imgHeroHead.getContext().getResources().getIdentifier("img_hero_head_id"+selected_hero.get(0), "drawable", binding.imgHeroHead.getContext().getPackageName()));
        binding.txtHeroName.setText(selected_hero.get(1));
        if(TextUtils.isEmpty(selected_hero.get(3))){
            binding.txtHeroRole.setText(selected_hero.get(2));
        }else{
            binding.txtHeroRole.setText(selected_hero.get(2) + "/" + selected_hero.get(3));
        }
        if(TextUtils.isEmpty(selected_hero.get(5))){
            binding.txtHeroSpeciality.setText(selected_hero.get(4));
        }else{
            binding.txtHeroSpeciality.setText(selected_hero.get(4) + "/" + selected_hero.get(5));
        }
        binding.txtBattlePoints.setText(selected_hero.get(6));
        binding.txtDiamonds.setText(selected_hero.get(7));
        binding.txtTickets.setText(selected_hero.get(8));
    }

    private void doAllRandom(){
        List<String> selected_hero = getSelectedHeroData(getRandomHero(all_heroes));

        showSelectedHero(selected_hero);
    }
}