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

import com.google.cloud.spanner.Key;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.google.cloud.spring.data.spanner.repository.query.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface SingerRepository extends SpannerRepository<Singer, Key> {
  List<Singer> findByLastName(String lastName);

  int countByFirstName(String firstName);

  int deleteByLastName(String lastName);

  List<Singer> findTop3DistinctByFirstNameAndIdIgnoreCaseOrLastNameOrderByLastNameDesc(
      String firstName, String lastName, long singerId);

  @Query("SELECT * FROM Singers WHERE firstName LIKE '%@fragment';")
  List<Singer> getByQuery(@Param("fragment") String firstNameFragment);
}
