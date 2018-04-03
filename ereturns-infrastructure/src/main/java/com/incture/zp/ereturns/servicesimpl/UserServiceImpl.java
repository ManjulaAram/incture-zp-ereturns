package com.incture.zp.ereturns.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.dto.ReturnUserDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
public class UserServiceImpl implements UserService {

//	@Autowired 
//	UserRepository userRepository;
	
//	@Override
	public ReturnUserDto addUser(UserDto userDto) {
		
		ReturnUserDto returnUserDto = new ReturnUserDto();
		returnUserDto.setMessage("SUCCESS");
		returnUserDto.setStatus("TRUE");
//		User user = new User();
//		user.setAddress("Address");
//		user.setEmail("contact@incture.com");
//		user.setUserName("Incture");
//		user.setLotNo("118288");
//		user.setSciId("S0001");
//		user.setUserCode("310740");
//		user.setUserId("U0001");
//
//		Header header = new Header();
//		header.setExpiryDate(new Date());
//		header.setHeaderData(user);
//		header.setInvoiceDate(new Date());
//		header.setInvoiceNo("2654707");
//		header.setInvoiceSeq("2");
//		header.setNetValue("27000");
//
//		Header header1 = new Header();
//		header1.setExpiryDate(new Date());
//		header1.setHeaderData(user);
//		header1.setInvoiceDate(new Date());
//		header1.setInvoiceNo("2666648");
//		header1.setInvoiceSeq("2");
//		header1.setNetValue("27000");
//
//		Item item = new Item();
//		item.setAvailableQty("10");
//		item.setItemCode("12345");
//		item.setItemData(header);
//		item.setItemDescription("Item1");
//		item.setItemName("Item1");
//		item.setNetValue("20000");
//		
//		Item item1 = new Item();
//		item1.setAvailableQty("7");
//		item1.setItemCode("23456");
//		item1.setItemData(header);
//		item1.setItemDescription("Item2");
//		item1.setItemName("Item2");
//		item1.setNetValue("7000");
//		
//		Item item3 = new Item();
//		item3.setAvailableQty("17");
//		item3.setItemCode("99999");
//		item3.setItemData(header1);
//		item3.setItemDescription("Item3");
//		item3.setItemName("Item3");
//		item3.setNetValue("27000");
//		
//		Set<Item> setItem = new HashSet<>();
//		setItem.add(item);
//		setItem.add(item1);
//		header.setSetItem(setItem);
//		
//		Set<Item> setItem1 = new HashSet<>();
//		setItem1.add(item3);
//		header1.setSetItem(setItem1);
//		
//		Set<Header> setHeader = new HashSet<>();
//		setHeader.add(header);
//		setHeader.add(header1);
//		
//		user.setSetHeader(setHeader);

		UserDto userDto2 = ImportExportUtil.exportUserDto(ImportExportUtil.importUserDto(userDto), userDto.getHeaderSet());
		returnUserDto.setUserDto(userDto2);
		return returnUserDto;
//		userRepository.addUser(user);
	}

	
	@Override
	public ReturnUserDto getUserById(String id) {
		ReturnUserDto returnUserDto = new ReturnUserDto();
//		User user = userRepository.getUserById(id);
		
		returnUserDto.setMessage("SUCCESS");
		returnUserDto.setStatus("TRUE");
//		returnUserDto.setUserDto(ImportExportUtil.exportUserDto(user, headerSet));
		return returnUserDto;
	}
	
}
