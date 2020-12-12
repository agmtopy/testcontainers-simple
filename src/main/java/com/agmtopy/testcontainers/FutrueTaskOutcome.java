package com.agmtopy.testcontainers;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FutrueTaskOutcome {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TaskDemo taskDemo = new TaskDemo();

        Thread t1 = new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + " start...");
                method1(taskDemo);
                taskDemo.state = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + " start...");
                    while (!taskDemo.state) {
                        TimeUnit.SECONDS.sleep(1L);
                    }
                    print(taskDemo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        t1.start();
        countDownLatch.countDown();
        System.out.println(new Scanner(System.in).next());
    }

    private static void method1(TaskDemo taskDemo) {
        try {
            TimeUnit.NANOSECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskDemo.value = LocalDateTime.now().toString();
    }

    private static void print(TaskDemo taskDemo) {
        System.out.println(Thread.currentThread().getName() + "taskDemo.state = " + taskDemo.state);
        System.out.println(Thread.currentThread().getName() + "taskDemo.value = " + taskDemo.value);
    }
}
