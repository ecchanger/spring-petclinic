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
 * Unit tests for Visit
 */
class VisitTests {

	private Visit visit;

	@BeforeEach
	void setUp() {
		visit = new Visit();
	}

	@Test
	void testConstructorSetsCurrentDate() {
		LocalDate today = LocalDate.now();
		Visit newVisit = new Visit();

		assertThat(newVisit.getDate()).isEqualTo(today);
	}

	@Test
	void testDateGetterAndSetter() {
		LocalDate visitDate = LocalDate.of(2023, 6, 15);
		visit.setDate(visitDate);

		assertThat(visit.getDate()).isEqualTo(visitDate);
	}

	@Test
	void testDescriptionGetterAndSetter() {
		assertThat(visit.getDescription()).isNull();

		visit.setDescription("Regular checkup");
		assertThat(visit.getDescription()).isEqualTo("Regular checkup");
	}

	@Test
	void testInheritanceFromBaseEntity() {
		visit.setId(123);

		assertThat(visit.getId()).isEqualTo(123);
		assertThat(visit.isNew()).isFalse();
	}

	@Test
	void testFullVisitDetails() {
		LocalDate visitDate = LocalDate.of(2023, 6, 15);

		visit.setId(1);
		visit.setDate(visitDate);
		visit.setDescription("Annual vaccination");

		assertThat(visit.getId()).isEqualTo(1);
		assertThat(visit.getDate()).isEqualTo(visitDate);
		assertThat(visit.getDescription()).isEqualTo("Annual vaccination");
		assertThat(visit.isNew()).isFalse();
	}

	@Test
	void testOverrideDefaultDate() {
		LocalDate customDate = LocalDate.of(2022, 12, 25);
		visit.setDate(customDate);

		assertThat(visit.getDate()).isEqualTo(customDate);
	}

}
