package com.example.reactor;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class FutureVsCompletableFuture {

    public static void main(String[] args) throws Exception {
//        System.out.println("=== 传统Future示例 ===");
//        testTraditionalFuture();

        System.out.println("\n=== CompletableFuture示例 ===");
        testCompletableFuture();
    }

    // 1. 传统Future的使用
    public static void testTraditionalFuture() throws Exception {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // 提交任务，获得Future
        Future<String> future = executor.submit(() -> {
            Thread.sleep(1000); // 模拟耗时操作
            return "Hello from Future";
        });

        // Future的限制1: 获取结果会阻塞
        System.out.println("任务已提交，等待结果...");
        String result = future.get(); // 阻塞直到获取结果
        System.out.println("Future结果: " + result);

        // Future的限制2: 无法方便地组合多个任务
        Future<Integer> future2 = executor.submit(() -> {
            Thread.sleep(500);
            return 42;
        });

        // 手动合并结果
        String combined = result + " and " + future2.get();
        System.out.println("手动合并: " + combined);

        executor.shutdown();
    }

    // 2. CompletableFuture的使用
    public static void testCompletableFuture() throws Exception {
        // 示例1: 异步执行
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        // 非阻塞处理: 结果准备好时自动回调
        cf1.thenAccept(result -> {
            System.out.println("CompletableFuture结果: " + result);
        });

       // Thread.sleep(4000);
        // 示例2: 任务链式组合
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "World";
        });

        // 自动组合两个异步结果
        CompletableFuture<String> combined = cf1.thenCombine(cf2, (r1, r2) -> {
            return r1 + " " + r2;
        });

        // 获取最终结果
        System.out.println("链式组合结果: " + combined.get());
        Thread.sleep(3000);
//
//        // 示例3: 异常处理
//        CompletableFuture<Integer> cf3 = CompletableFuture.supplyAsync(() -> {
//            if (Math.random() > 0.5) {
//                throw new RuntimeException("模拟异常");
//            }
//            return 100;
//        }).exceptionally(ex -> {
//            System.out.println("捕获到异常: " + ex.getMessage());
//            return 0; // 返回默认值
//        });
//
//        System.out.println("异常处理结果: " + cf3.get());
//
//        // 示例4: 多任务组合
//        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> "Task1");
//        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "Task2");
//        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> "Task3");
//
//        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
//        allTasks.thenRun(() -> {
//            System.out.println("所有任务完成!");
//            try {
//                System.out.println("结果: " + task1.get() + ", " + task2.get() + ", " + task3.get());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        // 等待所有异步操作完成
//        Thread.sleep(2000);
    }
}

