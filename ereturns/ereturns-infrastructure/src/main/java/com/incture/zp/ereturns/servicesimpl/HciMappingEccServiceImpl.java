package com.incture.zp.ereturns.servicesimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.constants.EReturnsHciConstants;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.utils.RestInvoker;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@Service
@Transactional
public class HciMappingEccServiceImpl implements HciMappingEccService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HciMappingEccServiceImpl.class);
	
	@Override
	public ResponseDto pushDataToEcc(RequestDto requestDto, String itemCode, String action) {
		ResponseDto responseDto = new ResponseDto();
		
		JSONObject returnOrder = new JSONObject();
		 
		JSONObject orderCreation = new JSONObject();
		JSONArray partnerArry = new JSONArray();
		JSONObject partnerShipTo = new JSONObject();
		JSONObject partnerSoldTo = new JSONObject();
		
		JSONObject header = new JSONObject();
		JSONArray itemsArry = new JSONArray();
		JSONArray scheduleArry = new JSONArray();
		JSONArray conditionArry = new JSONArray();
		JSONObject returnText = new JSONObject();
		
		String invoiceNo = "";
		
		if(requestDto.getUnrefFlag() != null && requestDto.getUnrefFlag().equalsIgnoreCase("FALSE")) {
			invoiceNo = requestDto.getHeaderDto().getInvoiceNo();
		} 
		
		partnerShipTo.put(EReturnsHciConstants.PARTNER_ROLE, EReturnsHciConstants.SHIP_TO_PARTY);
		partnerShipTo.put(EReturnsHciConstants.PARTNER_NUMBER, requestDto.getShipTo());
		partnerArry.put(partnerShipTo);

		partnerSoldTo.put(EReturnsHciConstants.PARTNER_ROLE, EReturnsHciConstants.SOLD_TO_PARTY);
		partnerSoldTo.put(EReturnsHciConstants.PARTNER_NUMBER, requestDto.getSoldTo());
		partnerArry.put(partnerSoldTo);
		
		header.put(EReturnsHciConstants.DOCUMENT_TYPE, requestDto.getHeaderDto().getDocumentType());
		header.put(EReturnsHciConstants.REF_DOC, invoiceNo);
		header.put(EReturnsHciConstants.PURCHASE_CUSTOMER_NO, requestDto.getRequestId());
		header.put(EReturnsHciConstants.REF_DOC_CAT, EReturnsHciConstants.REFERENCE_DOCUMENT_CATEGORY);
		header.put(EReturnsHciConstants.CURRENCY, requestDto.getHeaderDto().getCurrency());
		header.put(EReturnsHciConstants.SALES_ORG, requestDto.getHeaderDto().getSalesOrg());
		header.put(EReturnsHciConstants.DISTRIBUTION_CHANNEL, requestDto.getHeaderDto().getDistrChan());
		header.put(EReturnsHciConstants.DIVISION, requestDto.getHeaderDto().getDivision());
		header.put(EReturnsHciConstants.PO_METHOD, requestDto.getPurchaseOrder());
		header.put(EReturnsHciConstants.ASSIGNMENT_NO, requestDto.getHeaderDto().getInvoiceNo());
		 
		List<ItemDto> itemList = new ArrayList<>();
		itemList.addAll(requestDto.getHeaderDto().getItemSet());

		itemList.sort((i1, i2) -> i1.getItemCode().compareTo(i2.getItemCode()));
		 
		List<ReturnOrderDto> returnOrderList = new ArrayList<>();
		returnOrderList.addAll(requestDto.getSetReturnOrderDto());
		returnOrderList.sort((o1, o2) -> o1.getItemCode().compareTo(o2.getItemCode()));
		
		String reason = "";
		String reqComments = "";
		for(int i = 0; i < itemList.size(); i++) {
			
			if(!(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase("REJECTED"))) {
				ItemDto itemDto = itemList.get(i);
				
				JSONObject item = new JSONObject();
				
				item.put(EReturnsHciConstants.PLANT, itemDto.getPlant());
				item.put(EReturnsHciConstants.STORE_LOC, "");
				item.put(EReturnsHciConstants.MATERIAL, itemDto.getMaterial());
				item.put(EReturnsHciConstants.ITEM_NO, itemDto.getItemCode());
				item.put(EReturnsHciConstants.TARGET_QTY, returnOrderList.get(i).getReturnQty());// need to get from return order
				item.put(EReturnsHciConstants.REF_DOC, invoiceNo);
				item.put(EReturnsHciConstants.CURRENCY, requestDto.getHeaderDto().getCurrency());
				item.put(EReturnsHciConstants.ORDER_REASON, itemDto.getItemName());
				item.put(EReturnsHciConstants.BATCH, itemDto.getBatch());
				item.put(EReturnsHciConstants.HIGH_LEVEL_ITEM_CODE, itemDto.getHighLevelItemCode());
				
				JSONObject schedules = new JSONObject();
				schedules.put(EReturnsHciConstants.ITEM_NO, itemDto.getItemCode());
				schedules.put(EReturnsHciConstants.SCHEDULE_LINE, EReturnConstants.ECC_SCHEDULE_LINE);
				schedules.put(EReturnsHciConstants.REQ_DATE, new SimpleDateFormat(EReturnConstants.ECC_DATE_FORMAT).format(new Date()));
				schedules.put(EReturnsHciConstants.REQ_QTY, returnOrderList.get(i).getReturnQty());

				JSONObject conditions = new JSONObject();
				conditions.put(EReturnsHciConstants.CONDITION_ITEM_NO, itemDto.getItemCode());
				if(returnOrderList.get(i).getOverrideReturnValue() != null 
						&& !(returnOrderList.get(i).getOverrideReturnValue().equals("")) 
						&& !(returnOrderList.get(i).getOverrideReturnValue().equals("0"))) {
					conditions.put(EReturnsHciConstants.CONDITION_TYPE, EReturnsHciConstants.CONDITION_TYPE_VALUE);
				} else if(returnOrderList.get(i).getOverrideReturnValue() != null 
						&& returnOrderList.get(i).getOverrideReturnValue().equals("0")) {
					conditions.put(EReturnsHciConstants.CONDITION_TYPE, "");
				} else {
					conditions.put(EReturnsHciConstants.CONDITION_TYPE, "");
				}
				conditions.put(EReturnsHciConstants.CURRENCY, requestDto.getHeaderDto().getCurrency());
				conditions.put(EReturnsHciConstants.CONDITION_VALUE, returnOrderList.get(i).getOverrideReturnValue());
				
				if(returnOrderList.get(i).getPaymentType() != null && !(returnOrderList.get(i).getPaymentType().equals(""))) {
					if(returnOrderList.get(i).getPaymentType().equalsIgnoreCase("Credit")) {
						if(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase("INPROGRESS") 
								&& itemDto.getItemCode().equalsIgnoreCase(itemCode) && action.equalsIgnoreCase("Approved")) {
							itemsArry.put(item);
							scheduleArry.put(schedules);
							conditionArry.put(conditions);
						} else if(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase("COMPLETED")){
							itemsArry.put(item);
							scheduleArry.put(schedules);
							conditionArry.put(conditions);
						} else if(action.equalsIgnoreCase("AUTO")){
							itemsArry.put(item);
							scheduleArry.put(schedules);
							conditionArry.put(conditions);
						}
					} 
				} else {
					if(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase("INPROGRESS") 
							&& itemDto.getItemCode().equalsIgnoreCase(itemCode) && action.equalsIgnoreCase("Approved")) {
						itemsArry.put(item);
						scheduleArry.put(schedules);
						conditionArry.put(conditions);
					} else if(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase("COMPLETED")) {
						itemsArry.put(item);
						scheduleArry.put(schedules);
						conditionArry.put(conditions);
					}
				}
				
				reason = returnOrderList.get(i).getReason();
				reqComments = returnOrderList.get(i).getRemark();
				
			}
			
		}
		 
		header.put(EReturnsHciConstants.ORDER_REASON, reason);
		returnText.put(EReturnsHciConstants.TEXT_ID, "ZP02");
		returnText.put(EReturnsHciConstants.TEXT_LINE, reqComments);
		returnText.put(EReturnsHciConstants.TEXT_LANGUAGE, "EN");
		
		orderCreation.put(EReturnsHciConstants.RETURN_PARTNERS, partnerArry);
		orderCreation.put(EReturnsHciConstants.RETURN_HEADER, header);
		orderCreation.put(EReturnsHciConstants.RETURN_ITEMS, itemsArry);
		orderCreation.put(EReturnsHciConstants.RETURN_SCHEDULES_IN, scheduleArry);
		orderCreation.put(EReturnsHciConstants.RETURN_CONDITIONS, conditionArry);
		orderCreation.put(EReturnsHciConstants.RETURN_TEXT, returnText);
		 
		returnOrder.put(EReturnsHciConstants.RETURN_ORDER_CREATION, orderCreation);
		 
		LOGGER.error("Json payload for ECC:"+returnOrder.toString());
		
		
//		call rest invoker to push data
		
		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnsHciConstants.HCI_ECC_ENDPOINT);
		String destination = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_URL);
		String username = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_USER);
		String password = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_PWD);
		String url = destination;
		
