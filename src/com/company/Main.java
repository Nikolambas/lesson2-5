package com.company;

public class Main {
    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    public static void firstMethod() {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public static void secondMethod() {
        int size = 10_000_000;
        int half = size/2;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        float[]firstHalf = new float[half];
        float[]secondHalf = new float[half];
        System.arraycopy(arr,0,firstHalf,0,half);
        System.arraycopy(arr,half,secondHalf,0,half);
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < firstHalf.length; i++) {
                    firstHalf[i] = (float) (firstHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        };
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < secondHalf.length; i++) {
                    secondHalf[i] = (float) (secondHalf[i] * Math.sin(0.2f + (i+half) / 5) * Math.cos(0.2f + (i+half) / 5) * Math.cos(0.4f + (i+half) / 2));
                }
            }
        };
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(firstHalf,0,arr,0,half);
        System.arraycopy(secondHalf,0,arr,half,half);
        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

}
