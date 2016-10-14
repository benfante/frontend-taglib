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
package com.benfante.taglib.frontend.tags;

import java.io.IOException;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 * @author Michele Franzin
 * @author Lucio Benfante
 */
public class AlertTag extends  RequestContextAwareTag {
    
   protected String type = null;
    
   public String getType() {
       return (this.type);
   }
   public void setType(String type) {
       this.type = type;
   } 
   
   @Override
   protected final int doStartTagInternal() throws JspException, IOException {
        try {
            Map<String, String> flash = retrieveFlashMap();
            if (flash != null && flash.get(type) != null) {
                // Resolve the message.
                MessageSource messageSource = getMessageSource();
                if (messageSource == null) {
                        throw new JspTagException("No corresponding MessageSource found");
                }
                String msg = "";
                try {
                    Object[] argumentsArray = {};
                    msg = messageSource.getMessage(flash.get(type), argumentsArray, getRequestContext().getLocale());
                }
                catch (Exception ex)  {
                    // If the message is unresolved, use the key as message
                    msg = flash.get(type);
                }
                
                // Write the message
                writeMessage(msg);
                
                // And clean the session
                flash.remove(type);
            }
            return EVAL_BODY_INCLUDE;
        }
        catch (NoSuchMessageException ex) {
                throw new JspTagException(getNoSuchMessageExceptionDescription(ex));
        }
    }

   /**
    * Retrieve the flash map from its stored location (usually from the request or the session).
    * @return The flash map.
    */
    @SuppressWarnings("unchecked")
    private Map<String, String> retrieveFlashMap() {
        Map<String, String> flash = (Map<String, String>) pageContext.getRequest().getAttribute("flash");
        if (flash == null) flash = (Map<String, String>) pageContext.getSession().getAttribute("flash");
        return flash;
    }

    /**
     * Write the message to the page.
     * @param msg the message to write
     * @throws IOException if writing failed
     */
    protected void writeMessage(String msg) throws IOException {
        pageContext.getOut().write("<div class=\"alert alert-" + type
                + " alert-dismissible fade in\" role=\"alert\"><button type=\"button\" class=\"close\" aria-label=\"Close\" data-dismiss=\"alert\" >"
                + "<span aria-hidden=\"true\">&times;</span>"
                + "</button>"
                + msg + "</div>");
    }
    
    /**
     * Use the application context itself for default message resolution.
     */
    protected MessageSource getMessageSource() {
            return getRequestContext().getWebApplicationContext();
    }

    /**
     * Return default exception message.
     */
    protected String getNoSuchMessageExceptionDescription(NoSuchMessageException ex) {
            return ex.getMessage();
    }

}
