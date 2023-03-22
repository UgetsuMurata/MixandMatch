package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        DIFFICULTY = intent.getStringExtra("DIFFICULTY");
        MODE = intent.getStringExtra("MODE");
        USERNAME = intent.getStringExtra("USERNAME");
        CATEGORY = intent.getStringExtra("CATEGORY");
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
        provideCards(content);
    }

    private void provideCards(View content){
        List<Integer> index = generateCardPlacement();
        switch (DIFFICULTY) {
            case "MODERATE":
            case "HARD":
            case "EXTREME":
                break;
            default:
                Integer[] _id = new Integer[]{R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6};
                if(index.size() != _id.length)
                    break;
                for (int i=0; i < index.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, index.get(a));
                }
                break;
        }
    }

    private void onClick(ImageView img, int index) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(getImage(index));
            }
        });
    }

    private int getImage(int i) {
        switch (CATEGORY) {
            case "CANDY":
                if (i == 1)
                    return R.drawable.one_one;
                if (i == 2)
                    return R.drawable.one_two;
                if (i == 3)
                    return R.drawable.one_three;
                if (i == 4)
                    return R.drawable.one_four;
                if (i == 5)
                    return R.drawable.one_five;
                if (i == 6)
                    return R.drawable.one_six;
                break;
            case "CUBE":
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
        } else if (Objects.equals(DIFFICULTY, "moderate")) {
            //4x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4};
        } else if (Objects.equals(DIFFICULTY, "difficult")) {
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