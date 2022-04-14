package com.taomish.xero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.http.*;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taomish.xero.dto.InvoiceDTO;
import com.taomish.xero.service.XeroInvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
public class XeroInvoiceController {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final XeroInvoiceService xeroInvoiceService;
  private String XeroClientId = "97C62FF3B0C7417393BC515F35A0554E";
  private String XeroClientSecret = "b6mIuZ7Kz0H1wQn_HjL1jGvKcyT2G16_YBJNXxcaPV1CAw7i";
  private String XeroRedirectURL = "http://localhost:8080/xero/callback";
  private String XeroAuthorizeAPI = "https://login.xero.com/identity/connect/authorize";
  private String XeroTokenAPI = "https://identity.xero.com/connect/token";
  private String XeroConnectionsAPI = "https://api.xero.com/connections";
  private String XeroInvoiceCreateAPI = "https://api.xero.com/api.xro/2.0/Invoices";
  private String XeroAPIScopes = "offline_access openid profile email accounting.transactions accounting.settings accounting.contacts";
  private String authorizationString = XeroClientId + ":" + XeroClientSecret;
  private String encodedAuth = Base64.getEncoder().encodeToString(authorizationString.getBytes());
  private String authHeaderValue = "Basic " + new String(encodedAuth);
  public XeroInvoiceController(XeroInvoiceService xeroInvoiceService) {
    this.xeroInvoiceService = xeroInvoiceService;
  }

