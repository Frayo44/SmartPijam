package pijama.com.pijamasan.Utils;

import android.util.Log;

import pijama.com.pijamasan.Server.PijamServerr;

/**
 * Created by Yoav on 11/30/2015.
 */
public class PijamServer {

    int[] skinTempArr;

    int counter;
    int currSkinTemp, currAirTemp;
    int sum, avg;

    public int getCurrAirTemp() {
        return currAirTemp;
    }

    public void setCurrAirTemp(int currAirTemp) {
        this.currAirTemp = currAirTemp;
    }

    public int getCurrSkinTemp2() {
        return currSkinTemp;
    }




    public void setCurrSkinTemp(int currSkinTemp) {
        this.currSkinTemp = currSkinTemp;
    }

    public PijamServer()
    {
        skinTempArr = new int[]{ 29, 29, 29, 28, 28, 28, 29, 29, 29, 30, 30, 30, 29, 29, 29, 28,28, 28, 27, 27, 27, 27 ,28 , 29, 29, 29, 29, 30, 30, 30, 29, 29, 29, 30, 31, 31, 31, 32, 32, 32,32, 32, 32, 32, 32, 32, 33,
        33, 33, 34, 35, 36, 36, 36, 37, 36, 36, 36, 36, 35, 35, 35, 35, 35, 35, 34, 34, 34, 34, 34, 34,
        34, 33, 33, 33, 33, 33, 33, 32, 32, 32, 31, 30, 30, 30, 29, 29, 28, 28, 29, 29, 29, 29, 29, 29 };

        counter = sum = 0;
        currAirTemp = 24;
        avg = -1;

    }

    public void getSkinTemprature()
    {
        if(counter >= skinTempArr.length - 1)
            counter = 6;

        counter++;
        currSkinTemp = skinTempArr[counter];

        sum += currSkinTemp;

        if(counter == 5)
            avg = sum / counter;



        calculateAirTemp();

                //(int) 25 + (int)(Math.random() * ((34 - 25) + 1));
    }

    private void calculateAirTemp()
    {
        if(avg != -1) {
            int fluct = (avg - currSkinTemp);
            int prev = currAirTemp;

            if(Math.abs(fluct) > 4){
                if(currSkinTemp != skinTempArr[counter - 1]) {
                    currAirTemp = 24 + fluct / 2;
                    if (currAirTemp != prev) {
                        sendAirTemp();
                    }
                }
            }else{
                if(currAirTemp != 24) {
                    currAirTemp = 24;
                    sendAirTemp();
                }
            }

        }
    }

    private void sendAirTemp()
    {
        PijamServerr pij = new PijamServerr();
        pij.sendTemprature(currAirTemp + "");
    }

}
