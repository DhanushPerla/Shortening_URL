import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class LinkShortener {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private final HashMap<String, String> urlMap; // Maps short URL to long URL
    private final HashMap<String, String> reverseUrlMap; // Maps long URL to short URL
    private final Random random;

    public LinkShortener() {
        urlMap = new HashMap<>();
        reverseUrlMap = new HashMap<>();
        random = new Random();
    }

    // Method to shorten a long URL with error handling
    public String shortenURL(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            return "Error: URL cannot be null or empty."; // Error feedback
        }

        if (reverseUrlMap.containsKey(longUrl)) {
            return "Short URL already exists: " + reverseUrlMap.get(longUrl); // Feedback on duplicate URL
        }

        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlMap.containsKey(shortUrl)); // Ensure uniqueness

        urlMap.put(shortUrl, longUrl);
        reverseUrlMap.put(longUrl, shortUrl);
        return "Shortened URL: " + shortUrl;
    }

    // Method to expand a short URL to the original long URL with error handling
    public String expandURL(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            return "Error: Short URL cannot be null or empty."; // Error feedback
        }

        String longUrl = urlMap.get(shortUrl);
        if (longUrl == null) {
            return "Error: Short URL not found."; // Error feedback for invalid URL
        }

        return "Expanded URL: " + longUrl;
    }

    // Generate a random short URL using specified length
    private String generateShortUrl() {
        StringBuilder sb = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkShortener shortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Link Shortener!");
        System.out.println("Commands: 'shorten <URL>' to shorten, 'expand <shortURL>' to expand, 'exit' to quit.");

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2); // Split input into command and URL

            if (parts.length < 2 && !parts[0].equalsIgnoreCase("exit")) {
                System.out.println("Error: Command requires a URL. Try again.");
                continue;
            }

            String command = parts[0].toLowerCase();
            String url = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "shorten":
                    System.out.println(shortener.shortenURL(url));
                    break;
                case "expand":
                    System.out.println(shortener.expandURL(url));
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Error: Unknown command. Use 'shorten', 'expand', or 'exit'.");
            }
        }
    }
}
