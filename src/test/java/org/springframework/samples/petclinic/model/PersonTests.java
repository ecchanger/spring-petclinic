/*
 * Copyright 2012-2025 the original author or authors.
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
package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for Person
 */
class PersonTests {

	@Test
	void testFirstNameGetterAndSetter() {
		Person person = new TestPerson();

		assertThat(person.getFirstName()).isNull();

		person.setFirstName("John");
		assertThat(person.getFirstName()).isEqualTo("John");
	}

	@Test
	void testLastNameGetterAndSetter() {
		Person person = new TestPerson();

		assertThat(person.getLastName()).isNull();

		person.setLastName("Doe");
		assertThat(person.getLastName()).isEqualTo("Doe");
	}

	@Test
	void testFullPersonDetails() {
		Person person = new TestPerson();
		person.setId(123);
		person.setFirstName("John");
		person.setLastName("Doe");

		assertThat(person.getId()).isEqualTo(123);
		assertThat(person.getFirstName()).isEqualTo("John");
		assertThat(person.getLastName()).isEqualTo("Doe");
		assertThat(person.isNew()).isFalse();
	}

	@Test
	void testInheritanceFromBaseEntity() {
		Person person = new TestPerson();
		assertThat(person.isNew()).isTrue();

		person.setId(456);
		assertThat(person.isNew()).isFalse();
	}

	// Concrete test implementation of Person
	private static class TestPerson extends Person {

		// No additional implementation needed for testing

	}

}
