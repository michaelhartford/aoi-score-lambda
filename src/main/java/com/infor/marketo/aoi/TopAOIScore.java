package com.infor.marketo.aoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class TopAOIScore implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	response.setHeaders(Collections.singletonMap("Content-Type", "application/json"));
        context.getLogger().log("Event: " + event.toString());
        AOI topAOI = getTopAOI(event);
        if(topAOI != null)
        {
            context.getLogger().log("topAOI: " + topAOI.getName() + "," + topAOI.getScore());
        	response.setStatusCode(200);
        	Map<String, String> responseBody = new HashMap<String, String>();
            responseBody.put("AOI", topAOI.getName());
            String responseBodyString = new JSONObject(responseBody).toJSONString();
            response.setBody(responseBodyString);
        } else {
            context.getLogger().log("No Top AOI found in query parameters");
        	response.setStatusCode(400);
        	response.setBody("{\"error\":\"cannot parse query parameters\"}");
        }
        return response;
    }

	private AOI getTopAOI(APIGatewayProxyRequestEvent event) {
		AOI aoi = null;
		ArrayList<AOI> list = new ArrayList<AOI>();
		Map<String, String> qsp = event.getQueryStringParameters();
		if(qsp != null && qsp.size() > 0)
		{
			Set<String> keySet = qsp.keySet();
			Iterator<String> iter = keySet.iterator();
			while(iter.hasNext())
			{
				String name = iter.next();
				String strScore = qsp.get(name);
				int score = 0;
				try
				{
					score = Integer.parseInt(strScore);
				} catch(Exception e)
				{
					score = 0;
				}
				AOI myAOI = new AOI(name, score);
				list.add(myAOI);
			}
			if(list.size() > 0)
			{
				Collections.sort(list);
				aoi = list.get(list.size()-1);
			}
		}
		return aoi;
	}

}

