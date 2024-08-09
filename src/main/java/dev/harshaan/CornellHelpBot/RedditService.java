package dev.harshaan.CornellHelpBot;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Comment;
import net.dean.jraw.references.SubmissionReference;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.Optional;

@Service
public class RedditService {

    private final RedditClient redditClient;

    @Autowired
    public RedditService(RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    public Comment getLatestTopLevelComment(String submissionId) {
        SubmissionReference submissionReference = redditClient.submission(submissionId);
        RootCommentNode rootCommentNode = submissionReference.comments();

        //System.out.println("Raw comments data: " + rootCommentNode.getReplies());

        Optional<Comment> latestTopLevelComment = rootCommentNode.getReplies().stream()
                .map(CommentNode::getSubject)
                .filter(comment -> !comment.getBody().equals("[deleted]"))
                .max(Comparator.comparing(Comment::getCreated));

        if (latestTopLevelComment.isPresent()) {
            Comment latestComment = latestTopLevelComment.get();
            System.out.println("Latest top-level comment: " + latestComment.getBody() + ", ID: " + latestComment.getId());
            return latestComment;
        }
        else {
            System.out.println("No valid top-level comment found.");
            return null;
        }
    }
}