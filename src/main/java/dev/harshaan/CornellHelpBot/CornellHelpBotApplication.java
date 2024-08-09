package dev.harshaan.CornellHelpBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;

@SpringBootApplication
public class CornellHelpBotApplication implements CommandLineRunner {
	private final CornellHelpBotService cornellHelpBotService;

	public CornellHelpBotApplication(CornellHelpBotService cornellHelpBotService) {
		this.cornellHelpBotService = cornellHelpBotService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CornellHelpBotApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		String submissionId = "10ed8si";

		cornellHelpBotService.replyToLatestTopLevelComment(submissionId);
	}
}