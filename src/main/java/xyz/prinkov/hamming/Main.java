package xyz.prinkov.hamming;

/**
 * Created by akaroot on 19.11.16.
 */
public class Main {
    public static void main(String[] args) {
        Hamming ham = new Hamming();
        String test = ham.code("0101011110101010101001100111101010101");
        Frame f = new Frame("Код Хемминга");
    }

}
