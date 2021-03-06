package com.lamichhane.users.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message="First name can't be null")
	@Size(min=2,message="First name must not be less than two character")
	private String firstName;
	
	@NotNull(message="Last name can't be null")
	@Size(min=2,message="Last name must not be less than two character")
	private String lastName;
	
	@NotNull(message="Password can't be null")
	@Size(min=8,max=16,message="Password must be greater than 8 character and less than 16 character")
	private String password;
	
	@NotNull(message="Email can't be null")
	@Email
	private String email;
	
	
	public CreateUserRequestModel() {
		
	}


	public CreateUserRequestModel(String firstName, String lastName, String password, String email) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}


	@Override
	public String toString() {
		return "CreateUserRequestModel [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + "]";
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
	

}
