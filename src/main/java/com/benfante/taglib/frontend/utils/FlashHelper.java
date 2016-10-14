/**
 * Copyright 2016 Lucio Benfante <lucio.benfante@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.benfante.taglib.frontend.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.util.StringUtils;

/**
 * An helper for setting message that can be shown in page after a forward ot
 * a redirect.
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 * @author Lucio Benfante <lucio@benfante.com>
 * @author Michele Franzin
 */
public class FlashHelper {
    public static String DEFAULT_NOTICE_TYPE = "success";
    public static String DEFAULT_ERROR_TYPE = "danger";
    public static String DEFAULT_INFO_TYPE = "info";
    public static String DEFAULT_WARNING_TYPE = "warning";
    public static String DEFAULT_FLASH_ATTRIBUTE = "flash";
    public static Locale DEFAULT_LOCALE = Locale.getDefault(Locale.Category.DISPLAY);
    
    public static void setError(HttpServletRequest req, String code) {
        doSet(req, code, DEFAULT_ERROR_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void setNotice(HttpServletRequest req, String code) {
        doSet(req, code, DEFAULT_NOTICE_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void setInfo(final HttpServletRequest req, final String code) {
        doSet(req, code, DEFAULT_INFO_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }

    public static void setWarning(final HttpServletRequest req, final String code) {
        doSet(req, code, DEFAULT_WARNING_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void doSet(HttpServletRequest req, String code, String type, String attribute) {
        @SuppressWarnings("unchecked")
        Map<String, String> flash = (Map<String, String>)req.getAttribute(attribute);
        if (flash == null) flash = new HashMap<String, String>();
        if (type == null) {
            type = DEFAULT_ERROR_TYPE;
        }
        if (attribute == null) {
            attribute = DEFAULT_FLASH_ATTRIBUTE;
        }
        flash.put(type, code);
        req.setAttribute(attribute, flash);
    }

    public static void setRedirectError(HttpServletRequest req, String code) {
        doSetRedirect(req, code, DEFAULT_ERROR_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void setRedirectNotice(HttpServletRequest req, String code) {
        doSetRedirect(req, code, DEFAULT_NOTICE_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void setRedirectInfo(final HttpServletRequest req, final String code) {
        doSetRedirect(req, code, DEFAULT_INFO_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }

    public static void setRedirectWarning(final HttpServletRequest req, final String code) {
        doSetRedirect(req, code, DEFAULT_WARNING_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void doSetRedirect(HttpServletRequest req, String code, String type, String attribute) {
        @SuppressWarnings("unchecked")
        Map<String, String> flash = (Map<String, String>)req.getSession().getAttribute(attribute);
        if (flash == null) flash = new HashMap<String, String>();
        if (type == null) {
            type = DEFAULT_ERROR_TYPE;
        }
        if (attribute == null) {
            attribute = DEFAULT_FLASH_ATTRIBUTE;
        }
        flash.put(type, code);
        req.getSession().setAttribute(attribute, flash);
    }
    
    public static String getAccumulatedMessages(String mainMessage,
            List<String> accumulatedMessages, MessageSource messageSource, Locale locale) {
        StringBuilder sb = new StringBuilder();
        if (messageSource == null) {
            messageSource = new StaticMessageSource();
            ((StaticMessageSource) messageSource).setUseCodeAsDefaultMessage(true);
        }
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        if (StringUtils.hasText(mainMessage)) {
            sb.append(messageSource.getMessage(mainMessage, null, locale));
        }
        if (accumulatedMessages != null && !accumulatedMessages.isEmpty()) {
            sb.append("<ul>");
            for (String message : accumulatedMessages) {
                if (StringUtils.hasText(message)) {
                    sb.append("<li>")
                            .append(messageSource.getMessage(message, null, locale))
                            .append("</li>");
                }
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }
    
}

