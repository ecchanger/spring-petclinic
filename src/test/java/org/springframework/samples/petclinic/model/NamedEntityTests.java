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
 * Unit tests for NamedEntity
 */
class NamedEntityTests {

	@Test
	void testNameGetterAndSetter() {
		NamedEntity entity = new TestNamedEntity();

		assertThat(entity.getName()).isNull();

		entity.setName("TestName");
		assertThat(entity.getName()).isEqualTo("TestName");
	}

	@Test
	void testToStringWithName() {
		NamedEntity entity = new TestNamedEntity();
		entity.setName("TestName");
		assertThat(entity.toString()).isEqualTo("TestName");
	}

	@Test
	void testToStringWithNullName() {
		NamedEntity entity = new TestNamedEntity();
		assertThat(entity.toString()).isNull();
	}

	@Test
	void testToStringWithEmptyName() {
		NamedEntity entity = new TestNamedEntity();
		entity.setName("");
		assertThat(entity.toString()).isEqualTo("");
	}

	@Test
	void testInheritanceFromBaseEntity() {
		NamedEntity entity = new TestNamedEntity();
		entity.setId(123);
		entity.setName("TestName");

		assertThat(entity.getId()).isEqualTo(123);
		assertThat(entity.getName()).isEqualTo("TestName");
		assertThat(entity.isNew()).isFalse();
	}

	// Concrete test implementation of NamedEntity
	private static class TestNamedEntity extends NamedEntity {

		// No additional implementation needed for testing

	}

}
