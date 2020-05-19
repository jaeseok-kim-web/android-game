package com.example.findsamepicturegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Message;
import android.util.Log;


public class gamestart extends AppCompatActivity implements View.OnClickListener {

    int TOTAL_CARD_NUM = 16; // 카드 수

    int[] cardId = {R.id.image0, R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6, R.id.image7,
            R.id.image8, R.id.image9, R.id.image10, R.id.image11, R.id.image12, R.id.image13, R.id.image14, R.id.image15};

    Card[] cardArray = new Card[TOTAL_CARD_NUM];

    private int CLICK_CNT = 0; // 클릭 카운트
    private Card firstcard, secondcard; // 첫번째 누른 버튼과 두번재 누른 버튼
    private int SUCCESS_CNT = 0; // 짝 맞추기 성공 카운트
    private int count = 0; //총 횟수
    private boolean INPLAY = true; // 게임시작
    private Button btnStart, btnExit; //게임시작버튼,게임종료버튼
    private static MediaPlayer mp3; //배경음악
    SoundPool sound;
    int soundId1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestart);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("같은그림찾기");
        sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId1 = sound.load(this, R.raw.sound, 1);

        mp3 = MediaPlayer.create(this, R.raw.music);
        mp3.setLooping(true);
        mp3.start();


        for (int i = 0; i < TOTAL_CARD_NUM; i++) {
            cardArray[i] = new Card(i / 2); // 카드 8개 생성
            findViewById(cardId[i]).setOnClickListener(this); // 카드 클릭 리스너 설정
            cardArray[i].card = (ImageButton) findViewById(cardId[i]); // 카드 할당
            cardArray[i].onBack(); // 카드 뒤집어 놓은 상태로 대기
        }

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sound.play(soundId1, 1f, 1f, 0, 0, 1f);
                startGame();
            }
        });
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sound.play(soundId1, 1f, 1f, 0, 0, 1f);
                finish();
            }
        });


    }


    @Override //기본 메서드
    public void onClick(View v) {
        sound.play(soundId1, 1f, 1f, 0, 0, 1f);
        if (INPLAY) {
            switch (CLICK_CNT) {//클릭카운트
                case 0: // 첫번째 카드 뒤집었을 경우
                    for (int i = 0; i < TOTAL_CARD_NUM; i++) {
                        if (cardArray[i].card == (ImageButton) v) { //카드 할당
                            firstcard = cardArray[i]; //할당 받은 카드중 firstcard에 대임
                            break;
                        }
                    }
                    if (firstcard.isBack) { // 첫번째 카드 누르면
                        firstcard.onFront(); //앞면으로 뒤집어짐
                        CLICK_CNT++; //case 1번으로 넘어감

                    }

                    break;
                case 1: // 두번째 카드 뒤집었을 경우
                    for (int i = 0; i < TOTAL_CARD_NUM; i++) {
                        if (cardArray[i].card == (ImageButton) v) { //카드 할당
                            secondcard = cardArray[i]; //할당 받은 카드중 secondcard에 대입
                            break;
                        }
                    }
                    if (secondcard.isBack) { // 뒷면이 보이는 카드일 경우만 처리
                        secondcard.onFront(); //앞면으로 뒤집어짐
                        CLICK_CNT=0;

                        if (firstcard.value == secondcard.value) { // 짝이 맞은 경우
                            SUCCESS_CNT++;
                            count++;
                        } else { //짝이 맞지 않은 경우
                            count++;
                            Timer t = new Timer(0);
                            t.start();
                        }


                        if (SUCCESS_CNT == TOTAL_CARD_NUM / 2) { // 모든 카드의 짝을 다 맞추었을 경우
                            clearDialog();

                        }

                        CLICK_CNT = 0; //초기화
                    }
                    break;
            }
        }

    }

    void startGame() { //게임시작시
        int[] random = new int[TOTAL_CARD_NUM];
        int x;
        boolean dup;

        for (int i = 0; i < TOTAL_CARD_NUM; i++) { // 모든 카드의 뒷면이 보이게 함
            if (!cardArray[i].isBack)
                cardArray[i].onBack();

            while (true) {
                dup = false;
                x = (int) (Math.random() * TOTAL_CARD_NUM); //16개카드를 랜덤으로 해주는 수
                for (int j = 0; j < i; j++) {
                    if (random[j] == x) {
                        dup = true;
                        break;
                    }
                }
                if (!dup) break;
            }
            random[i] = x;
        }


        btnStart.setClickable(true);
        for (int i = 0; i < TOTAL_CARD_NUM; i++) {
            cardArray[i].card = (ImageButton) findViewById(cardId[random[i]]);
            cardArray[i].onFront();
        }

        Log.v("timer", "start");
        Timer t = new Timer(1);
        t.start();

        SUCCESS_CNT = 0; // 성공카운트 초기화
        CLICK_CNT = 0; // 클릭카운트 초기화
        count = 0; // 카운트 초기화
        INPLAY = false; //게임 종료

    }

    class Timer extends Thread { //타이머 설정(짝이 맞지 않을경우)
        int kind;

        Timer(int kind) { //생성자
            super();
            this.kind = kind;
        }

        @Override
        public void run() { //타이머 시작시
            INPLAY = false;

            try {
                if (kind == 0) { //카드 매칭 실패시에
                    Thread.sleep(1000);
                    mHandler.sendEmptyMessage(0);
                } else if (kind == 1) { //맨처음 시작시에
                    Thread.sleep(3000);
                    mHandler.sendEmptyMessage(1);
                }

            } catch (Exception e) {

            }
            INPLAY = true;
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    firstcard.onBack(); //뒤집어줌
                    secondcard.onBack();//뒤집어줌
                    firstcard.isBack = true;
                    secondcard.isBack = true;
                    break;
                case 1:
                    for (int i = 0; i < TOTAL_CARD_NUM; i++) {
                        cardArray[i].onBack();
                    }
                    break;
            }
        }
    };

    public void clearDialog() {
//        sound.play(soundId2, 1f, 1f, 0, 0, 1f);

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("짝 맞추기 완료");
        dlg.setMessage(count + "번 만에 모든 카드 짝을 맞추셨습니다. 축하합니다. 메인화면으로 돌아갑니다.");
        dlg.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mp3.stop();
                finish();
            }
        });
        dlg.show();
    }


    @Override
    protected void onUserLeaveHint() { //홈버튼 누를시에 음악정지
        mp3.pause();
        super.onUserLeaveHint();
    }

    @Override
    protected void onResume() {
        mp3.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() { //앱이 아예 정지되었을떄 음악정지
        mp3.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() { //백버튼을 눌렀을시에 음악정지지        mp3.stop();
        super.onBackPressed();
    }
}
