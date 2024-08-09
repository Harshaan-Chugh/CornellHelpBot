# Cornell Help Bot

Cornell Help Bot is a Spring Boot application that automates responses to prospective Cornell students on the Cornell subreddit. The bot monitors specific threads, fetches the latest top-level comment, and uses OpenAI's GPT-4 API to generate a context-aware reply tailored to admissions and life at Cornell University.

## Features

- Fetches the latest top-level comment from a specific Reddit submission.
- Generates a context-aware response using OpenAI's GPT-4 API.
- Automatically replies to the comment on Reddit.
- Handles potential issues like deleted comments and network errors.

## Technologies Used

- **Java 22**
- **Spring Boot**
- **Reddit API** (via [JRAW](https://github.com/mattbdean/JRAW))
- **OpenAI GPT-4o API**
- **OkHttp** (for making HTTP requests)
- **Maven** (for dependency management)

## Prerequisites

Before running the application, ensure you have the following set up:

1. **Java 11** or higher
2. **Maven** (for building the project)
3. **Reddit API credentials** (Client ID, Client Secret, Username, Password)
4. **OpenAI API Key**

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/CornellHelpBot.git
cd CornellHelpBot
```

### 2. Set Up Environment Variables
Create a `.env` file in the root directory of your project and add the needed environment variables:
```
USERNAME=your_reddit_user
PASSWORD=your_reddit_pw
CLIENT_ID=your_reddit_client_id
CLIENT_SECRET=your_reddit_client_secret
OPENAI_API_KEY=your_openai_api_key
```

### 3. Build the Project & Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Interacting with the Bot
Once the application is running, you can get the bot to reply to a comment in Cornell's "Chance Me! and Prospective Student Q&A" thread or any post (you can choose) by running the `CornellHelpBotApplication`.

Or you can use a Client URL to trigger it using a REST API:
```
http://localhost:8080/reply-to-comments?submissionId={submission_id}
```

### Project Structure and Classes
`CornellHelpBotApplication`: The entry point for the Spring Boot application.
`CornellHelpBotService`: Handles the core functionality of fetching comments and generating replies.
`RedditService`: Manages interaction with Reddit, fetching the latest top-level comments.
`OpenAIService`: Manages interaction with OpenAI's GPT-4 API to generate responses.
`RedditConfig`: Configures the Reddit client with necessary credentials.
`RedditOpenAIController`: Provides REST endpoints to trigger bot actions.

### Contributing
Contributions are welcome.
Thanks to [JRAW](https://github.com/mattbdean/JRAW) and [OpenAI](https://openai.com/) for providing a Java Wrapper for Reddit's API and the OpenAI API!
