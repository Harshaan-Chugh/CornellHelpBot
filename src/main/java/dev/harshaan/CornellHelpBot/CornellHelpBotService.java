package dev.harshaan.CornellHelpBot;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Comment;
import net.dean.jraw.references.CommentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import net.dean.jraw.ApiException;
import net.dean.jraw.http.NetworkException;

@Service
public class CornellHelpBotService {

    private final RedditService redditService;
    private final OpenAIService openAIService;
    private final RedditClient redditClient;

    @Autowired
    public CornellHelpBotService(RedditService redditService, OpenAIService openAIService, RedditClient redditClient) {
        this.redditService = redditService;
        this.openAIService = openAIService;
        this.redditClient = redditClient;
    }

    public void replyToLatestTopLevelComment(String submissionId) throws IOException {
        Comment latestTopLevelComment = redditService.getLatestTopLevelComment(submissionId);

        if (latestTopLevelComment != null) {
            String prompt = latestTopLevelComment.getBody();
            String reply = openAIService.generateReply(prompt);

            System.out.println("OpenAI generated reply: " + reply);

            try {
                CommentReference commentReference = redditClient.comment(latestTopLevelComment.getId());
                commentReference.reply(reply);
                System.out.println("Replied to comment: " + latestTopLevelComment.getId());
            }
            catch (ApiException e) {
                if (e.getMessage().contains("DELETED_COMMENT")) {
                    System.out.println("Cannot reply to a deleted comment: " + latestTopLevelComment.getId());
                }
                else {
                    throw e;
                }
            }
            catch (NetworkException e) {
                System.out.println("Network error occurred: " + e.getMessage());
                throw e;
            }
        }
        else {
            System.out.println("No new top-level comment to reply to.");
        }
    }
}
