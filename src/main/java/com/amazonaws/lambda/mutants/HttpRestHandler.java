package com.amazonaws.lambda.mutants;

import org.springframework.http.HttpStatus;

import com.amazonaws.lambda.mutants.dto.AverageMutantsDTO;
import com.amazonaws.lambda.mutants.dto.DnaDTO;
import com.amazonaws.lambda.mutants.service.SecuenceMutants;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


public class HttpRestHandler implements RequestHandler<DnaDTO, APIGatewayProxyResponseEvent> {
	
	private SecuenceMutants serviceSecuenceMutants =new SecuenceMutants(); 
	
	
	@Override
	public APIGatewayProxyResponseEvent handleRequest(DnaDTO input, Context context) {
	
		LambdaLogger logger=context.getLogger();
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		
		
		try {
			
			boolean validacion= serviceSecuenceMutants.validateMutants(input.getDna(),logger);
			
			if(validacion)
			{
				 response.setStatusCode(HttpStatus.OK.value());
			}else {
				 response.setStatusCode(HttpStatus.FORBIDDEN.value());
			}
		   
			
		} catch (Exception e) {
			e.printStackTrace();
			 response.setStatusCode(HttpStatus.FORBIDDEN.value());
		}
		
	     
	        return response;
	

	}
	

}
