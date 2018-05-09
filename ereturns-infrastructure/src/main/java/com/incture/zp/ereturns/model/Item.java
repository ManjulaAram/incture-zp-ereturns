package com.incture.zp.ereturns.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_INVOICE_ITEM")
public class Item {
	
	@Id
	@Column(name = "ITEM_ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long itemId;

	@Column(name = "ITEM_CODE", length = 100)
	private String itemCode;
	
	@Column(name = "ITEM_NAME", length = 100)
	private String itemName;
	
	@Column(name = "ITEM_DESC", length = 255)
	private String itemDescription;
	
	@Column(name = "AVAILABLE_QTY", length = 50)
	private String availableQty;

	@Column(name = "NET_VALUE", length = 50)
	private String netValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELIVERY_DATE")
	private Date deliveryDate;
	
	@Column(name = "PLANT", length = 50)
	private String plant;
	
	@Column(name = "SLAES_ORDER_ITEM", length = 50)
	private String salesOrderItem;
	
	@Column(name = "DELIVERY_DOC_ITEM", length = 50)
	private String deliveryDocItem;
	
	@Column(name = "STORE_LOC", length = 50)
	private String storeLoc;
	
	@Column(name = "MATERIAL", length = 50)
	private String material;
	
	@Column(name = "MATERIAL_GROUP", length = 50)
	private String materialGroup;

	@Column(name = "BATCH", length = 50)
	private String batch;
	
	@Column(name = "MATERIAL_DESC", length = 255)
	private String materialDesc;
	
	@Column(name = "PRINCIPAL", length = 50)
	private String pricipal;
	
	@Column(name = "PRICIPAL_CODE", length = 50)
	private String pricipalCode;

	@ManyToOne
	@JoinColumn(name = "HEADER_ID", nullable = false, updatable = false)
	private Header itemData;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(String availableQty) {
		this.availableQty = availableQty;
	}

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	public Header getItemData() {
		return itemData;
	}

	public void setItemData(Header itemData) {
		this.itemData = itemData;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getSalesOrderItem() {
		return salesOrderItem;
	}

	public void setSalesOrderItem(String salesOrderItem) {
		this.salesOrderItem = salesOrderItem;
	}

	public String getDeliveryDocItem() {
		return deliveryDocItem;
	}

	public void setDeliveryDocItem(String deliveryDocItem) {
		this.deliveryDocItem = deliveryDocItem;
	}

	public String getStoreLoc() {
		return storeLoc;
	}

	public void setStoreLoc(String storeLoc) {
		this.storeLoc = storeLoc;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getPricipal() {
		return pricipal;
	}

	public void setPricipal(String pricipal) {
		this.pricipal = pricipal;
	}

	public String getPricipalCode() {
		return pricipalCode;
	}

	public void setPricipalCode(String pricipalCode) {
		this.pricipalCode = pricipalCode;
	}

}
