package com.steamrankings;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// HTTP requests handled by the controller
// You identify controllers in Spring by using the annotation @Controller
@Controller
public class ProfileController {
	// This annotation sets that the HTTP requests of /profile are handled by the getProfile method
	@RequestMapping("/profile")
	public String getProfile(String id, Model model) {
		if(id == null)
		{
			model.addAttribute("message", "Invalid Steam user ID");
			return "profile";
		}
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, false);
        HttpGet request = new HttpGet("http://localhost:6789/getSteamID?id=" + id);
        String name = null;
        
        try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			byte[] buffer = new byte[10000];
			while ((is.read(buffer)) != -1);
			is.close();
			name = new String(buffer, "UTF-8");
			
			System.out.println(name.trim());
					
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(name == null)
        	model.addAttribute("message", "Invalid Steam user ID");
        else {
        	System.out.println("Hello, " + name.trim() + "!");
        	model.addAttribute("message", "Hello, " + name.trim() + "!");
        }
		return "profile";
	}
}