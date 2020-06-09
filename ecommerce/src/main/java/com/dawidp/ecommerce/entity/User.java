package com.dawidp.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(min=5, max = 15)
	private String username;
    @NotBlank
    @Size(min=6, max = 100)
	private String password;
    @NotBlank
    @Size(min=3, max = 50)
	private String name;
	@NotBlank
	@Size(min=5, max = 20)
	private String surname;
	@NotBlank
	private String country;
	@NotBlank
	private String city;
    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
	private String email;
    private String role;

}
