/*
 * Copyright 2017-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package springdata.jdbc.bug;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Demonstrates bug on @Embeddable with Aggreagte
 */
@SpringBootTest(classes = BugConfiguration.class)
@AutoConfigureJdbc
class BugEmbeddableTests {

    @Autowired
    ParentRepository repository;

    @Autowired
    ChildRepository childRepository;

    @Test
    void test() {
        Child bobJunior = childRepository.save(new Child(null, "Bob JR"));
        AggregateReference<Child, Long> bobRef = AggregateReference.to(bobJunior.id());
        // create some categories
        var parent1 = repository.save(new Parent(null, "Bob", new Nested(bobRef, "legitim")));
        var parent = repository.findById(parent1.id()).orElse(null);//failed because sql query is not correct the embedded prefix is missing: "PARENT"."CHILD_ID" must be "PARENT"."NESTED_CHILD_ID"
        // SELECT "PARENT"."ID" AS "ID", "PARENT"."NAME" AS "NAME", "PARENT"."NESTED_TYPE" AS "NESTED_TYPE", "PARENT"."CHILD_ID" AS "CHILD_ID" FROM "PARENT" WHERE "PARENT"."ID" = ?

        assertNotNull(parent);
        assertNotNull(parent.nested());
        assertEquals("Bob", parent.name());
        assertEquals(bobRef, parent.nested().child_id());
        assertEquals("legitim", parent.nested().type());
    }



}
