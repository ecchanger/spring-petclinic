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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for Pet
 */
class PetTests {

	private Pet pet;

	@BeforeEach
	void setUp() {
		pet = new Pet();
	}

	@Test
	void testNameGetterAndSetter() {
		assertThat(pet.getName()).isNull();

		pet.setName("Buddy");
		assertThat(pet.getName()).isEqualTo("Buddy");
	}

	@Test
	void testBirthDateGetterAndSetter() {
		assertThat(pet.getBirthDate()).isNull();

		LocalDate birthDate = LocalDate.of(2020, 1, 15);
		pet.setBirthDate(birthDate);
		assertThat(pet.getBirthDate()).isEqualTo(birthDate);
	}

	@Test
	void testTypeGetterAndSetter() {
		assertThat(pet.getType()).isNull();

		PetType petType = new PetType();
		petType.setName("Dog");
		pet.setType(petType);
		assertThat(pet.getType()).isEqualTo(petType);
	}

	@Test
	void testGetVisitsReturnsEmptyCollection() {
		assertThat(pet.getVisits()).isEmpty();
	}

	@Test
	void testAddVisit() {
		Visit visit = new Visit();
		visit.setDescription("Regular checkup");

		pet.addVisit(visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits().iterator().next()).isEqualTo(visit);
	}

	@Test
	void testAddMultipleVisits() {
		Visit visit1 = new Visit();
		visit1.setDescription("Regular checkup");

		Visit visit2 = new Visit();
		visit2.setDescription("Vaccination");

		pet.addVisit(visit1);
		pet.addVisit(visit2);

		assertThat(pet.getVisits()).hasSize(2);
		assertThat(pet.getVisits()).contains(visit1, visit2);
	}

	@Test
	void testInheritanceFromNamedEntity() {
		pet.setId(123);
		pet.setName("Buddy");

		assertThat(pet.getId()).isEqualTo(123);
		assertThat(pet.getName()).isEqualTo("Buddy");
		assertThat(pet.toString()).isEqualTo("Buddy");
		assertThat(pet.isNew()).isFalse();
	}

	@Test
	void testFullPetDetails() {
		PetType petType = new PetType();
		petType.setName("Dog");

		LocalDate birthDate = LocalDate.of(2020, 1, 15);

		Visit visit = new Visit();
		visit.setDescription("Regular checkup");

		pet.setId(1);
		pet.setName("Buddy");
		pet.setType(petType);
		pet.setBirthDate(birthDate);
		pet.addVisit(visit);

		assertThat(pet.getId()).isEqualTo(1);
		assertThat(pet.getName()).isEqualTo("Buddy");
		assertThat(pet.getType().getName()).isEqualTo("Dog");
		assertThat(pet.getBirthDate()).isEqualTo(birthDate);
		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits().iterator().next().getDescription()).isEqualTo("Regular checkup");
	}

}
