package ru.sandwichcloud.email;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Handles email content as taco orders where...</p>
 *  <li> The order's email is the sender's email</li>
 *  <li> The email subject line *must* be "SANDWICH ORDER" or else it will be ignored</li>
 *  <li> Each line of the email starts with the name of a sandwich design, followed by a colon,
 *    followed by one or more ingredient names in a comma-separated list.</li>
 *
 * <p>The ingredient names are matched against a known set of ingredients using a LevenshteinDistance
 * algorithm. As an example "bread" will match "Dark Bread" and be mapped to "DARK".</p>
 *
 * <p>An example email body might look like this:</p>
 *
 * <code>
 * Sandwich 1: dark, beef, cucumber, tomatoes, cheddar<br/>
 * Veggielicious: white, cucumber, tomatoes, mayonnaise
 * </code>
 *
 * <p>This will result in an order with two tacos where the names are "Sandwich 1" and "Veggielicious".
 * The ingredients will be {DARK, BEEF, CMBR, TMTO, CHED} and {WHTE,CMBR,TMTO,MAYO}.</p>
 */
@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private static Logger log = LoggerFactory.getLogger(EmailToOrderTransformer.class);
    private static final String SUBJECT_KEYWORDS = "SANDWICH ORDER";

    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message mailMessage) {
        EmailOrder sandwichOrder = processPayload(mailMessage);
        return MessageBuilder.withPayload(sandwichOrder);
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break;
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    private EmailOrder processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();

            if(subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = getTextFromMessage(mailMessage);

                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException e) {
            log.error("MessagingException: {}", e);
        } catch (IOException e) {
            log.error("IOException: {}", e);
        }

        return null;
    }

    private EmailOrder parseEmailToOrder(String email, String content) {
        EmailOrder order = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");

        for (String line : lines) {

            if (line.trim().length() > 0 && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String sandwichName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<String> ingredientCodes = new ArrayList<>();

                Sandwich sandwich = new Sandwich(sandwichName);
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());

                    if (code != null)
                        ingredientCodes.add(code);

                    sandwich.setIngredients(ingredientCodes);
                }
                order.addSandwich(sandwich);
            }
        }
        log.info("ORDER");
        log.info(order.toString());
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {

        for (Ingredient ingredient : ALL_INGREDIENTS) {
            String ucIngredientName = ingredientName.toUpperCase();
            String ingredientUpperCase = ingredient.getName().toUpperCase();

            if (LevenshteinDistance.getDefaultInstance()
                    .apply(ucIngredientName, ingredientUpperCase) < 3 ||
                ucIngredientName.contains(ingredientUpperCase) ||
                    ingredientUpperCase.contains(ucIngredientName)) {

                return ingredient.getCode();
            }
        }
        return null;
    }

    private static final Ingredient[] ALL_INGREDIENTS = new Ingredient[] {
            new Ingredient("DARK", "Dark Bread"),
            new Ingredient("WHTE", "White Bread"),
            new Ingredient("BEEF", "Beef"),
            new Ingredient("PORK", "Pork"),
            new Ingredient("TMTO", "Tomatoes"),
            new Ingredient("CMBR", "Cucumber"),
            new Ingredient("CHED", "Cheddar"),
            new Ingredient("MSDM", "Maasdam"),
            new Ingredient("MAYO", "Mayonnaise"),
            new Ingredient("MTRD", "Mustard")
    };
}