//		String url = EReturnsHciConstants.HCI_ECC_ENDPOINT_URL;
//		String username = EReturnsHciConstants.USERNAME;
//		String password = EReturnsHciConstants.PASSWORD;
		

		if(itemsArry.length() > 0) {
			RestInvoker restInvoker = new RestInvoker(url, username, password);
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
					
					Object     obj;
					JSONArray  itemArry;
					JSONObject itemObj;
					if(responseDto.getMessage().equals("")) {
						JSONObject msgReturnObj = new JSONObject();
						msgReturnObj = bapiObj.getJSONObject("RETURN");
						obj = msgReturnObj.get("item");
						if(obj instanceof JSONArray) {
							itemArry = (JSONArray) obj;
							for (int i = 0; i < itemArry.length(); i++) {
								JSONObject msgObject = new JSONObject();
								msgObject = (JSONObject) itemArry.get(i);
								JSONObject typeObject = new JSONObject();
								typeObject = (JSONObject) itemArry.get(i);
								String type = typeObject.get("TYPE").toString();
								if(type != null && !(type.equals("")) && type.equalsIgnoreCase("E")) {
									responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
									responseDto.setStatus(EReturnConstants.ECC_ERROR_STATUS);
									responseDto.setMessage(msgObject.get("MESSAGE").toString());
									break;
								} else if(type != null && !(type.equals("")) && type.equalsIgnoreCase("S")){
									responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
									responseDto.setStatus(EReturnConstants.ECC_SUCCESS_STATUS);
									responseDto.setMessage(msgObject.get("MESSAGE").toString());
								}
							}
						} else if(obj instanceof JSONObject) {
							itemObj = (JSONObject) obj;
							String type = itemObj.get("TYPE").toString();
							if (type.equalsIgnoreCase("E")) {
								responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
								responseDto.setStatus(EReturnConstants.ECC_ERROR_STATUS);
								responseDto.setMessage(itemObj.get("MESSAGE").toString());
							} else {
								responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
								responseDto.setStatus(EReturnConstants.ECC_SUCCESS_STATUS);
								responseDto.setMessage(bapiObj.getString("SALESDOCUMENT"));
							}

						}
					} else {
						responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
						responseDto.setStatus(EReturnConstants.ECC_SUCCESS_STATUS);
						responseDto.setMessage(bapiObj.getString("SALESDOCUMENT"));
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
		} else {
			responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
			responseDto.setMessage("No posting to ECC");
			responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		}
		return responseDto;
	}
	
}

