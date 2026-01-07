package com.completable.future;

import java.util.concurrent.CompletableFuture;

public class MultiApiDataFetcher {


    public CompletableFuture<String> fetchWeatherData() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(2000); // Simulate network delay
            return "Weather: Sunny, 25°C";
        });
    }

    public CompletableFuture<String> fetchNewsHeadlines() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(3000); // Simulate network delay
            return "News: Java 25 Released!";
        });
    }

    public CompletableFuture<String> fetchStockPrices() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(1500); // Simulate network delay
            return "Stocks: AAPL - $150, GOOGL - $2800";
        });
    }

    public static void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MultiApiDataFetcher fetcher = new MultiApiDataFetcher();

        //combine multiple independent future (more than 2) -> allOf(n task)
        //-> weatherDetailsAPI
        CompletableFuture<String> weatherFuture = fetcher.fetchWeatherData();
        //-> news apis
        CompletableFuture<String> newsFuture = fetcher.fetchNewsHeadlines();
        //-> stockPrice apis
        CompletableFuture<String> stockPriceFuture = fetcher.fetchStockPrices();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(weatherFuture, newsFuture, stockPriceFuture);
        allFutures.thenRun(() -> {
            System.out.println("Aggregated data : ");
            System.out.println(weatherFuture.join());
            System.out.println(newsFuture.join());
            System.out.println(stockPriceFuture.join());
        }).join();

    }

}
