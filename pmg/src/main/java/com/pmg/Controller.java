package com.pmg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;
import com.pmg.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
	@Autowired
	private UserService userService;
	
	@GetMapping("/pmg")
	public String pmg(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser != null) {
			User user = userService.findUserByUserId(loginUser.getUserId());
			model.addAttribute("loginUser", user);
		}
		String forwardPath = "pmg";
		return forwardPath;
	}

}
