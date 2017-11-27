package com.wesa.elasticsearch.core;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.postgresql.util.PSQLException;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wesa.elasticsearch.core.model.User;
import com.wesa.elasticsearch.core.shiro.BasicPrincipal;
import com.wesa.elasticsearch.core.shiro.permissions.UserActionsPermissions;

import javax.ws.rs.BadRequestException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SwaggerDefinition(securityDefinition = @SecurityDefinition(apiKeyAuthDefinitions = {
		@ApiKeyAuthDefinition(key = "username", name = "username", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER),
		@ApiKeyAuthDefinition(key = "password", name = "password", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER) }))
public abstract class Resource {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static Logger logger = LoggerFactory.getLogger(Resource.class);

	protected Date extractDateParamAsBeginOfDate(String dateString) {
		Date date = null;
		if (StringUtils.isNotEmpty(dateString)) {
			try {
				date = format.parse(dateString + " 00:00:00");
			} catch (ParseException e) {
				throw new BadRequestException("Wrong date format. Should be yyyy-MM-dd");
			}
		}
		return date;
	}

	protected User getLoggedUser() {
		BasicPrincipal bp = (BasicPrincipal) ThreadContext.getSubject().getPrincipal();
		return bp.getUser();
	}

	protected Date extractDateParamAsEndOfDate(String dateString) {
		Date date = null;
		if (StringUtils.isNotEmpty(dateString)) {
			try {
				date = format.parse(dateString + " 23:59:59");
			} catch (ParseException e) {
				throw new BadRequestException("Wrong date format. Should be yyyy-MM-dd");
			}
		}
		return date;
	}

	protected String getDateHash() throws NoSuchAlgorithmException {
		MessageDigest instance = MessageDigest.getInstance("MD5");
		byte[] messageDigest = instance.digest(String.valueOf(System.nanoTime()).getBytes());
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < messageDigest.length; i++) {
			String hex = Integer.toHexString(0xFF & messageDigest[i]);
			if (hex.length() == 1) {
				// could use a for loop, but we're only dealing with a single
				// byte
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	protected void parseJDBIExceptionAndThrowBadRequest(DBIException e, String messageToThrow) {

		/* catch unique_violation and throw 'exists' or custom message */
		if (e.getCause() instanceof PSQLException && ((PSQLException) e.getCause()).getSQLState().equals("23505")) {
			throw new BadRequestException(StringUtils.substringBetween(e.getCause().getMessage(), "(", ")")
					+ (messageToThrow == null ? " exists" : " " + messageToThrow));
		}

		/*
		 * catch not_null_violation and throw 'cannot be empty' or custom
		 * message
		 */
		if (e.getCause() instanceof PSQLException && ((PSQLException) e.getCause()).getSQLState().equals("23502")) {
			throw new BadRequestException(StringUtils.substringBetween(e.getCause().getMessage(), "\"")
					+ (messageToThrow == null ? " cannot be empty" : " " + messageToThrow));
		}

		logger.error("uncaught SQL exception" + e.getCause().getClass().toString());
		throw new BadRequestException("We have error. We will fix it!");
	}

	protected void compareUUIDsAndThrowBadRequest(Object first, Object second, String whatDontMatch) {
		if (!(first instanceof UUID))
			first = UUID.fromString(first.toString());

		if (!(second instanceof UUID))
			second = UUID.fromString(second.toString());

		if (!first.equals(second))
			throw new BadRequestException(whatDontMatch + " IDs in URI and payload don't match");
	}

	protected void checkForNotFoundAndThrowBadRequest(Object o, String whatNotFound) {
		if (o == null)
			throw new BadRequestException(whatNotFound + " not found");
	}

	protected void checkUserPermissions(String action, String id) {
		SecurityUtils.getSubject().checkPermission(new UserActionsPermissions(action, id+""));
	}

}
