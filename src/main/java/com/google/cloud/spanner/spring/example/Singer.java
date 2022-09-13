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

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import java.util.Random;

@Table(name = "Singers")
public class Singer {
  @PrimaryKey
  String id;

  String firstName;

  String lastName;

  private static final Random RANDOM = new Random();
  
  static String getRandomFirstName() {
    return RANDOM_NAMES[RANDOM.nextInt(RANDOM_NAMES.length)].split(" ")[0];
  }
  
  static String getRandomLastName() {
    return RANDOM_NAMES[RANDOM.nextInt(RANDOM_NAMES.length)].split(" ")[1];
  }

  private static final String[] RANDOM_NAMES =
      ("Isabella Herring,"
          + "Leo Owens,"
          + "Gregg Thompson,"
          + "Lilian Bowman,"
          + "Kerry Garrison,"
          + "Dale Solomon,"
          + "Cletus Obrien,"
          + "Victoria Fry,"
          + "Augustine Ali,"
          + "Warner Thomas,"
          + "Joe Franco,"
          + "Otis Porter,"
          + "Janelle Diaz,"
          + "Marquita Mccarthy,"
          + "Lorie Mckay,"
          + "Gerard Stewart,"
          + "Pauline Simpson,"
          + "Cleo Bentley,"
          + "Judith Stevenson,"
          + "Rodney Sexton,"
          + "Merlin Compton,"
          + "Les Valdez,"
          + "Lenard Williamson,"
          + "Ramona Beltran,"
          + "Christine Dillon,"
          + "Abraham Shea,"
          + "Madge Collins,"
          + "Napoleon Mcclure,"
          + "Quincy Medina,"
          + "Lee Stark,"
          + "Gail Ashley,"
          + "Susanna Washington,"
          + "Hunter Noble,"
          + "Jermaine Lester,"
          + "Marcelino Hale,"
          + "Elvia Meyer,"
          + "Matthew Hansen,"
          + "Corey Meyers,"
          + "Edward Spencer,"
          + "Cyrus Adams,"
          + "Elsie Melendez,"
          + "Miguel Watkins,"
          + "Adalberto King,"
          + "Bernadine Rosales,"
          + "Milo Joyce,"
          + "Dong Golden,"
          + "Carly Kramer,"
          + "Cristina Buckley,"
          + "Jerri Webster,"
          + "Wallace Walker,"
          + "Lelia Sutton,"
          + "Nicky Middleton,"
          + "Annabelle Moore,"
          + "Katharine Herman,"
          + "Evangelina Fernandez,"
          + "Felix Best,"
          + "Jc Arnold,"
          + "Ezequiel Myers,"
          + "Josefa Pena,"
          + "Long Acevedo,"
          + "Arlene Waters,"
          + "Ilene Walters,"
          + "Regina Drake,"
          + "Consuelo Gomez,"
          + "Dwayne Weeks,"
          + "Monte Church,"
          + "Dennis Pope,"
          + "Andrea Cain,"
          + "Andre Bender,"
          + "Guadalupe Strong").split(",");

}
