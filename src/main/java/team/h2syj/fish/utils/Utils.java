package team.h2syj.fish.utils;

public class Utils {

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

}
