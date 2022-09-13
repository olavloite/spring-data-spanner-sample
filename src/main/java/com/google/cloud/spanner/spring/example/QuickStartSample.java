/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.spanner.spring.example;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A quick-start sample that uses Spring Data Cloud Spanner to perform read, write, and DDL
 * operations.
 */
@SpringBootApplication
public class QuickStartSample implements CommandLineRunner {
  private static final Logger LOG = LoggerFactory.getLogger(QuickStartSample.class);

  private static final int NUM_CONCURRENT_TRANSACTIONS = 200;

  @Autowired SpannerSchemaToolsSample spannerSchemaToolsSample;

  @Autowired SpannerTemplateSample spannerTemplateSample;

  public static void main(String[] args) {
    LOG.info("Starting Spring Data Cloud Spanner Sample.");
    SpringApplication.run(QuickStartSample.class, args);
    LOG.info("Spring Data Cloud Spanner Sample finished running.");
  }

  public void run(String... args) throws ExecutionException, InterruptedException {
    LOG.info("(SpannerSchemaToolsSample): Creating database tables if they don't exist.");
    spannerSchemaToolsSample.createTableIfNotExists();
    LOG.info("(SpannerSchemaToolsSample): Delete all existing singers.");
    spannerTemplateSample.deleteAllSingers();

    Random random = new Random();
    ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(
        NUM_CONCURRENT_TRANSACTIONS));
    List<ListenableFuture<Integer>> futures = new ArrayList<>(NUM_CONCURRENT_TRANSACTIONS);

    Stopwatch watch = Stopwatch.createStarted();
    for (int transactionNum = 0; transactionNum < NUM_CONCURRENT_TRANSACTIONS; transactionNum++) {
      futures.add(executorService.submit(
          () -> {
            int numSingers = random.nextInt(5) + 1;
            Builder<Singer> singers = ImmutableList.builder();
            for (int i = 0; i < numSingers; i++) {
              Singer singer = new Singer();
              singer.id = UUID.randomUUID().toString();
              singer.firstName = Singer.getRandomFirstName();
              singer.lastName = Singer.getRandomLastName();
              singers.add(singer);
            }

            LOG.info("(SpannerTemplateSample): Saving a list of singers.");
            spannerTemplateSample.insertSingers(singers.build());

            LOG.info("(SpannerTemplateSample): Listing all singers.");
            return spannerTemplateSample.listSingers();
          }));
    }
    // Wait until all transactions have finished.
    List<Integer> allResults = Futures.allAsList(futures).get();
    int numSingers = allResults.stream().mapToInt(v->v).max().orElse(0);
    LOG.info(String.format("Finished running sample. %d singers were inserted", numSingers));
    LOG.info(String.format("Sample run time: %sms", watch.elapsed(TimeUnit.MILLISECONDS)));
    executorService.shutdown();
  }
}