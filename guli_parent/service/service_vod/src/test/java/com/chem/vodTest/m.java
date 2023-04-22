package com.chem.vodTest;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1514:22
 */
public class m {
    public static void main(String[] args) {
        int old = (int) (Math.random() * 9 + 1);
        int ge = old % 10;
        int shi = old / 10;
        System.out.printf("%02d",old);
        Scanner scanner = new Scanner(System.in);
        int nold = scanner.nextInt();
        int nge = nold % 10;
        int nshi = nold / 10;
        if(ge==nge&&shi==nshi){
            System.out.println("完全相同");
        }

    }
}
