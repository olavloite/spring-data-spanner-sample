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

import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpannerSchemaToolsSample {

  @Autowired
  SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

  @Autowired
  SpannerSchemaUtils spannerSchemaUtils;

  public void createTableIfNotExists() {
    if (!this.spannerDatabaseAdminTemplate.tableExists("Singers")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(ImmutableList.of(
          this.spannerSchemaUtils
              .getCreateTableDdlString(Singer.class)),
          true);
    }
  }

  public void dropTables() {
    if (this.spannerDatabaseAdminTemplate.tableExists("Singers")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          ImmutableList.of(this.spannerSchemaUtils.getDropTableDdlString(Singer.class)),
          false);
    }
  }
}
