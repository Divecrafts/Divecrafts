package io.clonalejandro.DivecraftsBungee.utils;

public class TimeUtils {

    public static int getTimeInMilliSeconds(int n, int data){
        switch (data){
            case 0:
                return n * 1000;
            case 1:
                return getTimeInMilliSeconds(n * 60, 0);
            case 2:
                return getTimeInMilliSeconds(n * 60, 1);
            case 3:
                return getTimeInMilliSeconds(n * 24, 2);
            case 4:
                return getTimeInMilliSeconds(n * 7, 3);
            case 5:
                return getTimeInMilliSeconds(n * 30, 4); //30 Días como media de mes
            case 6:
                return getTimeInMilliSeconds(n * 12, 5);
            default:
                return -1;
        }
    }

    public static int parseData(char time){
        switch (time){
            case 's':
                return 0;
            case 'm':
                return 1;
            case 'h':
                return 2;
            case 'd':
                return 3;
            case 'w':
                return 4;
            case 'o':
                return 5;
            case 'y':
                return 6;
            default:
                return 7;
        }
    }
}
