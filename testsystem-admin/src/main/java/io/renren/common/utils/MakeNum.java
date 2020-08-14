package io.renren.common.utils;

public class MakeNum {

    public static int makeNum(int num) {
        if (num == 1) {
            return (int) ((Math.random() * 9 + 1));
        } else if (num == 2) {
            return (int) ((Math.random() * 9 + 1) * 10);
        } else if (num == 3) {
            return (int) ((Math.random() * 9 + 1) * 100);
        } else if (num == 4) {
            return (int) ((Math.random() * 9 + 1) * 1000);
        } else {
            return (int) ((Math.random() * 9 + 1) * 10000);
        }
    }

    public static long addSomeNumber(Object[] arr) {
        int index = (int) (Math.random() * arr.length);
        return (long) arr[index];
    }

}
