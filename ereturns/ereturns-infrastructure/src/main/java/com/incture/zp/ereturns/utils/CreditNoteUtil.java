package com.incture.zp.ereturns.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.CreditNoteStatusDto;
import com.incture.zp.ereturns.dto.CreditNoteStatusResponseDto;

@Component
public class CreditNoteUtil {

	public List<CreditNoteStatusDto> getCreditNoteStatus(String sdNo) {
		String destination = "https://zpgt.apimanagement.ap1.hana.ondemand.com/qas/ereturns";
		
		List<CreditNoteStatusDto> arryList = new ArrayList<CreditNoteStatusDto>();
		CreditNoteStatusDto creditNoteStatusDto = null;
		try {
			
			String creditNotePath = destination + "/CV_CreditNote_Status/CreditNote_Status?$filter=PrecedingSalesandDistributionDocument%20eq%20%27"+sdNo+"%27";
			
			URL creditNoteUrl = new URL(creditNotePath);
			HttpURLConnection creditNoteConn = (HttpURLConnection) creditNoteUrl.openConnection();
			creditNoteConn.setRequestMethod(EReturnConstants.GET);
			creditNoteConn.setRequestProperty(EReturnConstants.ACCEPT, EReturnConstants.APPLICATION_JSON);
			
			if (creditNoteConn.getResponseCode() != EReturnConstants.HTTP_SUCCESS_CODE) { 
				throw new RuntimeException("Failed : HTTP error code : " + creditNoteConn.getResponseCode());
			}

			BufferedReader openbr = new BufferedReader(new InputStreamReader(
				(creditNoteConn.getInputStream())));
			
			String creditNoteOutput;
			CreditNoteStatusResponseDto creditNoteResponseDto = null;
			while ((creditNoteOutput = openbr.readLine()) != null) {
				creditNoteResponseDto = new Gson().fromJson(creditNoteOutput, CreditNoteStatusResponseDto.class);
			}
			
			for(int i = 0 ; i < creditNoteResponseDto.getD().getResults().size() ; i++){
				creditNoteStatusDto = new CreditNoteStatusDto();
				// credit notes
				creditNoteStatusDto.setBaseUnitofMeasure(creditNoteResponseDto.getD().getResults().get(i).getBaseUnitofMeasure());
				creditNoteStatusDto.setDocumentCategoryOfPrecedingSDdocument(creditNoteResponseDto.getD().getResults().get(i).getDocumentCategoryOfPrecedingSDdocument());
				// VBELN
				creditNoteStatusDto.setSubsequentSalesAndDistributionDocument(creditNoteResponseDto.getD().getResults().get(i).getSubsequentSalesAndDistributionDocument());
				// VBTYP_N = O
				creditNoteStatusDto.setDocumentCategoryOfSubsequentDocument(creditNoteResponseDto.getD().getResults().get(i).getDocumentCategoryOfSubsequentDocument());
				creditNoteStatusDto.setMaterialNumber(creditNoteResponseDto.getD().getResults().get(i).getMaterialNumber());
				creditNoteStatusDto.setPrecedingItemOfAnSDdocument(creditNoteResponseDto.getD().getResults().get(i).getPrecedingItemOfAnSDdocument());
				creditNoteStatusDto.setPrecedingSalesandDistributionDocument(creditNoteResponseDto.getD().getResults().get(i).getPrecedingSalesandDistributionDocument());
				creditNoteStatusDto.setRecordCreationDate(creditNoteResponseDto.getD().getResults().get(i).getRecordCreationDate());
				creditNoteStatusDto.setReferencedQuantityInBaseUnitofmeasure(creditNoteResponseDto.getD().getResults().get(i).getReferencedQuantityInBaseUnitofmeasure());
				creditNoteStatusDto.setReferenceValue(creditNoteResponseDto.getD().getResults().get(i).getReferenceValue());
				creditNoteStatusDto.setSalesUnit(creditNoteResponseDto.getD().getResults().get(i).getSalesUnit());
				creditNoteStatusDto.setStatisticsCurrency(creditNoteResponseDto.getD().getResults().get(i).getStatisticsCurrency());
				creditNoteStatusDto.setSubsequentItemOfAnSDdocument(creditNoteResponseDto.getD().getResults().get(i).getSubsequentItemOfAnSDdocument());
				arryList.add(creditNoteStatusDto);
			}
			creditNoteConn.disconnect();
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		return arryList;
	}

}
