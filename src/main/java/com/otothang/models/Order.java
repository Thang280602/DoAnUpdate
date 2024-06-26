package com.otothang.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "id")
	private User user;
	@Column(name ="addressShip")
	private String addressShip;
	@Column(name ="phone")
	private String phone;
	@Column(name ="status")
	private Integer status;
	@Column(name ="createAt")
	private Date createAt;
	@Column(name ="totalprice")
	private Double totalprice;

	public Order() {
		super();
	}
	public Order(Integer id, User user, String addressShip, String phone, Integer status, Date createAt,Double totalprice) {
		super();
		this.id = id;
		this.user = user;
		this.addressShip = addressShip;
		this.phone = phone;
		this.status = status;
		this.createAt = createAt;
		this.totalprice=totalprice;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAddressShip() {
		return addressShip;
	}
	public void setAddressShip(String addressShip) {
		this.addressShip = addressShip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
}
