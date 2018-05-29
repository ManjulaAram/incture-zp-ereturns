package com.incture.zp.ereturns.repositoriesimpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@Autowired
	GetReferenceData getReferenceData;

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportUtil.class);

	@Override
	public ResponseDto addRequest(Request request) {
		ResponseDto responseDto = new ResponseDto();
		String requestId = getReferenceData.getNextSeqNumber(getReferenceData.execute("R"), 6, sessionFactory);
		if (request.getRequestId() == null || request.getRequestId().equals("")) {
			request.setRequestId(requestId);
			responseDto.setMessage("Request " + requestId + " Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(request);
		responseDto.setMessage("Request " + request.getRequestId() + " Updated Successfully");
		responseDto.setStatus("SUCCESS");
		responseDto.setCode("00");
		return responseDto;
	}

	@Override
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto) {

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT r, o, h, i FROM Request r, ReturnOrder o, Header h, Item i WHERE r.requestId = o.returnOrderData.requestId "
						+ "AND r.requestHeader.headerId = h.headerId AND h.headerId = i.itemData.headerId");

		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			queryString.append(" AND r.requestId=:requestId");
		}

		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			queryString.append(" AND r.requestCreatedBy=:requestCreatedBy");
		}

		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			queryString.append(" AND o.soldTo=:soldTo");
		}

		if ((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals("")))
				&& (requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			queryString.append(" AND r.requestCreatedDate BETWEEN :startDate AND :endDate");
		}

		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			queryString.append(" AND o.orderPendingWith=:orderPendingWith");
		}

		LOGGER.error("Query for Status details:" + queryString.toString());
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			query.setParameter("requestId", requestDto.getRequestId());
		}

		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			query.setParameter("requestCreatedBy", requestDto.getCreatedBy());
		}

		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			query.setParameter("soldTo", requestDto.getCustomerCode());
		}

		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			query.setParameter("orderPendingWith", requestDto.getPendingWith());
		}
		if ((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals("")))
				&& (requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			query.setParameter("startDate", requestDto.getStartDate());
			query.setParameter("endDate", requestDto.getEndDate());
		}

		List<StatusResponseDto> reqList = new ArrayList<>();
		Request request = null;
		ReturnOrder returnOrder = null;
		Header header = null;
		Item item = null;
		StatusResponseDto statusResponseDto = null;
		@SuppressWarnings("unchecked")
		List<Object[]> objectsList = query.list();
		LOGGER.error("Results for Request:" + objectsList.size());
		for (Object[] objects : objectsList) {
			request = (Request) objects[0];
			returnOrder = (ReturnOrder) objects[1];
			header = (Header) objects[2];
			item = (Item) objects[3];

			if (request.getRequestId().equalsIgnoreCase(returnOrder.getReturnOrderData().getRequestId())
					&& request.getRequestHeader().getHeaderId().equals(header.getHeaderId())) {
				if (returnOrder.getItemCode().equalsIgnoreCase(item.getItemCode())) {
					statusResponseDto = new StatusResponseDto();
					statusResponseDto.setItemCode(item.getItemCode());
					statusResponseDto.setCreatedBy(returnOrder.getOrderCreatedBy());
					if (returnOrder.getOrderCreatedDate() != null && !(returnOrder.getOrderCreatedDate().equals(""))) {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String output = dateFormat.format(returnOrder.getOrderCreatedDate());
						statusResponseDto.setCreatedOn(output);
					}
					statusResponseDto.setMaterialCode(item.getMaterial());
					statusResponseDto.setMaterialDescription(item.getMaterialDesc());
					statusResponseDto.setRequestStatus(returnOrder.getOrderStatus());
					statusResponseDto.setNetQuantity(item.getAvailableQty());
					statusResponseDto.setNetValue(item.getNetValue());
					statusResponseDto.setReturnQuantity(returnOrder.getReturnQty());
					statusResponseDto.setReturnReason(returnOrder.getReason());
					statusResponseDto.setRemark(returnOrder.getRemark());
					statusResponseDto.setReturnType(returnOrder.getPaymentType());
					statusResponseDto.setReturnValue(returnOrder.getReturnValue());
					statusResponseDto.setSalesPerson("");

					// Request Level
					statusResponseDto.setRequestId(request.getRequestId());
					statusResponseDto.setShipTo(request.getShipTo());
					statusResponseDto.setSoldTo(request.getSoldTo());
					statusResponseDto.setInvoiceNo(request.getRequestHeader().getInvoiceNo());

					statusResponseDto.setMessage("Successfully Retrieved.");
					statusResponseDto.setStatus("SUCCESS");
					reqList.add(statusResponseDto);
				}
			}

		}
		return reqList;
	}

	@Override
	public Request getRequestById(String id) {
		return (Request) sessionFactory.getCurrentSession().get(Request.class, id);
	}

	@Override
	public List<RequestDto> getAllRequests() {
		List<RequestDto> list = new ArrayList<>();
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT r FROM Request r");

		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<Request> requestList = (List<Request>) query.list();
		for (Request request : requestList) {
			list.add(importExportUtil.exportRequestDto(request));
		}
		return list;
	}

	@Override
	public ResponseDto updateRequest(ReturnOrderDto returnOrderDto) {
		ResponseDto responseDto = new ResponseDto();

		String queryString = ("update Request r set r.requestUpdatedBy=:requestUpdatedBy"
				+ ", r.requestUpdatedDate=:requestUpdatedDate" + ", r.requestApprovedBy=:requestApprovedBy"
				+ ", r.requestApprovedDate=:requestApprovedDate" + ", r.requestStatus=:requestStatus"
				+ ", r.requestPendingWith=:requestPendingWith");
		StringBuilder queryBuilder = new StringBuilder(queryString);
		queryBuilder.append(" where requestId=:requestId");

		String query = queryBuilder.toString();

		Query updateQuery = sessionFactory.getCurrentSession().createQuery(query);

		if (returnOrderDto.getRequestId() != null || returnOrderDto.getRequestId() != "")
			updateQuery.setParameter("requestId", returnOrderDto.getRequestId());
		if (returnOrderDto.getOrderUpdatedBy() != null || returnOrderDto.getOrderUpdatedBy() != "")
			updateQuery.setParameter("requestUpdatedBy", returnOrderDto.getOrderUpdatedBy());
		if (returnOrderDto.getOrderApprovedBy() != null || returnOrderDto.getOrderApprovedBy() != "")
			updateQuery.setParameter("requestApprovedBy", returnOrderDto.getOrderApprovedBy());
		if (returnOrderDto.getOrderApprovedDate() != null || returnOrderDto.getOrderApprovedDate() != "")
			updateQuery.setParameter("requestApprovedDate", returnOrderDto.getOrderApprovedDate());
		if (returnOrderDto.getOrderStatus() != null || returnOrderDto.getOrderStatus() != "")
			updateQuery.setParameter("requestStatus", returnOrderDto.getOrderStatus());
		if (returnOrderDto.getOrderPendingWith() != null || returnOrderDto.getOrderPendingWith() != "")
			updateQuery.setParameter("requestPendingWith", returnOrderDto.getOrderPendingWith());
		if (returnOrderDto.getOrderUpdatedDate() != null || returnOrderDto.getOrderUpdatedDate() != "")
			updateQuery.setParameter("requestUpdatedDate", returnOrderDto.getOrderUpdatedDate());

		if (updateQuery.executeUpdate() > 0) {
			responseDto.setMessage("Request updated successfully");
			responseDto.setCode("00");
			responseDto.setStatus("SUCCESS");
		} else {
			responseDto.setMessage("Request did not update successfully");
			responseDto.setCode("01");
			responseDto.setStatus("ERROR");
		}
		return responseDto;
	}

}
