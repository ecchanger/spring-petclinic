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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator validator;

	@BeforeEach
	void setUp() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		validator = createValidator();
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {
		Person person = new TestPerson();
		person.setFirstName("");
		person.setLastName("smith");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("firstName");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWhenLastNameEmpty() {
		Person person = new TestPerson();
		person.setFirstName("John");
		person.setLastName("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("lastName");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWhenFirstAndLastNameEmpty() {
		Person person = new TestPerson();
		person.setFirstName("");
		person.setLastName("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations).hasSize(2);
	}

	@Test
	void shouldValidateWhenFirstAndLastNameNotEmpty() {
		Person person = new TestPerson();
		person.setFirstName("John");
		person.setLastName("Smith");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations).isEmpty();
	}

	@Test
	void shouldNotValidateOwnerWhenAddressEmpty() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("");
		owner.setCity("Springfield");
		owner.setTelephone("1234567890");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("address");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateOwnerWhenCityEmpty() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("123 Main St");
		owner.setCity("");
		owner.setTelephone("1234567890");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("city");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateOwnerWhenTelephoneEmpty() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).hasSize(2);
		// When telephone is empty, it violates both @NotBlank and @Pattern constraints
		assertThat(constraintViolations).extracting("propertyPath")
			.allMatch(path -> path.toString().equals("telephone"));
	}

	@Test
	void shouldNotValidateOwnerWhenTelephoneInvalid() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("123-456-7890");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("telephone");
	}

	@Test
	void shouldNotValidateOwnerWhenTelephoneTooShort() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("123456789");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("telephone");
	}

	@Test
	void shouldNotValidateOwnerWhenTelephoneTooLong() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("12345678901");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("telephone");
	}

	@Test
	void shouldValidateOwnerWhenAllFieldsValid() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Smith");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("1234567890");

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

		assertThat(constraintViolations).isEmpty();
	}

	@Test
	void shouldNotValidateVisitWhenDescriptionEmpty() {
		Visit visit = new Visit();
		visit.setDescription("");

		Set<ConstraintViolation<Visit>> constraintViolations = validator.validate(visit);

		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Visit> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("description");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldValidateVisitWhenDescriptionNotEmpty() {
		Visit visit = new Visit();
		visit.setDescription("Regular checkup");

		Set<ConstraintViolation<Visit>> constraintViolations = validator.validate(visit);

		assertThat(constraintViolations).isEmpty();
	}

	// Concrete test implementation of Person
	private static class TestPerson extends Person {

		// No additional implementation needed for testing

	}

}
