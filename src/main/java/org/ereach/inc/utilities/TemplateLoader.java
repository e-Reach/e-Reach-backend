package org.ereach.inc.utilities;

import lombok.extern.slf4j.Slf4j;

import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;
import static org.ereach.inc.utilities.Constants.TEMPLATE_LOAD_FAILED;
import static org.ereach.inc.utilities.JWTUtil.generateAccountActivationUrl;

@Slf4j
public class TemplateLoader {
	
	public static String loadTemplateContent(Resource templateResource) throws RequestInvalidException {
		Formatter formatter = new Formatter();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			InputStream inputStream = templateResource.getInputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
		} catch (IOException exception) {
			log.error(TEMPLATE_LOAD_FAILED, exception);
			throw new RequestInvalidException(TEMPLATE_LOAD_FAILED, exception);
		}
		return result.toString(StandardCharsets.UTF_8);
	}
	
	public static String getTemplate(Model model, EReachNotificationRequest notificationRequest) {
		model.addAttribute("Username", notificationRequest.getFullName());
		model.addAttribute("activationLink",
				generateAccountActivationUrl(notificationRequest.getEmail(), notificationRequest.getPassword(), notificationRequest.getPhoneNumber()));
		return "account_activation_mail_template";
	}
}
