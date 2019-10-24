package cats.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cats.service.HobbyService;
import cats.service.SchoolService;
import cats.service.StudentService;

@RestController
@RequestMapping(value = { "/Profile" })
public class ProfileController {
	@Autowired
	HobbyService hobbyService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	StudentService studentService;

	@Autowired
	HttpSession session;

}
