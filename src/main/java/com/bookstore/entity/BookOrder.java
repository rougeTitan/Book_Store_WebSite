package com.bookstore.entity;


import javax.persistence.*;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * BookOrder generated by hbm2java
 */


@Entity
@Table(name = "book_order", catalog = "bookstoredb")
@NamedQueries({ @NamedQuery(name = "BookOrder.findAll", query = "SELECT bo FROM BookOrder bo ORDER BY bo.orderDate DESC"),
@NamedQuery(name = "BookOrder.findByCustomer", query = "SELECT bo FROM BookOrder bo WHERE bo.customer.customerId=:customerId ORDER By bo.orderDate DESC"),
@NamedQuery (name = "BookOrder.findByIdAndCustomer", query = "SELECT bo FROM BookOrder bo WHERE bo.orderId = :orderId AND bo.customer.customerId =:customerId "),
@NamedQuery(name = "BookOrder.countAll", query = "SELECT Count(*) FROM BookOrder")})
public class BookOrder implements java.io.Serializable {


	private int orderId;
	private Customer customer;
	private Date orderDate;
	private String addressLine1;
	private String addressLine2;
	private String firstname;
	private String lastname;
	private String phone;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	private String paymentMethod;
	private float total;
	private String status;

	private float subtotal;
	private float shippingFee;
	private float tax;
	private Set <OrderDetail> orderDetails = new HashSet <OrderDetail>(0);

	public BookOrder(int orderId, Customer customer, Date orderDate, String addressLine1, String firstname,
					 String phone, String paymentMethod, float total, String status) {
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.addressLine1 = addressLine1;
		this.firstname = firstname;
		this.lastname = firstname;
		this.phone = phone;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.status = status;
	}

	public BookOrder(int orderId, Customer customer, Date orderDate, String addressLine1, String firstname,
					 String phone, String paymentMethod, float total, String status, Set orderDetails) {
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.addressLine1 = addressLine1;
		this.firstname = firstname;
		this.phone = phone;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	public BookOrder() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id", unique = true, nullable = false)
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date", nullable = false, length = 19)
	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "r_address_line1", nullable = false, length = 256)
	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@Column(name = "r_firstname", nullable = false, length = 30)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "r_phone", nullable = false, length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String recipientPhone) {
		this.phone = recipientPhone;
	}

	@Column(name = "payment_method", nullable = false, length = 20)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "total", nullable = false, precision = 12, scale = 0)
	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.EAGER,targetEntity = OrderDetail.class, mappedBy = "bookOrder",cascade = CascadeType.ALL, orphanRemoval = true)
	public Set getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(Set orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Column(name = "r_lastname", nullable = false, length = 30)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "r_city", nullable = false, length = 32)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "r_state", nullable = false, length = 45)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "r_zipcode", nullable = false, length = 25)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "r_country", nullable = false, length = 4)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Transient
	public String getCountryName() {
		return new Locale("",this.country).getDisplayCountry();
	}

	@Column(name = "subtotal", nullable = false, precision = 12, scale = 0)
	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	@Column(name = "shipping_fee", nullable = false, precision = 12, scale = 0)
	public float getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(float shippingFee) {
		this.shippingFee = shippingFee;
	}

	@Column(name = "tax", nullable = false, precision = 12, scale = 0)
	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	@Column(name = "r_address_line2", nullable = false, length = 256)
	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}


//	public void setBookCopies(Set orderDetails) {
//		this.orderDetails = orderDetails;
//	}
	
	@Transient
	public int getBookCopies() {
		int total =0;
		for (OrderDetail orderDetail:orderDetails) {
			total += orderDetail.getQuantity();
		}
		return total;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookOrder)) return false;
		BookOrder bookOrder = (BookOrder) o;
		return orderId == bookOrder.orderId;
	}

}
