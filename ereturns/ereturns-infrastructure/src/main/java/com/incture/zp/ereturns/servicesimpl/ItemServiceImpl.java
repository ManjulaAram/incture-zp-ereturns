package com.incture.zp.ereturns.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.repositories.ItemRepository;
import com.incture.zp.ereturns.services.ItemService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public ItemDto getItemById(String id) {
		Item item = itemRepository.getItemById(id);
		ItemDto itemDto = importExportUtil.exportItemDto(item);
		return itemDto;
	}

}
