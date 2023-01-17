package com.email.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;




@SpringBootApplication
public class DemoApplication {



	// change all these to integrate the database
	private String address = "pasddemo@gmail.com";
	private String subject = "Your package was collected";

	private String notification = "Dear PutNameHere, your package was collexted and will be delivered shortly";

	@Autowired
	private EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// Just for now, runs when the code is run
	// We can change this later to be called when something is updated in the database e.g.
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {

		senderService.sendNotification(address, subject, notification);
	}

}
