package com.example.mohea.aba_ep1_words;

public class Check {

    public Check() {
    }

    public int checkPosition(boolean isStart,boolean isCenter,boolean isEnd){
        int position=0;

        if(isStart&&isCenter&&isEnd){
            position = 7;
        }else if(isStart&&isCenter){
            position = 4;
        }else if(isStart&&isEnd){
            position = 5;
        }else if(isCenter&&isEnd){
            position = 6;
        }else if(isEnd){
            position = 3;
        }else if(isCenter){
            position = 2;
        }else if(isStart){
            position = 1;
        }

        return position;
    }

}
