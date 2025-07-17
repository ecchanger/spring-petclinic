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
import org.springframework.util.SerializationUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for Vet
 */
class VetTests {

	private Vet vet;

	@BeforeEach
	void setUp() {
		vet = new Vet();
	}

	@Test
	void testSerialization() {
		Vet vet = new Vet();
		vet.setFirstName("Zaphod");
		vet.setLastName("Beeblebrox");
		vet.setId(123);
		@SuppressWarnings("deprecation")
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());
	}

	@Test
	void testInheritanceFromPerson() {
		vet.setId(123);
		vet.setFirstName("John");
		vet.setLastName("Doe");

		assertThat(vet.getId()).isEqualTo(123);
		assertThat(vet.getFirstName()).isEqualTo("John");
		assertThat(vet.getLastName()).isEqualTo("Doe");
		assertThat(vet.isNew()).isFalse();
	}

	@Test
	void testGetSpecialtiesReturnsEmptyList() {
		List<Specialty> specialties = vet.getSpecialties();
		assertThat(specialties).isEmpty();
	}

	@Test
	void testAddSpecialty() {
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");

		vet.addSpecialty(specialty);

		assertThat(vet.getSpecialties()).hasSize(1);
		assertThat(vet.getSpecialties().get(0)).isEqualTo(specialty);
	}

	@Test
	void testAddMultipleSpecialties() {
		Specialty surgery = new Specialty();
		surgery.setName("Surgery");

		Specialty dentistry = new Specialty();
		dentistry.setName("Dentistry");

		vet.addSpecialty(surgery);
		vet.addSpecialty(dentistry);

		assertThat(vet.getSpecialties()).hasSize(2);
		assertThat(vet.getSpecialties()).contains(surgery, dentistry);
	}

	@Test
	void testGetSpecialtiesAreSorted() {
		Specialty surgery = new Specialty();
		surgery.setName("Surgery");

		Specialty dentistry = new Specialty();
		dentistry.setName("Dentistry");

		Specialty radiology = new Specialty();
		radiology.setName("Radiology");

		// Add in non-alphabetical order
		vet.addSpecialty(surgery);
		vet.addSpecialty(dentistry);
		vet.addSpecialty(radiology);

		List<Specialty> specialties = vet.getSpecialties();

		assertThat(specialties).hasSize(3);
		assertThat(specialties.get(0).getName()).isEqualTo("Dentistry");
		assertThat(specialties.get(1).getName()).isEqualTo("Radiology");
		assertThat(specialties.get(2).getName()).isEqualTo("Surgery");
	}

	@Test
	void testGetNrOfSpecialtiesWithNoSpecialties() {
		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
	}

	@Test
	void testGetNrOfSpecialtiesWithOneSpecialty() {
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");
		vet.addSpecialty(specialty);

		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
	}

	@Test
	void testGetNrOfSpecialtiesWithMultipleSpecialties() {
		Specialty surgery = new Specialty();
		surgery.setName("Surgery");

		Specialty dentistry = new Specialty();
		dentistry.setName("Dentistry");

		vet.addSpecialty(surgery);
		vet.addSpecialty(dentistry);

		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
	}

	@Test
	void testAddDuplicateSpecialty() {
		Specialty surgery = new Specialty();
		surgery.setName("Surgery");

		vet.addSpecialty(surgery);
		vet.addSpecialty(surgery);

		// Sets don't allow duplicates
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(vet.getSpecialties()).hasSize(1);
	}

}
