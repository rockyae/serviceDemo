package com.example.reactor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureAllOfDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 演示 CompletableFuture.allOf 的阻塞与非阻塞行为 ===\n");

        ExecutorService executor = Executors.newFixedThreadPool(3);
        AtomicInteger taskCounter = new AtomicInteger(1);

        // 创建3个异步任务
        CompletableFuture<String> task1 = createAsyncTask("任务1", 2000, taskCounter.getAndIncrement(), executor);
        CompletableFuture<String> task2 = createAsyncTask("任务2", 1500, taskCounter.getAndIncrement(), executor);
        CompletableFuture<String> task3 = createAsyncTask("任务3", 1000, taskCounter.getAndIncrement(), executor);

        // 1. 使用 allOf 组合任务 - 这是非阻塞的
        System.out.println("1. 使用 allOf 组合任务（立即返回，不阻塞）...");
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);

        // 立即继续执行，证明 allOf 是非阻塞的
        System.out.println("   allOf 调用后立即继续执行，耗时: " + (System.currentTimeMillis() - startTime) + "ms");

        // 2. 注册非阻塞回调
        System.out.println("\n2. 注册非阻塞回调...");
        allTasks.thenRun(() -> {
            System.out.println("   [回调线程] 所有任务完成！总耗时: " + (System.currentTimeMillis() - startTime) + "ms");
            try {
                // 获取各个任务的结果
                System.out.println("   [回调线程] 任务1结果: " + task1.get());
                System.out.println("   [回调线程] 任务2结果: " + task2.get());
                System.out.println("   [回调线程] 任务3结果: " + task3.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 3. 主线程继续执行其他工作
        System.out.println("\n3. 主线程继续执行其他工作...");
        for (int i = 1; i <= 5; i++) {
            Thread.sleep(300);
            System.out.println("   主线程工作进度: " + i + "/5");
        }

        // 4. 演示阻塞行为 - 调用 get() 会阻塞
        System.out.println("\n4. 演示阻塞行为 - 调用 allTasks.get()...");
        long getStartTime = System.currentTimeMillis();

        // 这里会阻塞，直到所有任务完成
        allTasks.get();
        System.out.println("   get() 返回，阻塞等待耗时: " + (System.currentTimeMillis() - getStartTime) + "ms");

        // 5. 另一种阻塞方式 - join()
        System.out.println("\n5. 创建新的一批任务演示 join()...");
        CompletableFuture<Void> newAllTasks = CompletableFuture.allOf(
                createAsyncTask("新任务A", 800, taskCounter.getAndIncrement(), executor),
                createAsyncTask("新任务B", 1200, taskCounter.getAndIncrement(), executor)
        );

        System.out.println("   调用 join() 会阻塞当前线程...");
        newAllTasks.join();  // 阻塞直到所有任务完成
        System.out.println("   join() 已返回，新任务完成");

        // 6. 使用 orTimeout 设置超时
        System.out.println("\n6. 演示带超时的 allOf...");
        CompletableFuture<String> longTask = createAsyncTask("长任务", 5000, taskCounter.getAndIncrement(), executor);
        CompletableFuture<Void> withTimeout = CompletableFuture.allOf(longTask)
                .orTimeout(2000, TimeUnit.MILLISECONDS)  // 2秒超时
                .exceptionally(ex -> {
                    System.out.println("   任务超时: " + ex.getMessage());
                    return null;
                });

        try {
            withTimeout.get();
        } catch (Exception e) {
            System.out.println("   捕获异常: " + e.getCause().getMessage());
        }

        // 关闭线程池
        executor.shutdown();
        executor.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("\n演示完成。");
    }

    private static CompletableFuture<String> createAsyncTask(String name, int delayMs, int taskId, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("   [任务" + taskId + "-" + name + "] 开始执行，预计耗时: " + delayMs + "ms");
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return name + " 被中断";
            }
            System.out.println("   [任务" + taskId + "-" + name + "] 完成");
            return name + " 的结果";
        }, executor);
    }
}
