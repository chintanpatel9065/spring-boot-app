package org.chintanpatel.springbootapp.employee;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.chintanpatel.springbootapp.department.Department;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @NotEmpty(message = "Please Provide FirstName")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "Please Provide MiddleName")
    @Column(name = "middle_name")
    private String middleName;

    @NotEmpty(message = "Please Provide LastName")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "Please Select Gender")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotEmpty(message = "Please Select Programming Language")
    @Column(name = "programming_language", nullable = false)
    private String[] programmingLanguage;

    @NotEmpty(message = "Please Provide Email")
    @Email(message = "Please Provide Valid Email")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "Please Provide Mobile Number")
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please Provide Birth Date")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotEmpty(message = "Please Provide UserName")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotEmpty(message = "Please Provide Password")
    @Column(name = "password", nullable = false)
    private String password;

    @Valid
    @ManyToOne
    @NotNull(message = "Please Provide Department")
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Employee() {
    }

    public Employee(Long employeeId, String firstName, String middleName, String lastName, String gender, String[] programmingLanguage, String email, String mobile, LocalDate birthDate, String userName, String password, Department department) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.programmingLanguage = programmingLanguage;
        this.email = email;
        this.mobile = mobile;
        this.birthDate = birthDate;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String[] programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
