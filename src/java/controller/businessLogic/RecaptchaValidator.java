package controller.businessLogic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
import java.util.logging.Logger;

/**
 * Lớp xử lý logic xác minh reCAPTCHA thông qua API Google.
 */
public class RecaptchaValidator {

    private static final Logger LOGGER = Logger.getLogger(RecaptchaValidator.class.getName());
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String RECAPTCHA_SECRET_KEY = "6LeerBIqAAAAAPphZrfsfrpyN4q8jVviHbz2pvRo"; // Nên đưa vào config

    /**
     * Xác minh reCAPTCHA thông qua API Google.
     *
     * @param recaptchaResponse Phản hồi từ client gửi đến từ reCAPTCHA
     * @return true nếu xác minh thành công, false nếu thất bại
     */
    public static boolean verifyRecaptcha(String recaptchaResponse) {
        if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(RECAPTCHA_URL);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String params = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + recaptchaResponse;
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(params);
                wr.flush();
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            JSONObject json = new JSONObject(response.toString());
            boolean success = json.getBoolean("success");
            if (!success) {
                LOGGER.warning("reCAPTCHA verification failed: " + json.toString());
            }
            return success;
        } catch (Exception e) {
            LOGGER.severe("Error verifying reCAPTCHA: " + e.getMessage());
            return false;
        }
    }
}
