package com.incture.zp.ereturns.servicesimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.constants.EReturnsHciConstants;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.utils.RestInvoker;

@Service
@Transactional
public class HciMappingEccServiceImpl implements HciMappingEccService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HciMappingEccServiceImpl.class);
	
	@Override
	public ResponseDto pushDataToEcc(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		
		JSONObject returnOrder = new JSONObject();
		 
		JSONObject orderCreation = new JSONObject();
		JSONArray partnerArry = new JSONArray();
		JSONObject partnerShipTo = new JSONObject();
		JSONObject partnerSoldTo = new JSONObject();
		
		JSONObject header = new JSONObject();
		JSONArray itemsArry = new JSONArray();
		JSONArray scheduleArry = new JSONArray();
		
		partnerShipTo.put(EReturnsHciConstants.PARTNER_NUMBER, requestDto.getShipTo());
		partnerShipTo.put(EReturnsHciConstants.PARTNER_ROLE, EReturnsHciConstants.SHIP_TO_PARTY);
		partnerArry.put(partnerShipTo);

		partnerSoldTo.put(EReturnsHciConstants.PARTNER_ROLE, EReturnsHciConstants.SOLD_TO_PARTY);
		partnerSoldTo.put(EReturnsHciConstants.PARTNER_NUMBER, requestDto.getSoldTo());
		partnerArry.put(partnerSoldTo);
		
		header.put(EReturnsHciConstants.DOCUMENT_TYPE, requestDto.getHeaderDto().getDocumentType());
		header.put(EReturnsHciConstants.REF_DOC, requestDto.getHeaderDto().getInvoiceNo());
		header.put(EReturnsHciConstants.PURCHASE_CUSTOMER_NO, requestDto.getHeaderDto().getPurchNoCust());
		header.put(EReturnsHciConstants.REF_DOC_CAT, requestDto.getHeaderDto().getRefDocCat());
		header.put(EReturnsHciConstants.CURRENCY, requestDto.getHeaderDto().getCurrency());
		header.put(EReturnsHciConstants.SALES_ORG, requestDto.getHeaderDto().getSalesOrg());
		header.put(EReturnsHciConstants.DISTRIBUTION_CHANNEL, requestDto.getHeaderDto().getDistrChan());
		header.put(EReturnsHciConstants.DIVISION, requestDto.getHeaderDto().getDivision());
		 
		List<ItemDto> itemList = new ArrayList<>();
		itemList.addAll(requestDto.getHeaderDto().getItemSet());
		
		List<ReturnOrderDto> returnOrderList = new ArrayList<>();
		returnOrderList.addAll(requestDto.getSetReturnOrderDto());
		
		for(int i = 0; i < itemList.size(); i++) {
			ItemDto itemDto = itemList.get(i);
			
			JSONObject item = new JSONObject();
			
			item.put(EReturnsHciConstants.PLANT, itemDto.getPlant());
			item.put(EReturnsHciConstants.STORE_LOC, itemDto.getStoreLoc());
			item.put(EReturnsHciConstants.MATERIAL, itemDto.getMaterial());
			item.put(EReturnsHciConstants.ITEM_NO, itemDto.getItemCode());
			item.put(EReturnsHciConstants.TARGET_QTY, returnOrderList.get(i).getReturnQty());// need to get from return order
			item.put(EReturnsHciConstants.REF_DOC, requestDto.getHeaderDto().getInvoiceNo());
			item.put(EReturnsHciConstants.CURRENCY, requestDto.getHeaderDto().getCurrency());
			item.put(EReturnsHciConstants.ORDER_REASON, returnOrderList.get(i).getReason());
			itemsArry.put(item);
			
			JSONObject schedules = new JSONObject();
			schedules.put(EReturnsHciConstants.ITEM_NO, itemDto.getItemCode());
			schedules.put(EReturnsHciConstants.SCHEDULE_LINE, "0001");
			schedules.put(EReturnsHciConstants.REQ_DATE, new SimpleDateFormat("yyyyMMdd").format(new Date()));
			schedules.put(EReturnsHciConstants.REQ_QTY, returnOrderList.get(i).getReturnQty());

			scheduleArry.put(schedules);
		}
		 

		orderCreation.put(EReturnsHciConstants.RETURN_PARTNERS, partnerArry);
		orderCreation.put(EReturnsHciConstants.RETURN_HEADER, header);
		orderCreation.put(EReturnsHciConstants.RETURN_ITEMS, itemsArry);
		orderCreation.put(EReturnsHciConstants.RETURN_SCHEDULES_IN, scheduleArry);
		 
		returnOrder.put(EReturnsHciConstants.RETURN_ORDER_CREATION, orderCreation);
		 
		LOGGER.error("Json payload for ECC:"+returnOrder.toString());
		
//		call rest invoker to push data
		String url = EReturnsHciConstants.HCI_ECC_ENDPOINT;
		String username = EReturnsHciConstants.USERNAME;
		String password = EReturnsHciConstants.PASSWORD;

		RestInvoker restInvoker = new RestInvoker(url, username, password);
		LOGGER.error("Response coming from ECC1:");
		String response = restInvoker.postDataToServer("/http/ro", returnOrder.toString());
		LOGGER.error("Response coming from ECC:"+response);
		if(response != null && !(response.equals(""))) {
			JSONObject returnObj = new JSONObject(response);
			JSONObject bapiObj = new JSONObject();
			bapiObj = returnObj.getJSONObject("rfc:BAPI_CUSTOMERRETURN_CREATE.Response");
			responseDto.setCode(String.valueOf(HttpStatus.SC_OK));
			responseDto.setMessage(bapiObj.getString("SALESDOCUMENT"));
			responseDto.setStatus("SUCCESS");
		} else {
			responseDto.setCode(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
			responseDto.setMessage("FAILURE");
			responseDto.setStatus("ERROR");
		}
		return responseDto;
	}
	
}

