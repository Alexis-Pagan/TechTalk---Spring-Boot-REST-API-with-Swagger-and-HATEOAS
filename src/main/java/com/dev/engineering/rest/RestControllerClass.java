package com.dev.engineering.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.engineering.entities.DaoUser;
import com.dev.engineering.headers.Headers;
import com.dev.engineering.jpa.hb.HibernateRepository;
import com.dev.engineering.model.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "rest service to process customer information")
public class RestControllerClass {

	@Autowired
	private HibernateRepository hr;

	MultiValueMap<String, String> headDef = Headers.construct();

	Message ms = new Message();

	DaoUser sM = new DaoUser();
	
	Resource<DaoUser> resource = new Resource<>(sM);



	/*@Autowired 
	private MailUtils utils;

	@ApiOperation(value="Send Email")
	@PostMapping("api/v1/send/mail")
	public ResponseEntity<HttpStatus> mail() {

		MailModel model = new MailModel();
		model.setContent("Sending Message from REST"); // content of email
		model.setFrom("your-mail"); // email from with @
		model.setSubject("Web Service Mail");
		model.setTo("other-mail"); // to with @

		try {
			utils.message(model);
		} catch(MailException mailEx) {
			System.out.println(mailEx);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}*/

	/*@ApiOperation(value="Send message")
	@GetMapping(value="api/v1/message/{ms}", produces="application/json")
	public ResponseEntity<Message> simpleMessage(@PathVariable("ms") String ms) {

		Message msg = new Message();

		msg.setText("Mensaje por Segmento de Path: " + ms);

		MultiValueMap<String, String> headDef = Headers.construct();

		return new ResponseEntity<Message>(msg, headDef, HttpStatus.OK);
	}*/

	@ApiOperation(value="Read all profiles in available", produces="application/json")
	@GetMapping(value="api/v1/all/customers")
	public ResponseEntity<List<DaoUser>> profiles() {

		List<DaoUser> ulist = new ArrayList<DaoUser>();

		Iterable<DaoUser> a = hr.findAll();
		for(DaoUser du: a) {
			ulist.add(du);
		}

		if(a.equals(null)) {
			Message ms = new Message();
			ms.setMessage("No profiles to show. Database may be empty.");
			return new ResponseEntity<List<DaoUser>>(ulist, headDef, HttpStatus.OK);
		}

		return new ResponseEntity<List<DaoUser>>(ulist, HttpStatus.FOUND);
	}

	@ApiOperation(value="Create all profiles available")
	@PostMapping(value="api/v1/customers", produces="application/hal+json")
	public ResponseEntity<Resource<DaoUser>> create(@ApiParam("Expecing JSON body request") @RequestBody DaoUser daouser) {

		int employeeNumber = daouser.getEmployeeNumber();		

		try {
			boolean result = hr.existByEmployeeNumber(employeeNumber);

			if(result) {
				return new ResponseEntity<Resource<DaoUser>>(HttpStatus.CONFLICT);
			} 

		} catch(Exception ex) {
			ex.printStackTrace();
		}

		if(daouser != null) {
			hr.createUser(daouser);
		}

		ms.setMessage("Welcome:" + daouser.getFirstName() + " to " + "PayTrix, Inc.");
		
		DaoUser user = new DaoUser(daouser.getFirstName(), daouser.getLastName(), daouser.getMiddleName(), daouser.getEmployeeNumber());
		Resource<DaoUser> resource = new Resource<DaoUser>(user);
		
		Link link = ControllerLinkBuilder
				.linkTo(RestControllerClass.class)// which class to search for HATEOAS
				.slash("api/v1/all/customers") // reference link
				.withSelfRel(); // to self href
		resource.add(link);
		
		return new ResponseEntity<Resource<DaoUser>>(resource,HttpStatus.OK);
	}

	@ApiOperation(value="Update a resource-based information")
	@PutMapping(value="api/v1/customers/informations/{employeeNumber}", produces="application/json")
	public ResponseEntity<Message> update(@ApiParam("Expecing JSON body request") @RequestBody DaoUser user, @PathVariable("employeeNumber") String employeeNumber) {
		Message ms = new Message();

		if(user != null && employeeNumber != null) {
			hr.updateExistingEmployee(user, employeeNumber);
		}

		ms.setMessage("PayTrix customer with number id : " + employeeNumber + " updated.");

		return new ResponseEntity<Message>(ms, headDef, HttpStatus.OK);
	}

	@ApiOperation(value="Delete an identified resource-based information : customer profiles")
	@DeleteMapping(value="api/v1/specific-profiles/{employeeNumber}")
	public ResponseEntity<Message> delete(@ApiParam("Expecing integer value") @PathVariable("employeeNumber") int employeeNumber) {

		hr.deleteExistingEmployee(employeeNumber);

		Message ms = new Message();
		ms.setMessage("PayTrix customer with employee number: " + employeeNumber + " removed.");

		return new ResponseEntity<Message>(ms, headDef, HttpStatus.OK);
	}
}