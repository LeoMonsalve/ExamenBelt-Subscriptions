package com.leonel.examen.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.leonel.examen.models.Subscription;
import com.leonel.examen.models.User;
import com.leonel.examen.services.RoleService;
import com.leonel.examen.services.SubscriptionService;
import com.leonel.examen.services.UserService;
import com.leonel.examen.validator.UserValidator;


@Controller
@RequestMapping("/")
public class UserController {

	private UserValidator userValidator;
	private UserService userService;
	private RoleService roleService;
	private SubscriptionService subService;
	public UserController(UserService userService, UserValidator userValidator, RoleService roleService, SubscriptionService subService) {
	    this.userService = userService;
	    this.userValidator = userValidator;
	    this.roleService = roleService;
	    this.subService = subService;
    }
	
	@RequestMapping(value={"/login","/register"})
	public String login(Model model,@RequestParam(value="error",required=false) String error,@RequestParam(value="logout",required=false) String logout){
		if(error != null){model.addAttribute("errorMessage","Invalid Credentials.");}
		if(logout != null){model.addAttribute("logoutMessage","Logout Successful");}
		
		model.addAttribute("user",new User());
		return "loginRegister";
	}
	
	@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "loginRegister";
        }
        else if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
        		userService.saveUserWithAdminRole(user);
        		return "redirect:/login";
        } else {
        		userService.saveWithUserRole(user);
        		return "redirect:/login";
        }
    }
	
	@RequestMapping(value = {"/dashboard"})
	public String showHome(Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("currentUser", userService.findByEmail(email));
        userService.updateUser(user);
        if(userService.checkIfAdmin(user)) {
        		return "redirect:/admin";
        } else if(user.getSubscription() == null){
        		return "redirect:/signup";
        } else {
        		return "homePage";
        }
        
	}
	
	@RequestMapping("/admin")
	public String displayAdmin(Principal principal, Model model, @ModelAttribute("subscription") Subscription subscription) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("all", userService.getAll());
        model.addAttribute("allsubs", subService.getAll());
        return "adminPage";
	}
	
	@RequestMapping("/signup")
	public String getSub(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("allsub", subService.getAll());
        return "signUp";
	}
	@PostMapping("/createsub")
	public String createSub(Principal principal, @Valid @ModelAttribute("subscription") Subscription subscription, BindingResult result) {
		subService.createSub(subscription);
//		String email = principal.getName();
//        User user = userService.findByEmail(email);
        return "redirect:/admin";
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/signup")
	public String setSub(Principal principal, @RequestParam("due") Integer due, @RequestParam("subid") Long id) {
		String email = principal.getName();
		User user = userService.findByEmail(email);
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endofmonth = c.getTime();
		if(due < date.getDate()) {
			date.setMonth(date.getMonth()+1);
			date.setDate(due);
		} else if (due > endofmonth.getDate()) {
			date.setDate(endofmonth.getDate());
		}
		user.setDueDate(date);
		Subscription sub = subService.findSubscriptionById(id);
		user.setSubscription(sub);
		userService.updateUser(user);
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/")
	public String gotoLogin() {
		return "redirect:/login";
	}
	
	
	@RequestMapping("/sub/activate/{id}")
	public String activate(@PathVariable("id") Long id) {
		Subscription sub = subService.findSubscriptionById(id);
		Boolean availability = true;
		sub.setAvailability(availability);
		subService.updateSub(sub);
		return "redirect:/admin";
	}
	
	@RequestMapping("/sub/deactivate/{id}")
	public String deactivate(@PathVariable("id") Long id) {
		Subscription sub = subService.findSubscriptionById(id);
		Boolean availability = false;
		sub.setAvailability(availability);
		subService.updateSub(sub);
		return "redirect:/admin";
	}
	
	@RequestMapping("/sub/delete/{id}")
	public String deleteSub(@PathVariable("id") Long id) {
		subService.deleteSub(id);
		return "redirect:/admin";
	}
	@RequestMapping(value = "/subscriptions/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		Subscription sub = subService.findSubscriptionById(id);
		model.addAttribute("sub", sub);
		return "edit";
	}

	@RequestMapping(value = "/subscriptions/{id}/edit", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("sub") Subscription sub, BindingResult result, HttpSession session, @PathVariable("id") Long id) {
		System.out.println(result);
		if (result.hasErrors()) {
			return "edit";
		} else {
			Subscription sub2 = subService.findSubscriptionById(id);
			sub2.setPrice(sub.getPrice());
			subService.updateSub(sub2);
			return "redirect:/admin"; 		}
		
	}
//	
}
