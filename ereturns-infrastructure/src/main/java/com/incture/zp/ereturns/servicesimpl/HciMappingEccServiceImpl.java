package com.incture.zp.ereturns.servicesimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.constants.EReturnConstants;
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
	
	@SuppressWarnings("unused")
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
		header.put(EReturnsHciConstants.REF_DOC_CAT, EReturnsHciConstants.REFERENCE_DOCUMENT_CATEGORY);
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
			schedules.put(EReturnsHciConstants.SCHEDULE_LINE, EReturnConstants.ECC_SCHEDULE_LINE);
			schedules.put(EReturnsHciConstants.REQ_DATE, new SimpleDateFormat(EReturnConstants.ECC_DATE_FORMAT).format(new Date()));
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
		
//		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnsHciConstants.HCI_ECC_ENDPOINT);
//		String destination = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_URL);
//		String username = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_USER);
//		String password = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_PWD);
//		String url = destination;
		
		String url = EReturnsHciConstants.HCI_ECC_ENDPOINT_URL;
		String username = EReturnsHciConstants.USERNAME;
		String password = EReturnsHciConstants.PASSWORD;
		

		RestInvoker restInvoker = new RestInvoker(url, username, password);
		LOGGER.error("Response coming from ECC1:");
		String response = restInvoker.postDataToServer(EReturnConstants.ECC_HCI_URL, returnOrder.toString());
		LOGGER.error("Response coming from ECC:"+response);
		if(response != null && !(response.equals(""))) {
			if(response.contains(EReturnConstants.ECC_RESPONSE)) {
				JSONObject returnObj = new JSONObject(response);
				JSONObject bapiObj = new JSONObject();
				bapiObj = returnObj.getJSONObject(EReturnConstants.ECC_RESPONSE);
				responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
				responseDto.setStatus(EReturnConstants.ECC_SUCCESS_STATUS);
				responseDto.setMessage(bapiObj.getString("SALESDOCUMENT"));
				if(responseDto.getMessage().equals("")) {
					JSONObject msgReturnObj = new JSONObject();
					msgReturnObj = bapiObj.getJSONObject("RETURN");
					JSONArray itemAry = msgReturnObj.getJSONArray("item");
					
					for (int i = 0; i < itemAry.length(); i++) {
						JSONObject msgObject = new JSONObject();
						msgObject = (JSONObject) itemAry.get(i);
						JSONObject typeObject = new JSONObject();
						typeObject = (JSONObject) itemAry.get(i);
						String type = typeObject.get("TYPE").toString();
						LOGGER.error(msgObject.get("MESSAGE")+"...."+type);
						if(type != null && !(type.equals("")) && type.equalsIgnoreCase("E")) {
							responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
							responseDto.setStatus(EReturnConstants.ECC_ERROR_STATUS);
							responseDto.setMessage(msgObject.get("MESSAGE").toString());
						}
						break;
					}
				} 
			} else if(response.contains(EReturnConstants.ECC_EXCEPTION)) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setMessage(response);
				responseDto.setStatus(EReturnConstants.ECC_ERROR_STATUS);
			} else if(response.contains(EReturnConstants.ECC_500_ERROR)) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setMessage(response);
				responseDto.setStatus(EReturnConstants.ECC_ERROR_STATUS);
			}
		} else {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage(EReturnConstants.ECC_NO_DATA_STATUS);
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
		}
		return responseDto;
	}
	
}

