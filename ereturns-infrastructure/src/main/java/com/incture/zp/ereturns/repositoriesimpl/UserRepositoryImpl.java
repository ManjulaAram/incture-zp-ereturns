package com.incture.zp.ereturns.repositoriesimpl;

import com.incture.zp.ereturns.repositories.UserRepository;

//@Repository("userRepository")
//@Transactional
public class UserRepositoryImpl implements UserRepository {

//	@Autowired
//    private SessionFactory sessionFactory;

//	@Override
//	public void addUser(User user) {
//		sessionFactory.getCurrentSession().saveOrUpdate(user);
//	}
//
//	@Override
//	public User getUserById(String id) {
//		return (User) sessionFactory.getCurrentSession().get(User.class, id);
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public List<ReturnRequestDto> getReturnRequest(SearchDto searchDto) {
//		StringBuilder queryString = new StringBuilder();
//		
//		queryString.append("SELECT u, h, i FROM User u, Header h, Item i "
//				+ "WHERE u.userId = h.headerData.userId and h.invoiceNo = i.itemData.invoiceNo ");
//		
//		if(searchDto.getCustomerCode() != null) {
//			queryString.append(" AND u.userCode=:userCode");
//		}
//		
//		if(searchDto.getCustomerName() != null) {
//			queryString.append(" AND u.userName=:userName");
//		}
//		
//		if(searchDto.getInvoiceNo() != null) {
//			queryString.append(" AND h.invoiceNo=:invoiceNo");
//		}
//		
//		if(searchDto.getSalesOrder() != null) {
//			queryString.append(" AND h.invoiceNo=:invoiceNo");
//		}
//		
//		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
//		query.setParameter("userCode", searchDto.getCustomerCode());
//		query.setParameter("userName", searchDto.getCustomerName());
//		query.setParameter("invoiceNo", searchDto.getInvoiceNo());
//		query.setParameter("invoiceNo", searchDto.getSalesOrder());
//		
//		List<ReturnRequestDto> requestDtos = new ArrayList<>();
//		ReturnRequestDto requestDto = null;
//		User user = null;
//		List<HeaderDto> headerList = new ArrayList<>();
//		HeaderDto headerDto = null;
//		Header header = null;
//		Item item = null;
//		Set<ItemDto> itemList = new HashSet<>();
//		ItemDto itemDto = null;
//		List<Object[]> objectsList = query.getResultList();
//			for (Object[] objects : objectsList) {
//			user = (User) objects[0];
//			header = (Header) objects[1];
//			item = (Item) objects[2];
//	
//			headerDto = new HeaderDto();
//			headerDto.setAvailableQty(header.getAvailableQty());
//			headerDto.setExpiryDate(header.getExpiryDate());
//			headerDto.setInvoiceDate(header.getInvoiceDate());
//			headerDto.setInvoiceNo(header.getInvoiceNo());
//			headerDto.setInvoiceSeq(header.getInvoiceSeq());
//			
//			itemDto = new ItemDto();
//			itemDto.setAvailableQty(item.getAvailableQty());
//			itemDto.setExpiryDate(item.getExpiryDate());
//			itemDto.setItemCode(item.getItemCode());
//			itemDto.setItemDescription(item.getItemDescription());
//			itemDto.setItemName(item.getItemName());
//			itemDto.setNetValue(item.getNetValue());
//			
//			itemList.add(itemDto);
//			
//			headerDto.setItemSet(itemList);
//			headerDto.setNetValue(header.getNetValue());
//			
//			headerList.add(headerDto);
//			
//			requestDto = new ReturnRequestDto();
//			requestDto.setAddress(user.getAddress());
//			requestDto.setBoxQty("");
//			requestDto.setDocumentType("Invoices");
//			requestDto.setEmail(user.getEmail());
//			
//			requestDto.setHeaderList(headerList);
//			requestDto.setLocation("");
//			requestDto.setLotNo(user.getLotNo());
//			requestDto.setNoOfLine("");
//			requestDto.setRemarks("");
//			requestDto.setRequestApprovedBy("");
//			requestDto.setRequestApprovedDate(null);
//			requestDto.setRequestCreatedBy("");
//			requestDto.setRequestCreatedDate(null);
//			requestDto.setRequestId("");
//			requestDto.setRequestPendingWith("");
//			requestDto.setRequestStatus("");
//			requestDto.setRequestUpdatedBy("");
//			requestDto.setRequestUpdatedDate(null);
//			requestDto.setReturnEntireOrder("");
//			requestDto.setReturnPrice("");
//			requestDto.setReturnQty("");
//			requestDto.setReturnReason("");
//			requestDto.setReturnValue("");
//			requestDto.setSalesDocument("");
//			requestDto.setSalesPerson("");
//			requestDto.setSalesPersonName("");
//			requestDto.setSoldToParty("");
//			requestDto.setUserCode(user.getUserCode());
//			requestDto.setUserName(user.getUserName());
//			
//			requestDtos.add(requestDto);
//		}
//		
//		return requestDtos;
//	}

}
