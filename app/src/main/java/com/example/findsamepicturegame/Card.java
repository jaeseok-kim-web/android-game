package com.example.findsamepicturegame;

import android.widget.ImageButton;

public class Card {
    private final static int backImageID = R.drawable.image0; //카드 뒷면
    private final static int[] frontImageID = {R.drawable.image1, R.drawable.image2, //카드 앞면
            R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6,
            R.drawable.image7, R.drawable.image8};

    int value; //카드 앞면의 인덱스 값
    boolean isBack; //true면 앞면 false면 뒷면
    ImageButton card; //카드

    Card(int value) {
        this.value = value;
    }

    public void onBack() { // 카드 뒷면이 보이게 뒤집음
        if (!isBack) {
            card.setBackgroundResource(backImageID);
            isBack = true;
        }
    }


    public void onFront() { // 카드 그림면을 보여줌
        if (isBack) {
            card.setBackgroundResource(frontImageID[value]);
            isBack = false;
        }
    }
}
