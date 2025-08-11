package org.chintanpatel.springbootapp.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.chintanpatel.springbootapp.department.Department;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @NotEmpty(message = "Please Provide First Name")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "Please Provide Middle Name")
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @NotEmpty(message = "Please Provide Last Name")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "Please Provide Address")
    @Column(name = "address", nullable = false)
    private String address;

    @NotEmpty(message = "Please Provide Email")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "Please Provide Phone Number")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please Provide Date Of Birth")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Please Provide User Name")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotEmpty(message = "Please Provide Password")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @NotNull(message = "Please Select Department")
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Employee() {
    }

    public Employee(Long employeeId, String firstName, String middleName, String lastName, String address, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String password, Department department) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
        this.department = department;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
