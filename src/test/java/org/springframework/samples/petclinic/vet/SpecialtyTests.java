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
package org.springframework.samples.petclinic.vet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for Specialty
 */
class SpecialtyTests {

	private Specialty specialty;

	@BeforeEach
	void setUp() {
		specialty = new Specialty();
	}

	@Test
	void testInheritanceFromNamedEntity() {
		specialty.setId(123);
		specialty.setName("Surgery");

		assertThat(specialty.getId()).isEqualTo(123);
		assertThat(specialty.getName()).isEqualTo("Surgery");
		assertThat(specialty.toString()).isEqualTo("Surgery");
		assertThat(specialty.isNew()).isFalse();
	}

	@Test
	void testNewSpecialty() {
		assertThat(specialty.getId()).isNull();
		assertThat(specialty.getName()).isNull();
		assertThat(specialty.isNew()).isTrue();
	}

	@Test
	void testSpecialtyWithDifferentNames() {
		specialty.setName("Dentistry");
		assertThat(specialty.getName()).isEqualTo("Dentistry");
		assertThat(specialty.toString()).isEqualTo("Dentistry");

		specialty.setName("Radiology");
		assertThat(specialty.getName()).isEqualTo("Radiology");
		assertThat(specialty.toString()).isEqualTo("Radiology");
	}

	@Test
	void testSpecialtyWithEmptyName() {
		specialty.setName("");
		assertThat(specialty.getName()).isEqualTo("");
		assertThat(specialty.toString()).isEqualTo("");
	}

	@Test
	void testSpecialtyEquality() {
		Specialty specialty1 = new Specialty();
		specialty1.setId(1);
		specialty1.setName("Surgery");

		Specialty specialty2 = new Specialty();
		specialty2.setId(1);
		specialty2.setName("Surgery");

		// Note: Entity equality is typically based on ID in JPA entities
		// This test verifies that the basic properties are set correctly
		assertThat(specialty1.getId()).isEqualTo(specialty2.getId());
		assertThat(specialty1.getName()).isEqualTo(specialty2.getName());
		assertThat(specialty1.toString()).isEqualTo(specialty2.toString());
	}

}
