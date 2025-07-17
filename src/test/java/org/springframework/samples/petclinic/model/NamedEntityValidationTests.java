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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for NamedEntity validation
 */
class NamedEntityValidationTests {

	private Validator validator;

	@BeforeEach
	void setUp() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		validator = localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidatePetTypeWhenNameEmpty() {
		PetType petType = new PetType();
		petType.setName("");

		Set<ConstraintViolation<PetType>> constraintViolations = validator.validate(petType);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<PetType> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidatePetTypeWhenNameNull() {
		PetType petType = new PetType();
		petType.setName(null);

		Set<ConstraintViolation<PetType>> constraintViolations = validator.validate(petType);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<PetType> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldValidatePetTypeWhenNameNotEmpty() {
		PetType petType = new PetType();
		petType.setName("Dog");

		Set<ConstraintViolation<PetType>> constraintViolations = validator.validate(petType);

		assertThat(constraintViolations).isEmpty();
	}

	@Test
	void shouldNotValidateSpecialtyWhenNameEmpty() {
		Specialty specialty = new Specialty();
		specialty.setName("");

		Set<ConstraintViolation<Specialty>> constraintViolations = validator.validate(specialty);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Specialty> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateSpecialtyWhenNameNull() {
		Specialty specialty = new Specialty();
		specialty.setName(null);

		Set<ConstraintViolation<Specialty>> constraintViolations = validator.validate(specialty);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Specialty> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldValidateSpecialtyWhenNameNotEmpty() {
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");

		Set<ConstraintViolation<Specialty>> constraintViolations = validator.validate(specialty);

		assertThat(constraintViolations).isEmpty();
	}

	@Test
	void shouldValidatePetTypeWithWhitespaceOnlyName() {
		PetType petType = new PetType();
		petType.setName("   ");

		Set<ConstraintViolation<PetType>> constraintViolations = validator.validate(petType);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<PetType> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

}
