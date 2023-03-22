package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class game extends AppCompatActivity {
    DBHandler DB;
    public String USERNAME;
    public String DIFFICULTY;
    public String MODE;
    public String CATEGORY;
    public Dictionary<Integer, Integer> CARDS = new Hashtable<>();
    public View HEADER;
    public View CONTENT;
    public ArrayList<View> OPEN_CARDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        DIFFICULTY = intent.getStringExtra("DIFFICULTY");
        MODE = intent.getStringExtra("MODE");
        USERNAME = intent.getStringExtra("USERNAME");
        CATEGORY = intent.getStringExtra("CATEGORY");
        OPEN_CARDS = new ArrayList<View>();
        configureScreen();
        if (MODE.equals("TIME")){
            CARDS = new Hashtable<>();
            CARDS.put(1, 0); //container of card flip data
            CARDS.put(2, 0); //card number, card flip amount
            CARDS.put(3, 0);
            CARDS.put(4, 0);
            CARDS.put(5, 0);
            CARDS.put(6, 0);
            CARDS.put(7, 0);
            CARDS.put(8, 0);
            CARDS.put(9, 0);
            CARDS.put(10, 0);
            CARDS.put(11, 0);
            CARDS.put(12, 0);
        }
    }

    private String stringID(View view){
        if (view.getId() == View.NO_ID) return "0";
        else return view.getResources().getResourceName(view.getId());
    }

    private void configureScreen(){
        View header, content;
        if (MODE.equals("TIME")){
            header = findViewById(R.id.gameHeader_TIME);
            switch (DIFFICULTY){
                case "MODERATE":
                    content = findViewById(R.id.TIME_MODERATE);
                    break;
                case "HARD":
                    content = findViewById(R.id.TIME_HARD);
                    break;
                case "EXTREME":
                    content = findViewById(R.id.TIME_EXTREME);
                    break;
                default:
                    content = findViewById(R.id.TIME_EASY);
                    break;
            }
        } else {
            header = findViewById(R.id.gameHeader_SCORE);
            switch (DIFFICULTY) {
                case "MODERATE":
                    content = findViewById(R.id.SCORE_MODERATE);
                    break;
                case "HARD":
                    content = findViewById(R.id.SCORE_HARD);
                    break;
                case "EXTREME":
                    content = findViewById(R.id.SCORE_EXTREME);
                    break;
                default:
                    content = findViewById(R.id.SCORE_EASY);
                    break;
            }
        }
        header.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
        HEADER = header;
        CONTENT = content;
        provideCards(content);
    }

    private void provideCards(View content){
        List<Integer> index = generateCardPlacement();
        Integer[] _id;
        switch (DIFFICULTY) {
            case "MODERATE":
                _id = new Integer[]{R.id.moderateCard01, R.id.moderateCard02,
                        R.id.moderateCard03, R.id.moderateCard04, R.id.moderateCard05,
                        R.id.moderateCard06, R.id.moderateCard07, R.id.moderateCard08};
                if(index.size() != _id.length)
                    break;
                for (int i=0; i < index.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, index.get(a));
                }
                break;
            case "HARD":
                _id = new Integer[]{R.id.hardCard01, R.id.hardCard02,
                        R.id.hardCard03, R.id.hardCard04, R.id.hardCard05,
                        R.id.hardCard06, R.id.hardCard07, R.id.hardCard08,
                        R.id.hardCard09, R.id.hardCard10};
                if(index.size() != _id.length) break;
                for (int i=0; i < index.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, index.get(a));
                }
                break;
            case "EXTREME":
                _id = new Integer[]{R.id.extremeCard01, R.id.extremeCard02,  R.id.extremeCard03,
                        R.id.extremeCard04, R.id.extremeCard05, R.id.extremeCard06,
                        R.id.extremeCard07, R.id.extremeCard08, R.id.extremeCard09,
                        R.id.extremeCard10, R.id.extremeCard11, R.id.extremeCard12};
                if(index.size() != _id.length)
                    break;
                for (int i=0; i < index.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, index.get(a));
                }
                break;
            default:
                _id = new Integer[]{R.id.card01, R.id.card02, R.id.card03,
                        R.id.card04, R.id.card05, R.id.card06};
                if(index.size() != _id.length) break;
                for (int i=0; i < index.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, index.get(a));
                }
                break;
        }
    }

    ImageView currentImageId;
    ImageView previousImageId;
    int currentValue;
    int previousValue;

    private void onClick(ImageView img, int index) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(getImage(index));
            }
        });
    }
    private int getImage(int imageNumber) {
        switch (CATEGORY) {
            case "CANDY":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.one_one;
                    case 2:
                        return R.drawable.one_two;
                    case 3:
                        return R.drawable.one_three;
                    case 4:
                        return R.drawable.one_four;
                    case 5:
                        return R.drawable.one_five;
                    case 6:
                        return R.drawable.one_six;
                }
            case "CUBE":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.two_one;
                    case 2:
                        return R.drawable.two_two;
                    case 3:
                        return R.drawable.two_three;
                    case 4:
                        return R.drawable.two_four;
                    case 5:
                        return R.drawable.two_five;
                    case 6:
                        return R.drawable.two_six;
                }
            case "DONUT":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.three_one;
                    case 2:
                        return R.drawable.three_two;
                    case 3:
                        return R.drawable.three_three;
                    case 4:
                        return R.drawable.three_four;
                    case 5:
                        return R.drawable.three_five;
                    case 6:
                        return R.drawable.three_six;
                }
            case "COOKIE":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.four_one;
                    case 2:
                        return R.drawable.four_two;
                    case 3:
                        return R.drawable.four_three;
                    case 4:
                        return R.drawable.four_four;
                    case 5:
                        return R.drawable.four_five;
                    case 6:
                        return R.drawable.four_six;
                }
            case "CANE":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.five_one;
                    case 2:
                        return R.drawable.five_two;
                    case 3:
                        return R.drawable.five_three;
                    case 4:
                        return R.drawable.five_four;
                    case 5:
                        return R.drawable.five_five;
                    case 6:
                        return R.drawable.five_six;
                }
            case "WIGGLES":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.six_one;
                    case 2:
                        return R.drawable.six_two;
                    case 3:
                        return R.drawable.six_three;
                    case 4:
                        return R.drawable.six_four;
                    case 5:
                        return R.drawable.six_five;
                    case 6:
                        return R.drawable.six_six;
                }
            case "GUMMY":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.seven_one;
                    case 2:
                        return R.drawable.seven_two;
                    case 3:
                        return R.drawable.seven_three;
                    case 4:
                        return R.drawable.seven_four;
                    case 5:
                        return R.drawable.seven_five;
                    case 6:
                        return R.drawable.seven_six;
                }
            case "ICE-CREAM":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.eight_one;
                    case 2:
                        return R.drawable.eight_two;
                    case 3:
                        return R.drawable.eight_three;
                    case 4:
                        return R.drawable.eight_four;
                    case 5:
                        return R.drawable.eight_five;
                    case 6:
                        return R.drawable.eight_six;
                }
            case "BEANS":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.nine_one;
                    case 2:
                        return R.drawable.nine_two;
                    case 3:
                        return R.drawable.nine_three;
                    case 4:
                        return R.drawable.nine_four;
                    case 5:
                        return R.drawable.nine_five;
                    case 6:
                        return R.drawable.nine_six;
                }
            case "POPSICLE":
                switch(imageNumber) {
                    case 1:
                        return R.drawable.ten_one;
                    case 2:
                        return R.drawable.ten_two;
                    case 3:
                        return R.drawable.ten_three;
                    case 4:
                        return R.drawable.ten_four;
                    case 5:
                        return R.drawable.ten_five;
                    case 6:
                        return R.drawable.ten_six;
                }
            default:
                break;
        }
        return 0;
    }

    //randomize cards
    //3x2 4x2 5x2 6x2
    private List<Integer> generateCardPlacement(){
        Integer[] cards = {1, 1, 2, 2};
        if (Objects.equals(DIFFICULTY, "EASY")){
            //3x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3};
        } else if (Objects.equals(DIFFICULTY, "MODERATE")) {
            //4x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4};
        } else if (Objects.equals(DIFFICULTY, "HARD")) {
            //4x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
        } else {
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6};
        }
        List<Integer> intList = Arrays.asList(cards);
        Collections.shuffle(intList);
        intList.toArray(cards);
        return intList;
    }

    private void storeTimeScore(Integer time_taken){
        //DB.timeLB.insertScore(username, difficulty, time_taken);
    }

    private void addScore(Integer points){
        // factors:
        // how many times flipped for cards as a
        // took how long since first flipped as b
        // (b/10)*a = score added
        // Store in dictionary which cards are flipped.

    }
    public void cardFlipped(View view){
        switch (view.getId()){
            //case R.id.card1:
                //add 1 to dictionary value
            //    break;
        }
    }


}