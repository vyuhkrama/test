package com.test.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.test.web.form.LoginForm;
import com.test.web.service.LoginServiceInterface;

@Controller
public class LoginController {

	@Autowired
	public LoginServiceInterface loginService;
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out from JournalDEV successfully.");
		}
		LoginForm loginForm = new LoginForm();
		model.addObject("loginForm", loginForm);
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String processForm(@Valid LoginForm loginForm, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "login";
		}
		
		boolean userExists = loginService.checkLogin(loginForm.getUserName(),loginForm.getPassword());
		if(userExists){
			model.put("loginForm", loginForm);
			System.out.println(loginForm.getUserName());
			return "loginsuccess";
		}else{
			result.rejectValue("userName","invaliduser");
			return "login";
		}
	}	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Locale locale, Model model) {
		return "admin";
	}
	 @RequestMapping(value="/logout", method = RequestMethod.GET)
	    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null){    
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
	        return "redirect:/login?logout";
	    }
	  @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	    public String accessDeniedPage(ModelMap model) {
	        model.addAttribute("user", getPrincipal());
	        return "accessDenied";
	    }
	  
	  private String getPrincipal(){
	        String userName = null;
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 
	        if (principal instanceof UserDetails) {
	            userName = ((UserDetails)principal).getUsername();
	        } else {
	            userName = principal.toString();
	        }
	        return userName;
	    }
}
