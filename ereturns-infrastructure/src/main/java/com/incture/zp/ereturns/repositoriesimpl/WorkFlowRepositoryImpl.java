package com.incture.zp.ereturns.repositoriesimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.WorkFlow;
import com.incture.zp.ereturns.repositories.WorkflowRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class WorkFlowRepositoryImpl implements WorkflowRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public ResponseDto addWorkflowInstance(WorkFlow workFlow) {
		ResponseDto responseDto = new ResponseDto();

		if (!(workFlow.getRequestId() == null || workFlow.getRequestId().equals(""))) {
			sessionFactory.getCurrentSession().persist(workFlow);
		}

		responseDto.setMessage("WorkFlow Instance added " + workFlow.getWorkFlowInstanceId() + "Successfully");
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}

	@Override
	public WorkFlow getWorkFlowInstance(String requestId,String matCode) {
		
		String queryStr = "select w from WorkFlow w where w.requestId=:requestId and w.materialCode=:materialCode";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("requestId", requestId);
		query.setParameter("materialCode",matCode );
		WorkFlow workflow = null;
		@SuppressWarnings("unchecked")
		List<WorkFlow> workflowList = (List<WorkFlow>) query.list();
		if(workflowList != null && workflowList.size() > 0) {
			for(int i = 0; i < workflowList.size(); i++) {
				workflow = workflowList.get(i);
			}
		}
		return workflow;

	}

	@Override
	public ResponseDto deleteWorkflow(String requestId) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE WorkFlow w WHERE w.requestId=:requestId";
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setParameter("requestId", requestId);
			
			int result = query.executeUpdate();
			if (result > 0) {
				responseDto.setMessage("Deleted Successfully");
				responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
				responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
			} else {
				responseDto.setMessage("Delete Unsuccessfully");
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			}
		return responseDto;
	}

}
