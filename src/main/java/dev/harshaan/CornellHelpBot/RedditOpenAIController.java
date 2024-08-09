package dev.harshaan.CornellHelpBot;

import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RedditOpenAIController {

    private final CornellHelpBotService cornellHelpBotService;

    public RedditOpenAIController(CornellHelpBotService cornellHelpBotService) {
        this.cornellHelpBotService = cornellHelpBotService;
    }

    @GetMapping("/reply-to-comments")
    public String replyToComments(@RequestParam String submissionId) throws IOException, JSONException {
        cornellHelpBotService.replyToLatestTopLevelComment(submissionId);
        return "Replied to recent comments in the submission!";
    }
}