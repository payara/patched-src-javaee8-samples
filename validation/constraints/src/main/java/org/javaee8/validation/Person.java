package org.javaee8.validation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author mertcaliskan
 */
class Person {

    @Past(message = "must be a past date")
    private Year yearOfBirth;

    private Optional<@Past LocalDate> marriageAnniversary;

    private List<@NotNull @Email String> emails;

    @Size(min = 8, groups = Default.class)
    @Size(min = 12, groups = Admin.class)
    private String password;

    private Map<@Valid Country, @Valid Address> addressMap = new HashMap<>();


    public Year getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Year yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Optional<LocalDate> getMarriageAnniversary() {
        return marriageAnniversary;
    }

    public void setMarriageAnniversary(Optional<LocalDate> marriageAnniversary) {
        this.marriageAnniversary = marriageAnniversary;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Country, Address> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<Country, Address> addressMap) {
        this.addressMap = addressMap;
    }
}
