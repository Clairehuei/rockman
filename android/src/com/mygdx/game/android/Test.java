package com.mygdx.game.android;

/**
 * Created by User on 2015/10/21.
 */
public class Test {
    public static void main(String[] args){
        System.out.println("123");
//        double v = 0;
//        double v0 = 300;
//        double a = -10;
//        double d = 64;
//        double y = 0;
//        double deltat = 0.016;
//        double tempD = 0;




//        System.out.println("Start");
//        for(int i=1; i<100000;i++){
//            v = v0 + a*deltat;
//            tempD = v*deltat;
//            y = y+tempD;
//            if(y>=d){
//                System.out.println("i = "+i);
//                break;
//            }else{
//                System.out.println("y = "+y);
//            }
//        }
//        System.out.println("End");


        double v = 0;
        double v0 = 0;
        double a = -10;
        double d = 0;
        double y = 64;
        double deltat = 0.016;
        double tempD = 0;

        System.out.println("Start");
        for(int i=1; i<100000;i++){
            v = v0 + a*deltat;
            tempD = v*deltat;
            y = y+tempD;
            if(y<=d){
                System.out.println("i = "+i);
                break;
            }else{
                System.out.println("y = "+y);
            }
        }
        System.out.println("End");

    }
}
