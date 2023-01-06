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
        log.info("LOG1");
        if (message.isMimeType("text/plain")) {
            log.info("LOG2");
            result = message.getContent().toString();
            log.info("LOG3");
        } else if (message.isMimeType("multipart/*")) {
            log.info("LOG4");
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            log.info("LOG5");
            result = getTextFromMimeMultipart(mimeMultipart);
            log.info("LOG6");
        }
        log.info("LOG7");
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        log.info("LOG8");
        int count = mimeMultipart.getCount();
        log.info("LOG9");
        for (int i = 0; i < count; i++) {
            log.info("LOG10");
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            log.info("LOG11");
            if (bodyPart.isMimeType("text/plain")) {
                log.info("LOG12");
                result = result + "\n" + bodyPart.getContent();
                log.info("LOG12");
                break; // without break same text appears twice in my tests
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                log.info("LOG13");
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
                log.info("LOG14");
            }
            log.info("LOG15");
        }
        log.info("LOG16");
        return result;
    }



    private EmailOrder processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();

            log.info("LOGLOGLOG");
            log.info("SUBJECT " + subject);
            log.info(getTextFromMessage(mailMessage));

            if(subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = getTextFromMessage(mailMessage);
                log.info("CONTENT: + \n" + content);
                log.info("\n");
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

                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());

                    if (code != null)
                        ingredientCodes.add(code);

                    Sandwich sandwich = new Sandwich(sandwichName);
                    sandwich.setIngredients(ingredientCodes);
                    order.addSandwich(sandwich);
                }
            }
        }
        log.info("ORDER + \n");
        log.info(order.toString());
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {

        for (Ingredient ingredient : ALL_INGREDIENTS) {
            String ucIngredientName = ingredientName.toUpperCase();

            if (LevenshteinDistance.getDefaultInstance()
                    .apply(ucIngredientName, ingredient.getName()) < 3 ||
                ucIngredientName.contains(ingredient.getName()) ||
                ingredient.getName().contains(ucIngredientName)) {

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
