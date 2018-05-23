package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		responseDto.setStatus("OK");
		return responseDto;
	}

	@Override
	public WorkFlow getWorkFlowInstance(String requestId,String matCode) {
		
		String queryStr = "select w from WorkFlow w where w.requestId=:requestId and w.materialCode=:materialCode";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("requestId", requestId);
		query.setParameter("materialCode",matCode );
		
		WorkFlow workflow = (WorkFlow) query.list();
		return workflow;

	}

}
