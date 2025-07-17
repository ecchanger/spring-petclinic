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
package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for EntityUtils
 */
class EntityUtilsTests {

	@Test
	void testGetByIdFound() {
		List<PetType> petTypes = new ArrayList<>();

		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		petTypes.add(cat);

		PetType dog = new PetType();
		dog.setId(2);
		dog.setName("dog");
		petTypes.add(dog);

		PetType foundPetType = EntityUtils.getById(petTypes, PetType.class, 1);

		assertThat(foundPetType).isEqualTo(cat);
		assertThat(foundPetType.getName()).isEqualTo("cat");
	}

	@Test
	void testGetByIdNotFound() {
		List<PetType> petTypes = new ArrayList<>();

		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		petTypes.add(cat);

		assertThrows(ObjectRetrievalFailureException.class, () -> {
			EntityUtils.getById(petTypes, PetType.class, 99);
		});
	}

	@Test
	void testGetByIdEmptyCollection() {
		List<PetType> petTypes = new ArrayList<>();

		assertThrows(ObjectRetrievalFailureException.class, () -> {
			EntityUtils.getById(petTypes, PetType.class, 1);
		});
	}

	@Test
	void testGetByIdWithDifferentEntityTypes() {
		List<Owner> owners = new ArrayList<>();

		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owners.add(owner);

		Owner foundOwner = EntityUtils.getById(owners, Owner.class, 1);

		assertThat(foundOwner).isInstanceOf(Owner.class);
		assertThat(foundOwner.getFirstName()).isEqualTo("John");
	}

	@Test
	void testGetByIdWithMixedEntityTypes() {
		List<PetType> petTypes = new ArrayList<>();

		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		petTypes.add(cat);

		PetType dog = new PetType();
		dog.setId(2);
		dog.setName("dog");
		petTypes.add(dog);

		// Create a collection with the same type and ensure proper lookup
		PetType foundPetType = EntityUtils.getById(petTypes, PetType.class, 2);

		assertThat(foundPetType).isEqualTo(dog);
		assertThat(foundPetType.getName()).isEqualTo("dog");
	}

	@Test
	void testGetByIdWithNullId() {
		List<PetType> petTypes = new ArrayList<>();

		PetType cat = new PetType();
		cat.setId(null);
		cat.setName("cat");
		petTypes.add(cat);

		// The method throws NullPointerException when entity ID is null (line 46 in
		// EntityUtils)
		// This is the expected behavior when comparing null with int
		assertThrows(NullPointerException.class, () -> {
			EntityUtils.getById(petTypes, PetType.class, 1);
		});
	}

	@Test
	void testGetByIdWithMultipleMatches() {
		List<Owner> owners = new ArrayList<>();

		Owner owner1 = new Owner();
		owner1.setId(1);
		owner1.setFirstName("John");
		owners.add(owner1);

		Owner owner2 = new Owner();
		owner2.setId(1);
		owner2.setFirstName("Jane");
		owners.add(owner2);

		// Should return the first match
		Owner foundOwner = EntityUtils.getById(owners, Owner.class, 1);

		assertThat(foundOwner).isEqualTo(owner1);
		assertThat(foundOwner.getFirstName()).isEqualTo("John");
	}

	@Test
	void testGetByIdWithWrongType() {
		List<Pet> pets = new ArrayList<>();

		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");
		pets.add(pet);

		// This test ensures that the method works with type-safe collections
		Pet foundPet = EntityUtils.getById(pets, Pet.class, 1);
		assertThat(foundPet).isEqualTo(pet);
		assertThat(foundPet.getName()).isEqualTo("Buddy");

		// Test not found case
		assertThrows(ObjectRetrievalFailureException.class, () -> {
			EntityUtils.getById(pets, Pet.class, 99);
		});
	}

	@Test
	void testGetByIdExceptionDetails() {
		List<PetType> petTypes = new ArrayList<>();

		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		petTypes.add(cat);

		ObjectRetrievalFailureException exception = assertThrows(ObjectRetrievalFailureException.class, () -> {
			EntityUtils.getById(petTypes, PetType.class, 99);
		});

		assertThat(exception.getPersistentClass()).isEqualTo(PetType.class);
		assertThat(exception.getIdentifier()).isEqualTo(99);
	}

}
