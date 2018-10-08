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

import com.alfianpp.randomherogeneratormlbb.databinding.ActivityRandomByRoleBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomByRoleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityRandomByRoleBinding binding;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private AdView mAdView;

    private ArrayList<String> all_heroes = new ArrayList<>();
    private ArrayList<String> tank_heroes = new ArrayList<>();
    private ArrayList<String> fighter_heroes = new ArrayList<>();
    private ArrayList<String> assassin_heroes = new ArrayList<>();
    private ArrayList<String> mage_heroes = new ArrayList<>();
    private ArrayList<String> marksman_heroes = new ArrayList<>();
    private ArrayList<String> support_heroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_random_by_role);

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
        navigationView.setCheckedItem(R.id.nav_random_by_role);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, "ca-app-pub-7582061309581400~2116909334");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initializeHeroesByRole();
        showSelectedHero(getSelectedHeroData(getRandomHero(all_heroes)));

        binding.btnRandomTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomByRole("Tank");
            }
        });

        binding.btnRandomFighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomByRole("Fighter");
            }
        });

        binding.btnRandomAssassin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomByRole("Assassin");
            }
        });

        binding.btnRandomMage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomByRole("Mage");
            }
        });

        binding.btnRandomMarksman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomByRole("Marksman");
            }
        });

        binding.btnRandomSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRandomByRole("Support");
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
                Intent intent = new Intent(RandomByRoleActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("all_heroes", all_heroes);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.nav_random_by_speciality: {
                Intent intent = new Intent(RandomByRoleActivity.this, RandomBySpecialityActivity.class);
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

    private void initializeHeroesByRole(){
        List<String> hero_data;

        for(int i=0; i<all_heroes.size(); i++){
            hero_data = getSelectedHeroData(all_heroes.get(i));

            addHeroByRoleToList(all_heroes.get(i), hero_data.get(2));
            if(!TextUtils.isEmpty(hero_data.get(3))){
                addHeroByRoleToList(all_heroes.get(i), hero_data.get(3));
            }
        }
    }

    private void addHeroByRoleToList(String hero, String role){
        if(role.equals("Tank")){
            tank_heroes.add(hero);
        }else if(role.equals("Fighter")){
            fighter_heroes.add(hero);
        }else if(role.equals("Assassin")){
            assassin_heroes.add(hero);
        }else if(role.equals("Mage")){
            mage_heroes.add(hero);
        }else if(role.equals("Marksman")){
            marksman_heroes.add(hero);
        }else if(role.equals("Support")){
            support_heroes.add(hero);
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

    private void doRandomByRole(String role){
        List<String> selected_hero = new ArrayList<>();

        if(role.equals("Tank")){
            selected_hero = getSelectedHeroData(getRandomHero(tank_heroes));
        }else if(role.equals("Fighter")){
            selected_hero = getSelectedHeroData(getRandomHero(fighter_heroes));
        }else if(role.equals("Assassin")){
            selected_hero = getSelectedHeroData(getRandomHero(assassin_heroes));
        }else if(role.equals("Mage")){
            selected_hero = getSelectedHeroData(getRandomHero(mage_heroes));
        }else if(role.equals("Marksman")){
            selected_hero = getSelectedHeroData(getRandomHero(marksman_heroes));
        }else if(role.equals("Support")){
            selected_hero = getSelectedHeroData(getRandomHero(support_heroes));
        }

        showSelectedHero(selected_hero);
    }
}
