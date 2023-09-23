package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ImportLibraryMemberDTO {
    @Expose
    @Size(min = 2 , max = 40)
    private String address;
    @Expose
    @Size(min = 2 , max = 30)
    @NotNull
    private String firstName;
    @Expose
    @Size(min = 2 , max = 30)
    @NotNull
    private String lastName;
    @Expose
    @Size(min = 2 , max = 20)
    @NotNull
    private String phoneNumber;

    public ImportLibraryMemberDTO() {
    }

    public String getAddress() {
        return address;
    }

    public ImportLibraryMemberDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ImportLibraryMemberDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ImportLibraryMemberDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ImportLibraryMemberDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
