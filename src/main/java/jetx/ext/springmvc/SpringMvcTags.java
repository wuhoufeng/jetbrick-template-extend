package jetx.ext.springmvc;


import static jetx.ext.internal.TagUtils.getRequest;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import jetbrick.template.JetAnnotations.Tags;
import jetbrick.template.runtime.JetTagContext;
import jetbrick.template.utils.ExceptionUtils;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.support.RequestContextUtils;


/**
 * 针对SpringMVC使用者的扩展Tag
 * 
 * @author 应卓(yingzhor@gmail.com)
 * @since 1.0.0
 */
@Tags
public final class SpringMvcTags {

	/* i18n
	 ------------------------------------------------------------------------------------------------------------------- */

	public static void i18n(JetTagContext ctx, String code) throws IOException {
		i18n(ctx, code, null);
	}
	
	public static void i18n(JetTagContext ctx, String code, Object[] args) throws IOException {
		i18n(ctx, code, args, "");
	}
	
	public static void i18n(JetTagContext ctx, String code, Object[] args, String defaultMessage)
			throws IOException {

		HttpServletRequest request = getRequest(ctx);
		
		MessageSource messageSource = RequestContextUtils.getWebApplicationContext(request);
		Locale locale = RequestContextUtils.getLocale(request);
		String value = null;
		
		try {
			value = messageSource.getMessage(code, args, defaultMessage, locale);
		} catch (NoSuchMessageException e) {
			ExceptionUtils.uncheck(e);
		}
		
		if (value != null) {
			ctx.getWriter().print(value);
		}
	}

}
