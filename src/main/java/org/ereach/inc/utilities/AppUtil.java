package org.ereach.inc.utilities;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.services.notifications.EReachNotificationRequest;

import java.lang.reflect.Field;

public class AppUtil {
	
	public static ReplaceOperation doReplace(Object request, Field field) {
		field.setAccessible(true);
		String jsonPointerPath = "/"+field.getName();
		try {
			JsonPointer jsonPointer = new JsonPointer(jsonPointerPath);
			Object accessedField = field.get(request);
			TextNode textNode = new TextNode(accessedField.toString());
			return new ReplaceOperation(jsonPointer, textNode);
		} catch (Throwable exception) {
			throw new EReachUncheckedBaseException(exception);
		}
	}
	
	public static boolean filterEmptyField(Object request, Field field) {
		field.setAccessible(true);
		try {
			return field.get(request) != null;
		} catch (IllegalAccessException e) {
			throw new EReachUncheckedBaseException(e);
		}
	}
}
