/*
 * Copyright (c) 2017 Otávio Santana and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Otavio Santana
 */

package org.jnosql.artemis.demo.se;


import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.mapping.column.ColumnTemplate;
import org.eclipse.jnosql.artemis.cassandra.column.CassandraTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Arrays;
import java.util.Optional;

import static jakarta.nosql.column.ColumnQuery.select;

public class App {


    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Person person = Person.builder().withPhones(Arrays.asList("234", "432"))
                    .withName("Ada Lovelace").withId(1).build();
            ColumnTemplate template = container.select(CassandraTemplate.class).get();
            Person saved = template.insert(person);
            System.out.println("Person saved" + saved);

            ColumnQuery query = select().from("Person").where("id").eq(1L).build();

            Optional<Person> result = template.singleResult(query);
            System.out.println("Entity found: " + result);

        }
    }

    private App() {
    }
}
