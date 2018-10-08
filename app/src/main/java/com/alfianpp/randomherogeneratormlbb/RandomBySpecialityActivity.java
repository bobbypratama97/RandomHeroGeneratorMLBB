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

import com.alfianpp.randomherogeneratormlbb.databinding.ActivityRandomBySpecialityBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBySpecialityActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityRandomBySpecialityBinding binding;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private AdView mAdView;

    private ArrayList<String> all_heroes = new ArrayList<>();
    private ArrayList<String> charge_heroes = new ArrayList<>();
    private ArrayList<String> burst_heroes = new ArrayList<>();
    private ArrayList<String> damage_heroes = new ArrayList<>();
    private ArrayList<String> push_heroes = new ArrayList<>();
    private ArrayList<String> reap_heroes = new ArrayList<>();
    private ArrayList<String> regen_heroes = new ArrayList<>();
    private ArrayList<String> poke_heroes = new ArrayList<>();
    private ArrayList<String> cc_heroes = new ArrayList<>();
    private ArrayList<String> initiator_heroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_random_by_speciality);

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
        navigationView.setCheckedItem(R.id.nav_random_by_speciality);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, "ca-app-pub-7582061309581400~2116909334");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initializeHeroesBySpeciality();
        showSelectedHero(getSelectedHeroData(getRandomHero(all_heroes)));

        binding.btnRandomCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Charge");
            }
        });

        binding.btnRandomBurst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Burst");
            }
        });

        binding.btnRandomDamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Damage");
            }
        });

        binding.btnRandomPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Push");
            }
        });

        binding.btnRandomReap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Reap");
            }
        });

        binding.btnRandomRegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Regen");
            }
        });

        binding.btnRandomPoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Poke");
            }
        });

        binding.btnRandomCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Crowd Control");
            }
        });

        binding.btnRandomInitiator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomBySpeciality("Initiator");
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
            case R.id.nav_random_all: {
                Intent intent = new Intent(RandomBySpecialityActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("all_heroes", all_heroes);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.nav_random_by_role: {
                Intent intent = new Intent(RandomBySpecialityActivity.this, RandomByRoleActivity.class);
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

    private void initializeHeroesBySpeciality(){
        List<String> hero_data;

        for(int i=0; i<all_heroes.size(); i++){
            hero_data = getSelectedHeroData(all_heroes.get(i));

            addHeroBySpecialityToList(all_heroes.get(i), hero_data.get(4));
            if(!TextUtils.isEmpty(hero_data.get(5))){
                addHeroBySpecialityToList(all_heroes.get(i), hero_data.get(5));
            }
        }
    }

    private void addHeroBySpecialityToList(String hero, String speciality){
        if(speciality.equals("Charge")){
            charge_heroes.add(hero);
        }else if(speciality.equals("Burst")){
            burst_heroes.add(hero);
        }else if(speciality.equals("Damage")){
            damage_heroes.add(hero);
        }else if(speciality.equals("Push")){
            push_heroes.add(hero);
        }else if(speciality.equals("Reap")){
            reap_heroes.add(hero);
        }else if(speciality.equals("Regen")){
            regen_heroes.add(hero);
        }else if(speciality.equals("Poke")){
            poke_heroes.add(hero);
        }else if(speciality.equals("Crowd Control")){
            cc_heroes.add(hero);
        }else if(speciality.equals("Initiator")){
            initiator_heroes.add(hero);
        }
    }

    private String getRandomHero(List<String> list){
        Random random = new Random();

        return list.get(random.nextInt(list.size()));
    }

    private List<String> getSelectedHeroData(String selected_hero){
        List<String> list = new ArrayList<>();

        String[] hero_data = selected_hero.split(",");
        for(int i=0; i<hero_data.length; i++){
            list.add(hero_data[i]);
        }

        return list;
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

    private void doRandomBySpeciality(String speciality){
        List<String> selected_hero = new ArrayList<>();

        if(speciality.equals("Charge")){
            selected_hero = getSelectedHeroData(getRandomHero(charge_heroes));
        }else if(speciality.equals("Burst")){
            selected_hero = getSelectedHeroData(getRandomHero(burst_heroes));
        }else if(speciality.equals("Damage")){
            selected_hero = getSelectedHeroData(getRandomHero(damage_heroes));
        }else if(speciality.equals("Push")){
            selected_hero = getSelectedHeroData(getRandomHero(push_heroes));
        }else if(speciality.equals("Reap")){
            selected_hero = getSelectedHeroData(getRandomHero(reap_heroes));
        }else if(speciality.equals("Regen")){
            selected_hero = getSelectedHeroData(getRandomHero(regen_heroes));
        }else if(speciality.equals("Poke")){
            selected_hero = getSelectedHeroData(getRandomHero(poke_heroes));
        }else if(speciality.equals("Crowd Control")){
            selected_hero = getSelectedHeroData(getRandomHero(cc_heroes));
        }else if(speciality.equals("Initiator")){
            selected_hero = getSelectedHeroData(getRandomHero(initiator_heroes));
        }

        showSelectedHero(selected_hero);
    }
}
