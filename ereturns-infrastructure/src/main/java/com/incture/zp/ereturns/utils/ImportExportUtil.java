package com.incture.zp.ereturns.utils;

import java.util.HashSet;
import java.util.Set;

import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.User;

public class ImportExportUtil {

	public static User importUserDto(UserDto userDto) {
		User user = new User();
		user.setAddress(userDto.getAddress());
		user.setEmail(userDto.getEmail());
		user.setLotNo(userDto.getLotNo());
		user.setSciId(userDto.getSciId());
		user.setUserCode(userDto.getUserCode());
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		
		Set<Header> headerSet = null;
		if (userDto.getHeaderSet() != null && userDto.getHeaderSet().size() > 0) {
			headerSet = setHeaderDetail(userDto.getHeaderSet(), user);
		}
		
		user.setSetHeader(headerSet);
		return user;
	}
	
	private static Set<Header> setHeaderDetail(Set<HeaderDto> headerSet, User user) {
		Set<Header> setHeader = new HashSet<>();
		
		for(HeaderDto headerDto : headerSet) {
			setHeader.add(importHeaderDto(headerDto, user));
		}
		return setHeader;
	}
	
	public static UserDto exportUserDto(User user, Set<HeaderDto> headerSet) {
		UserDto userDto = new UserDto();
		userDto.setAddress(user.getAddress());
		userDto.setEmail(user.getEmail());
		userDto.setHeaderSet(headerSet);
		userDto.setLotNo(user.getLotNo());
		userDto.setSciId(user.getSciId());
		userDto.setUserCode(user.getUserCode());
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		
		return userDto;
	}
	
	public static Header importHeaderDto(HeaderDto headerDto, User user) {
		Header header = new Header();
		header.setAvailableQty(headerDto.getAvailableQty());
		header.setExpiryDate(headerDto.getExpiryDate());
		header.setHeaderData(user);
		header.setInvoiceDate(headerDto.getInvoiceDate());
		header.setInvoiceNo(headerDto.getInvoiceNo());
		header.setInvoiceSeq(headerDto.getInvoiceSeq());
		header.setNetValue(headerDto.getNetValue());
		
		Set<Item> itemSet = null;
		if (headerDto.getItemSet() != null && headerDto.getItemSet().size() > 0) {
			itemSet = setItemDetail(headerDto.getItemSet(), header);
		}

		header.setSetItem(itemSet);
		
		return header;
	}
	
	public static HeaderDto exportHeaderDto(Header header, Set<ItemDto> setItem) {
		HeaderDto headerDto = new HeaderDto();
		headerDto.setAvailableQty(header.getAvailableQty());
		headerDto.setExpiryDate(header.getExpiryDate());
		headerDto.setInvoiceDate(header.getInvoiceDate());
		headerDto.setInvoiceNo(header.getInvoiceNo());
		headerDto.setInvoiceSeq(header.getInvoiceSeq());
		
		headerDto.setItemSet(setItem);
		headerDto.setNetValue(header.getNetValue());
		
		return headerDto;
	}
	
	private static Set<Item> setItemDetail(Set<ItemDto> itemSet, Header header) {
		Set<Item> setItem = new HashSet<>();
		
		for(ItemDto itemDto : itemSet) {
			setItem.add(importItemDto(itemDto, header));
		}
		return setItem;
	}
	
	public static Item importItemDto(ItemDto itemDto, Header header) {
		Item item = new Item();
		item.setAvailableQty(itemDto.getAvailableQty());
		item.setExpiryDate(itemDto.getExpiryDate());
		item.setItemCode(itemDto.getItemCode());
		item.setItemData(header);
		item.setItemDescription(itemDto.getItemDescription());
		item.setItemName(itemDto.getItemName());
		item.setNetValue(itemDto.getNetValue());
		
		return item;
	}
	
	public static ItemDto exportItemDto(Item item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setAvailableQty(item.getAvailableQty());
		itemDto.setExpiryDate(item.getExpiryDate());
		itemDto.setItemCode(item.getItemCode());
		itemDto.setItemDescription(item.getItemDescription());
		itemDto.setItemName(item.getItemName());
		itemDto.setNetValue(item.getNetValue());
		
		return itemDto;
	}

}