  @PostMapping("/xero/createInvoice/{tenantId}")
	public JsonNode submitInvoice(@RequestBody JsonNode payload, @PathVariable("tenantId") String tenantId) {
		JsonNode jsonNode = null;
    String accessToken = getAccessTokenWithTenantId(tenantId);
    try {
      if(accessToken != "") {
        HttpURLConnection con = (HttpURLConnection) new URL(XeroInvoiceCreateAPI).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true); 
        con.setRequestProperty("Authorization", "Bearer "+accessToken);
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Xero-tenant-id", tenantId);
        con.getOutputStream().write(payload.toString().getBytes("UTF-8"));
        /*
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        out.write(payload.toString());
        out.close();
        */

        con.getInputStream();
        con.connect();
        int resCode = con.getResponseCode();
        BufferedReader br = null;
        StringBuilder sb = null;
        if (100 <= resCode && resCode <= 399) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
              sb.append(output);
            }
            
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
              sb.append(output);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(sb.toString());
      }
    } catch(Exception e) {
      System.out.println("Exception : "+ e.getMessage());
    }
    return jsonNode;
    
	}
  
  @GetMapping("/xero/authorization")
	public void authenticateXero(HttpServletResponse response) {
		//return "authorization";
    try {
      String secretState = "secret" + new Random().nextInt(999_999);
      String authURL = XeroAuthorizeAPI + "?response_type=code&client_id="+this.XeroClientId+"&redirect_uri="+this.XeroRedirectURL+"&scope="+this.XeroAPIScopes+"&state="+secretState;
      this.saveCookie(response, "state", secretState);
      response.sendRedirect(authURL);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
	}

  @GetMapping("/xero/callback")
  public String xeroCallback(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "code", defaultValue = "", required = true) String code, @RequestParam(value = "state", defaultValue = "", required = true) String state, @RequestParam(value = "scope") String scope, @RequestParam(value = "session_state") String sessionState, Model model) {
    String secretState = getCookie(request, "state");
    String callbackCode = "";
    if (request.getParameter("code") != null) {
      callbackCode = request.getParameter("code");
    }
    if (request.getParameter("state") != null && secretState.equals(request.getParameter("state").toString())) {
    
      try {
        
        String urlParameters = "grant_type=authorization_code&code="+callbackCode+"&redirect_uri="+XeroRedirectURL;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        //requesting xero API to get access_token with the code got after authentication
        HttpURLConnection con = (HttpURLConnection) new URL(XeroTokenAPI).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true); 
        con.setRequestProperty("Authorization", authHeaderValue);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.getOutputStream().write(postData);
        con.getInputStream();
        con.connect();
        
        int resCode = con.getResponseCode();
        BufferedReader br = null;
        StringBuilder sb = null;
        if (100 <= resCode && resCode <= 399) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
              sb.append(output);
            }
            
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
              sb.append(output);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(sb.toString());
        
        //if received tokens in the response then the next call will be getting tenant id
        if(jsonNode.get("access_token") != null) {
          
          long timeStamp = new Date().getTime();
          String tokenExpireTime = String.valueOf(timeStamp + 1800000);
          String accessToken = jsonNode.get("access_token").asText();
          String refreshToken = jsonNode.get("refresh_token").asText();
          String tokenSet = jsonNode.get("id_token").asText();
          JsonNode tenantJson = getTenant(accessToken);
          tenantJson = tenantJson.get(0);
          String tenantName = tenantJson.get("tenantName").asText();
          String tenantId = tenantJson.get("tenantId").asText();
          if(tenantJson != null) {
            String sql = "INSERT INTO customers (customerName, companyName, realmId, accessToken, accessTokenExpire, refreshToken, tokenSet) VALUES (?, ?, ?, ?, ?, ?, ?)";
            int tokenInsertStatus = jdbcTemplate.update(sql, tenantName, tenantName, tenantId, accessToken, tokenExpireTime, refreshToken, tokenSet);
            if(tokenInsertStatus > 0) {
              System.out.println("Tokens saved in DB");
            }
          }
        }
        
      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage()); 
      }

    } else {
      System.out.println("Invalid state - possible CSFR");
    }
    model.addAttribute("code", code);
    return "callback";
  }
  public JsonNode getTenant(String accessToken) {
    JsonNode jsonNode = null;
    try {
      //getting tenant id
      
      HttpURLConnection getTenantCon = (HttpURLConnection) new URL(XeroConnectionsAPI).openConnection();
      getTenantCon.setRequestMethod("GET");
      getTenantCon.setDoOutput(true); 
      getTenantCon.setRequestProperty("Authorization", "Bearer " + accessToken);
      getTenantCon.setRequestProperty("Content-Type", "application/json");
      //getTenantCon.getInputStream();
      getTenantCon.connect();
      int resTenant = getTenantCon.getResponseCode();
      if (100 <= resTenant && resTenant <= 399) { 
        BufferedReader tenantBr = null;
        StringBuilder tenantSb = null;
        tenantBr = new BufferedReader(new InputStreamReader(getTenantCon.getInputStream()));
        tenantSb = new StringBuilder();
        String tenantOutput;
        while ((tenantOutput = tenantBr.readLine()) != null) {
          tenantSb.append(tenantOutput);
        }
        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(tenantSb.toString());
        
      }
    } catch(Exception e) {
      System.out.println("Exception : "+ e.getMessage());
    }
    return jsonNode;
  }

  
  public String getAccessTokenWithTenantId(String tenantId) {
    String accessToken = "";
    try {
        String getTokenQry = "SELECT * FROM customers WHERE realmId = '" + tenantId + "'";
        //accessToken = jdbcTemplate.queryForObject(getTokenQry, String.class);
        List<Map<String, Object>> tokensData = jdbcTemplate.queryForList(getTokenQry); 
        accessToken = tokensData.get(0).get("accessToken").toString();
        String accessTokenExpire = tokensData.get(0).get("accessTokenExpire").toString();
        String refreshToken = tokensData.get(0).get("refreshToken").toString();
        long currTimeStamp = new Date().getTime();
        //if the current time stamp is greater than the saved timestamp then new access token will fetch
        if(currTimeStamp > Long.parseLong(accessTokenExpire)) {
          String urlParameters = "grant_type=refresh_token&refresh_token="+refreshToken;
          byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
          
          HttpURLConnection con = (HttpURLConnection) new URL(XeroTokenAPI).openConnection();
          con.setRequestMethod("POST");
          con.setDoOutput(true); 
          con.setRequestProperty("Authorization", authHeaderValue);
          con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          con.getOutputStream().write(postData);
          con.getInputStream();
          con.connect();
          
          int resCode = con.getResponseCode();
          BufferedReader br = null;
          StringBuilder sb = null;
          if (100 <= resCode && resCode <= 399) {
              br = new BufferedReader(new InputStreamReader(con.getInputStream()));
              sb = new StringBuilder();
              String output;
              while ((output = br.readLine()) != null) {
                sb.append(output);
              }
              
          } else {
              br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
              sb = new StringBuilder();
              String output;
              while ((output = br.readLine()) != null) {
                sb.append(output);
              }
          }
          ObjectMapper mapper = new ObjectMapper();
          JsonNode jsonNode = mapper.readTree(sb.toString());
          

          if(jsonNode.get("access_token") != null) {
            accessToken = jsonNode.get("access_token").asText();
            long newExpireTime = currTimeStamp + 1800000;
            String updateTokenQry = "UPDATE customers set accessToken = ?, accessTokenExpire = ? where realmId = ?";
            int tokenInsertStatus = jdbcTemplate.update(updateTokenQry, accessToken, newExpireTime, tenantId);
            if(tokenInsertStatus > 0) {
              System.out.println("Tokens saved in DB");
            }
          } else {
            System.out.println("Error while getting tokens");
          }
        }

    } catch (Exception e) {
      System.out.println("Exception occured: "+ e.getMessage());
    }
    return accessToken;
  }

  
  //Creating cookie function
  public void saveCookie(HttpServletResponse response, String key, String value) {
    Cookie t = new Cookie(key, value);
    response.addCookie(t);
  }

  //Getting value of saved cookie
  public String getCookie(HttpServletRequest request, String key) {
    String item = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key)) {
                item = cookies[i].getValue();
            }
        }
    }
    return item;
  }


}
