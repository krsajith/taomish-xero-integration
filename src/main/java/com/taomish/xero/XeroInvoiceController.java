package com.taomish.xero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.http.*;

import com.taomish.xero.dto.InvoiceDTO;
import com.taomish.xero.service.XeroInvoiceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class XeroInvoiceController {

  private final XeroInvoiceService xeroInvoiceService;
  private String XeroClientId = "97C62FF3B0C7417393BC515F35A0554E";
  private String XeroClientSecret = "b6mIuZ7Kz0H1wQn_HjL1jGvKcyT2G16_YBJNXxcaPV1CAw7i";
  private String XeroRedirectURL = "http://localhost:8080/xero/callback";
  private String XeroAPIScopes = "openid profile email accounting.transactions accounting.settings accounting.contacts";

  public XeroInvoiceController(XeroInvoiceService xeroInvoiceService) {
    this.xeroInvoiceService = xeroInvoiceService;
  }

  @PostMapping("/submit-invoice")
	public InvoiceDTO submitInvoice(@RequestBody InvoiceDTO invoice) {
		return xeroInvoiceService.submitInvoice(invoice);
	}
  
  @GetMapping("/xero/authorization")
	public void authenticateXero(HttpServletResponse response) {
		//return "authorization";
    try {
      String secretState = "secret" + new Random().nextInt(999_999);
      String authURL = "https://login.xero.com/identity/connect/authorize?response_type=code&client_id="+this.XeroClientId+"&redirect_uri="+this.XeroRedirectURL+"&scope="+this.XeroAPIScopes+"&state="+secretState;
      this.saveCookie(response, "state", secretState);
      response.sendRedirect(authURL);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
	}

  @GetMapping("/xero/callback")
  public String xeroCallback(HttpServletRequest request, @RequestParam(value = "code", defaultValue = "", required = true) String code, @RequestParam(value = "state", defaultValue = "", required = true) String state, @RequestParam(value = "scope") String scope, @RequestParam(value = "session_state") String sessionState, Model model) {
    String secretState = getCookie(request, "state");
    String callbackCode = "";
    if (request.getParameter("code") != null) {
      callbackCode = request.getParameter("code");
    }
    if (request.getParameter("state") != null && secretState.equals(request.getParameter("state").toString())) {
    
      System.out.println("code: " + callbackCode);
      //String command = "curl -X POST https://postman-echo.com/post --data foo1=bar1&foo2=bar2";
      try {
        //Process process = Runtime.getRuntime().exec(command);
        
        String authorizationString = XeroClientId + ":" + XeroClientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(authorizationString.getBytes());
        String authHeaderValue = "Basic " + new String(encodedAuth);
        String urlParameters = "grant_type=authorization_code&code="+callbackCode+"&redirect_uri="+XeroRedirectURL;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        HttpURLConnection con = (HttpURLConnection) new URL("https://identity.xero.com/connect/token").openConnection();
        
        con.setRequestMethod("POST");
        con.setDoOutput(true); 
        con.setRequestProperty("Authorization", authHeaderValue);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.getOutputStream().write(postData);
        con.getInputStream();
        con.connect();
        
        int resCode = con.getResponseCode();
        String resMessage = con.getResponseMessage();
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
        System.out.println("Response from xero " + sb);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    } else {
      System.out.println("Invalid state - possible CSFR");
    }
    model.addAttribute("code", code);
    return "callback";
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
