package com.infor.marketo.aoi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class TopAOIScoreTest {

    private static APIGatewayProxyRequestEvent input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new APIGatewayProxyRequestEvent();
        Map<String, String> qsp = new HashMap<String, String>();
        qsp.put("ABC", "50");
        qsp.put("ERP","100");
        qsp.put("HMC", "25");
        input.setQueryStringParameters(qsp);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testTopAOIScore() {
        TopAOIScore handler = new TopAOIScore();
        Context ctx = createContext();

        APIGatewayProxyResponseEvent output = handler.handleRequest(input, ctx);
        System.out.println(output.getBody());
        // TODO: validate output here if needed.
        Assert.assertEquals("{\"AOI\":\"ERP\"}", output.getBody());
    }
}
