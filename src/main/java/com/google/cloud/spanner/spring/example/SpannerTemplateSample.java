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

import com.google.cloud.spanner.KeySet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spring.data.spanner.core.SpannerQueryOptions;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpannerTemplateSample {
  private static final Logger LOG = LoggerFactory.getLogger(SpannerTemplateSample.class);

  @Autowired
  SpannerTemplate spannerTemplate;

  public void deleteAllSingers() {
    this.spannerTemplate.delete(Singer.class, KeySet.all());
  }

  public void insertSingers(Iterable<Singer> singers) {
    this.spannerTemplate.insertAll(singers);
  }

  public int listSingers() {
    // Read all of the singers in the Singers table.
    List<Singer> allSingers = this.spannerTemplate
        .query(Singer.class, Statement.of("SELECT * FROM Singers"),
            new SpannerQueryOptions().setAllowPartialRead(true));

    for (Singer s : allSingers) {
      LOG.info(String.format("Found singer: %s %s", s.firstName, s.lastName));
    }
    return allSingers.size();
  }

}
