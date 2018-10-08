package com.alfianpp.randomherogeneratormlbb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView hero_name;
    private TextView hero_role;
    private TextView hero_speciality;
    private ImageView hero_image;

    private OnFragmentInteractionListener mListener;

    private List<String> all_heroes = new ArrayList<>();

    private void initializeAllHeroes() {
        String line;

        InputStream is = getResources().openRawResource(R.raw.heroes_mlbb);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        try {
            while((line = reader.readLine()) != null){
                all_heroes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        all_heroes.remove(0);
    }

    private void doAllRandom(){
        List<List<String>> selectedHeros = new ArrayList<>();
        for(int i=0; i<2; i++) {
            List<String> selected_hero = getSelectedHeroData(getRandomList(all_heroes));
            selectedHeros.add(selected_hero);
        }

        showSelectedHero(selectedHeros);
    }

    private List<String> getSelectedHeroData(String selected_hero){
        List<String> list = new ArrayList<>();

        String[] hero_data = selected_hero.split(",");
        for(int i=0; i<hero_data.length; i++){
            list.add(hero_data[i]);
        }

        return list;
    }

    private String getRandomList(List<String> list){
        Random random = new Random();

        return list.get(random.nextInt(list.size()));
    }

    private void showSelectedHero(List<List<String>> selectedHeros){
        int count = 0;
        for (List<String> selected_hero : selectedHeros){
            count++;
            if(count==1)
                showNumberOne(selected_hero);
        }
    }

    private void showNumberOne(List<String> selected_hero){
        hero_image.setImageResource(hero_image.getContext().getResources().getIdentifier("img_hero_head_id"+selected_hero.get(0), "drawable", hero_image.getContext().getPackageName()));
        hero_name.setText(selected_hero.get(1));
        if(TextUtils.isEmpty(selected_hero.get(3))){
            hero_role.setText(selected_hero.get(2));
        }else{
            hero_role.setText(selected_hero.get(2) + "/" + selected_hero.get(3));
        }
        if(TextUtils.isEmpty(selected_hero.get(5))){
            hero_speciality.setText(selected_hero.get(4));
        }else{
            hero_speciality.setText(selected_hero.get(4) + "/" + selected_hero.get(5));
        }
    }

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab3, container, false);
        hero_name = view.findViewById(R.id.txtHeroName);
        hero_role = view.findViewById(R.id.txtHeroRole);
        hero_speciality = view.findViewById(R.id.txtHeroSpeciality);
        hero_image = view.findViewById(R.id.imgHeroHead);
        initializeAllHeroes();
        doAllRandom();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
