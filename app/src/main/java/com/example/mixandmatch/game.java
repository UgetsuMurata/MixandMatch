package com.example.mixandmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class game extends AppCompatActivity {
    DBHandler DB;
    private ProgressBar progress;
    public String USERNAME;
    public String DIFFICULTY;
    public String MODE;
    public String CATEGORY;
    public Dictionary<Integer, Integer> CARDS;
    public View HEADER;
    public View CONTENT;
    public Integer OPENED_CARD;
    public List<Integer> CARD_PLACEMENT;
    public List<Integer> OPENED_CARDS;
    public List<ImageView> previousCard;
    public Integer SCORE;
    public TextView scoreDisplay, remainingTime;
    public CountDownTimer timer;
    public Long msUntilFinished;
    private boolean timerRunning;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private boolean isStopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DB = new DBHandler(this);

        Intent intent = getIntent();
        DIFFICULTY = intent.getStringExtra("DIFFICULTY");
        MODE = intent.getStringExtra("MODE");
        USERNAME = intent.getStringExtra("USERNAME");
        CATEGORY = intent.getStringExtra("CATEGORY");
        OPENED_CARD = -1;
        OPENED_CARDS = new ArrayList<>();
        SCORE = 0;
        previousCard = new ArrayList<>();
        CARD_PLACEMENT = generateCardPlacement();
        configureScreen();
        if (MODE.equals("SCORE")){
            CARDS = new Hashtable<>();
            CARDS.put(1, 0);
            CARDS.put(2, 0);
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
            scoreDisplay = HEADER.findViewById(R.id.score_view);
            remainingTime = HEADER.findViewById(R.id.remaining_time);
            progress = HEADER.findViewById(R.id.progressBar);
            isStopwatch = false;
            configureCountdown(); //run timer
            timerRunning = true;
        } else {
            remainingTime = HEADER.findViewById(R.id.stopwatch);
            isStopwatch = true;
            runStop(); //run stopwatch
            startStopwatch(); //start stopwatch
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
        Integer[] _id;
        switch (DIFFICULTY) {
            case "MODERATE":
                _id = new Integer[]{R.id.moderateCard01, R.id.moderateCard02,
                        R.id.moderateCard03, R.id.moderateCard04, R.id.moderateCard05,
                        R.id.moderateCard06, R.id.moderateCard07, R.id.moderateCard08};
                if(CARD_PLACEMENT.size() != _id.length)
                    break;
                for (int i=0; i < CARD_PLACEMENT.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, CARD_PLACEMENT.get(a));
                }
                break;
            case "HARD":
                _id = new Integer[]{R.id.hardCard01, R.id.hardCard02,
                        R.id.hardCard03, R.id.hardCard04, R.id.hardCard05,
                        R.id.hardCard06, R.id.hardCard07, R.id.hardCard08,
                        R.id.hardCard09, R.id.hardCard10};
                if(CARD_PLACEMENT.size() != _id.length) break;
                for (int i=0; i < CARD_PLACEMENT.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, CARD_PLACEMENT.get(a));
                }
                break;
            case "EXTREME":
                _id = new Integer[]{R.id.extremeCard01, R.id.extremeCard02,  R.id.extremeCard03,
                        R.id.extremeCard04, R.id.extremeCard05, R.id.extremeCard06,
                        R.id.extremeCard07, R.id.extremeCard08, R.id.extremeCard09,
                        R.id.extremeCard10, R.id.extremeCard11, R.id.extremeCard12};
                if(CARD_PLACEMENT.size() != _id.length)
                    break;
                for (int i=0; i < CARD_PLACEMENT.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, CARD_PLACEMENT.get(a));
                }
                break;
            default:
                _id = new Integer[]{R.id.card01, R.id.card02, R.id.card03,
                        R.id.card04, R.id.card05, R.id.card06};
                if(CARD_PLACEMENT.size() != _id.length) break;
                for (int i=0; i < CARD_PLACEMENT.size(); i++) {
                    int a = i;
                    ImageView img = content.findViewById(_id[i]);
                    onClick(img, CARD_PLACEMENT.get(a));
                }
                break;
        }
    }

    private void onClick(ImageView img, int index) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(getImage(index));
                String stringID = stringID(img);
                Integer idNum = Integer.parseInt(stringID.substring(stringID.length()-2));

                if (OPENED_CARD.equals(idNum) || OPENED_CARDS.contains(idNum)) return;
                if (OPENED_CARD == -1) {
                    OPENED_CARD = idNum;
                    previousCard.add(img);
                } else {
                    if (CARD_PLACEMENT.get(idNum-1).equals(CARD_PLACEMENT.get(OPENED_CARD-1))){
                        if (MODE.equals("SCORE")){
                            //get how many times the card is opened
                            Integer times1 = CARDS.get(OPENED_CARD);
                            Integer times2 = CARDS.get(idNum);
                            times1 = 7 - times1;
                            times2 = 7 - times2;
                            //calculate equivalent score
                            Integer score;
                            if (times1<1) score = times2;
                            else if (times2<1) score = times1;
                            else if (times1<1 && times2<1) score = 1;
                            else score = times1 * times2;
                            SCORE = SCORE + score;
                            scoreDisplay.setText(String.valueOf(SCORE));
                        } //do nothing specific if time-based
                        OPENED_CARDS.add(OPENED_CARD);
                        OPENED_CARDS.add(idNum);

                        for (int i=0; i < previousCard.size(); i++) {
                            String previousStringID = stringID(previousCard.get(i));
                            Integer previousIdNum = Integer.parseInt(previousStringID.substring(previousStringID.length()-2));
                            if (Objects.equals(OPENED_CARD, previousIdNum) || Objects.equals(idNum, previousIdNum))
                                previousCard.remove(previousCard.get(i));
                        }
                        for (int i=0; i < previousCard.size(); i++)
                            previousCard.get(i).setImageResource(R.drawable.blank_tile1);
                        previousCard.clear();
                        //detect if all cards are open
                        switch (DIFFICULTY){
                            case "MODERATE":
                                if (OPENED_CARDS.size() == 8) finishGame();//call finish game
                                break;
                            case "HARD":
                                if (OPENED_CARDS.size() == 10) finishGame();//call finish game
                                break;
                            case "EXTREME":
                                if (OPENED_CARDS.size() == 12) finishGame();//call finish game
                                break;
                            default:
                                if (OPENED_CARDS.size() == 6) finishGame();//call finish game
                                break;
                        }
                    } else {
                        //close cards
                        final Handler handler = new Handler();
                        handler.postDelayed((Runnable) () -> {
                            img.setImageResource(R.drawable.blank_tile1);
                            for (int i=0; i < previousCard.size(); i++)
                                previousCard.get(i).setImageResource(R.drawable.blank_tile1);
                            previousCard.clear();
                        }, 500);
                        //if score-based, add 1 to opened cards
                        if (MODE.equals("SCORE") && CARDS.get(idNum) <= 7) {
                            CARDS.put(idNum, CARDS.get(idNum)+1);
                            CARDS.put(OPENED_CARD, CARDS.get(OPENED_CARD)+1);
                        }
                    }
                    OPENED_CARD = -1;
                }
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

    private void finishGame(){
        if (MODE.equals("SCORE")) {
            Integer remainingTimeBonus = Math.toIntExact(msUntilFinished / 1000)*5;
            SCORE = SCORE + remainingTimeBonus;
            pauseCountdown();
            timerRunning = false;
        } else {
            doneStopwatch();
            SCORE = seconds;
        }
        //finish game - store results to database
        Intent intent = new Intent(game.this, leaderboard.class);
        if (!USERNAME.equals("Guest")) { //if using an account, add to database
            if (MODE.equals("TIME")) {
                DBHandler.timeLB time = DB.new timeLB();
                Integer previousRecords = time.userScore(USERNAME, DIFFICULTY.toLowerCase());
                if (previousRecords > SCORE || previousRecords==0){
                    time.insertScore(USERNAME, DIFFICULTY.toLowerCase(), SCORE);
                }
            } else {
                DBHandler.scoreLB score = DB.new scoreLB();
                if (score.userScore(USERNAME, DIFFICULTY.toLowerCase()) < SCORE){
                    score.insertScore(USERNAME, DIFFICULTY.toLowerCase(), SCORE);
                }
                boolean timer_done;
                switch (DIFFICULTY){
                    case "MODERATE":
                        timer_done = !(OPENED_CARDS.size() == 8);
                    case "HARD":
                        timer_done = !(OPENED_CARDS.size() == 10);
                    case "EXTREME":
                        timer_done = !(OPENED_CARDS.size() == 12);
                    default:
                        timer_done = !(OPENED_CARDS.size() == 6);
                }
                Log.d("TIME_DONE 0", String.valueOf(timer_done));
                intent.putExtra("TIMER_DONE", timer_done);
            }
        }
        //send intents
        intent.putExtra("DATABASE", MODE);
        intent.putExtra("SCORE", String.valueOf(SCORE));
        intent.putExtra("USERNAME", USERNAME);
        intent.putExtra("DIFFICULTY", DIFFICULTY);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

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

    @Override
    protected void onPause() {
        super.onPause();
        if (isStopwatch) {
            wasRunning = running;
            running = false;
        } else {
            if (timerRunning) pauseCountdown();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isStopwatch) {
            if (wasRunning) {
                running = true;
            }
        } else {
            if (msUntilFinished!=null) continueCountdown();
        }
    }

    //COUNTDOWN TIMER
    public void configureCountdown(){
        Long time;
        switch (DIFFICULTY){
            case "MODERATE": time = (15*1000L*2);
            case "HARD": time = (15*1000L*4);
            case "EXTREME": time = (15*1000L*6);
            default:time = (15*1000L);
        }
        progress.setMax(Math.toIntExact(time));
        startCountdown(time);
    }
    public void startCountdown(Long time){
        Log.d("TIMER INSIDE startCountDown", String.valueOf(time));
        timer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                msUntilFinished = millisUntilFinished;
                remainingTime.setText(f.format(min) + ":" + f.format(sec));
                progress.setProgress(Math.toIntExact(msUntilFinished));
            }
            public void onFinish() {
                remainingTime.setText("00:00");
                finishGame();
            }
        };
        timer.start();
    }
    public void pauseCountdown(){timer.cancel();}
    public void continueCountdown(){startCountdown(msUntilFinished);}

    //STOPWATCH TIMER
    public void startStopwatch(){
        running = true;
        seconds = 0;}
    public void doneStopwatch(){running = false;}
    private void runStop(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                remainingTime.setText(time);
                if (running) seconds++;
                handler.postDelayed(this, 1000);
            }
        });
    }
}