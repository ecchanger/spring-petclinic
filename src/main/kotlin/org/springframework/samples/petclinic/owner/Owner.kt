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
package org.springframework.samples.petclinic.owner

import org.springframework.core.style.ToStringCreator
import org.springframework.samples.petclinic.Person
import org.springframework.util.Assert
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.NotBlank

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Oliver Drotbohm
 * @author Wick Dynex
 */
@Entity
@Table(name = "owners")
class Owner : Person() {

    @Column(name = "address")
    @NotBlank
    var address: String? = null

    @Column(name = "city")
    @NotBlank
    var city: String? = null

    @Column(name = "telephone")
    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "{telephone.invalid}")
    var telephone: String? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @OrderBy("name")
    private val pets: MutableList<Pet> = ArrayList()

    fun getPets(): List<Pet> = pets

    fun addPet(pet: Pet) {
        if (pet.isNew) {
            pets.add(pet)
        }
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return the Pet with the given name, or null if no such Pet exists for this Owner
     */
    fun getPet(name: String): Pet? {
        return getPet(name, false)
    }

    /**
     * Return the Pet with the given id, or null if none found for this Owner.
     * @param id to test
     * @return the Pet with the given id, or null if no such Pet exists for this Owner
     */
    fun getPet(id: Int): Pet? {
        for (pet in pets) {
            if (!pet.isNew) {
                val compId = pet.id
                if (compId == id) {
                    return pet
                }
            }
        }
        return null
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @param ignoreNew whether to ignore new pets (pets that are not saved yet)
     * @return the Pet with the given name, or null if no such Pet exists for this Owner
     */
    fun getPet(name: String, ignoreNew: Boolean): Pet? {
        for (pet in pets) {
            val compName = pet.name
            if (compName != null && compName.equals(name, ignoreCase = true)) {
                if (!ignoreNew || !pet.isNew) {
                    return pet
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return ToStringCreator(this)
            .append("id", this.id)
            .append("new", this.isNew)
            .append("lastName", this.lastName)
            .append("firstName", this.firstName)
            .append("address", this.address)
            .append("city", this.city)
            .append("telephone", this.telephone)
            .toString()
    }

    /**
     * Adds the given [Visit] to the [Pet] with the given identifier.
     * @param petId the identifier of the [Pet], must not be null.
     * @param visit the visit to add, must not be null.
     */
    fun addVisit(petId: Int, visit: Visit) {
        Assert.notNull(petId, "Pet identifier must not be null!")
        Assert.notNull(visit, "Visit must not be null!")

        val pet = getPet(petId)
        Assert.notNull(pet, "Invalid Pet identifier!")

        pet!!.addVisit(visit)
    }

}