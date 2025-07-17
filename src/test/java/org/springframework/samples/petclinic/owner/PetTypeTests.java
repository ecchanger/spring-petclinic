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
package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for PetType
 */
class PetTypeTests {

	private PetType petType;

	@BeforeEach
	void setUp() {
		petType = new PetType();
	}

	@Test
	void testInheritanceFromNamedEntity() {
		petType.setId(123);
		petType.setName("Dog");

		assertThat(petType.getId()).isEqualTo(123);
		assertThat(petType.getName()).isEqualTo("Dog");
		assertThat(petType.toString()).isEqualTo("Dog");
		assertThat(petType.isNew()).isFalse();
	}

	@Test
	void testNewPetType() {
		assertThat(petType.getId()).isNull();
		assertThat(petType.getName()).isNull();
		assertThat(petType.isNew()).isTrue();
	}

	@Test
	void testPetTypeWithDifferentNames() {
		petType.setName("Cat");
		assertThat(petType.getName()).isEqualTo("Cat");
		assertThat(petType.toString()).isEqualTo("Cat");

		petType.setName("Bird");
		assertThat(petType.getName()).isEqualTo("Bird");
		assertThat(petType.toString()).isEqualTo("Bird");
	}

	@Test
	void testPetTypeWithEmptyName() {
		petType.setName("");
		assertThat(petType.getName()).isEqualTo("");
		assertThat(petType.toString()).isEqualTo("");
	}

}
