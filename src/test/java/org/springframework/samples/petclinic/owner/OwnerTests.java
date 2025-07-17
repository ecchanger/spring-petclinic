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
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for Owner
 */
class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setUp() {
		owner = new Owner();
	}

	@Test
	void testAddressGetterAndSetter() {
		assertThat(owner.getAddress()).isNull();

		owner.setAddress("123 Main St");
		assertThat(owner.getAddress()).isEqualTo("123 Main St");
	}

	@Test
	void testCityGetterAndSetter() {
		assertThat(owner.getCity()).isNull();

		owner.setCity("Springfield");
		assertThat(owner.getCity()).isEqualTo("Springfield");
	}

	@Test
	void testTelephoneGetterAndSetter() {
		assertThat(owner.getTelephone()).isNull();

		owner.setTelephone("5551234567");
		assertThat(owner.getTelephone()).isEqualTo("5551234567");
	}

	@Test
	void testGetPetsReturnsEmptyList() {
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testAddPetWithNewPet() {
		Pet pet = new Pet();
		pet.setName("Buddy");

		owner.addPet(pet);

		assertThat(owner.getPets()).hasSize(1);
		assertThat(owner.getPets().get(0)).isEqualTo(pet);
	}

	@Test
	void testAddPetWithExistingPet() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");

		owner.addPet(pet);

		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testGetPetByName() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("Buddy");
		assertThat(foundPet).isEqualTo(pet);
	}

	@Test
	void testGetPetByNameCaseInsensitive() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("buddy");
		assertThat(foundPet).isEqualTo(pet);
	}

	@Test
	void testGetPetByNameNotFound() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("Max");
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetByNameWithNullName() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet((String) null);
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetById() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");
		owner.getPets().add(pet);

		Pet foundPet = owner.getPet(1);
		assertThat(foundPet).isEqualTo(pet);
	}

	@Test
	void testGetPetByIdNotFound() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");
		owner.getPets().add(pet);

		Pet foundPet = owner.getPet(2);
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetByIdWithNewPet() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet(1);
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetByNameIgnoreNew() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("Buddy", true);
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetByNameDontIgnoreNew() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("Buddy", false);
		assertThat(foundPet).isEqualTo(pet);
	}

	@Test
	void testAddVisit() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");
		owner.getPets().add(pet);

		Visit visit = new Visit();
		visit.setDescription("Regular checkup");

		owner.addVisit(1, visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits().iterator().next()).isEqualTo(visit);
	}

	@Test
	void testAddVisitWithNullPetId() {
		Visit visit = new Visit();
		visit.setDescription("Regular checkup");

		assertThrows(IllegalArgumentException.class, () -> owner.addVisit(null, visit));
	}

	@Test
	void testAddVisitWithNullVisit() {
		assertThrows(IllegalArgumentException.class, () -> owner.addVisit(1, null));
	}

	@Test
	void testAddVisitWithInvalidPetId() {
		Visit visit = new Visit();
		visit.setDescription("Regular checkup");

		assertThrows(IllegalArgumentException.class, () -> owner.addVisit(999, visit));
	}

	@Test
	void testToString() {
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("5551234567");

		String result = owner.toString();

		assertThat(result).contains("id = 1");
		assertThat(result).contains("new = false");
		assertThat(result).contains("lastName = 'Doe'");
		assertThat(result).contains("firstName = 'John'");
		assertThat(result).contains("address = '123 Main St'");
		assertThat(result).contains("city = 'Springfield'");
		assertThat(result).contains("telephone = '5551234567'");
	}

}
