/**
 *    Copyright 2016 Lucio Benfante <lucio.benfante@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.benfante.taglib.frontend.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 *
 * @author michele franzin <michele at franzin.net>
 */
public class TextareaTag extends org.springframework.web.servlet.tags.form.TextareaTag {

    private String label;
    private String prefix;
    private String suffix;
    private String help;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        tagWriter.startTag("div");
        if (this.getBindStatus().isError()) {
            tagWriter.writeAttribute("class", "control-group error");
        } else {
            tagWriter.writeAttribute("class", "control-group");
        }
        writeLabelTagContent(tagWriter);
        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "controls");
        String cssClasses = BootstrapTagHelper.prependAppendCssClasses(prefix, suffix);
        if (StringUtils.hasText(cssClasses)) {
            tagWriter.startTag("div");
            tagWriter.writeAttribute("class", cssClasses);
            BootstrapTagHelper.writeInputTagDecorator(tagWriter, prefix);
        }
        super.writeTagContent(tagWriter);
        if (StringUtils.hasText(cssClasses)) {
            BootstrapTagHelper.writeInputTagDecorator(tagWriter, suffix);
            tagWriter.endTag();
        }
        if (this.getBindStatus().isError()) {
            tagWriter.startTag("span");
            tagWriter.writeAttribute("id", autogenerateErrorId());
            tagWriter.writeAttribute("class", "help-inline");
            // writeDefaultAttributes(tagWriter);
            String delimiter = "<br/>";
            String[] errorMessages = getBindStatus().getErrorMessages();
            for (int i = 0; i < errorMessages.length; i++) {
                String errorMessage = errorMessages[i];
                if (i > 0) {
                    tagWriter.appendValue(delimiter);
                }
                tagWriter.appendValue(getDisplayString(errorMessage));
            }
            tagWriter.endTag();
        }
        BootstrapTagHelper.writeInputTagHelpBlock(tagWriter, help);
        tagWriter.endTag();
        tagWriter.endTag();
        return SKIP_BODY;
    }

    /**
     * Writes the opening '<code>label</code>' tag and forces a block tag so
     * that body content is written correctly.
     * @return {@link javax.servlet.jsp.tagext.Tag#EVAL_BODY_INCLUDE}
     */
    protected void writeLabelTagContent(TagWriter tagWriter) throws JspException {
        String labelText = this.getRequestContext().getMessage(this.label, this.label);
        tagWriter.startTag("label");
        tagWriter.writeAttribute("for", autogenerateFor());
        tagWriter.writeAttribute("class", "control-label");
        tagWriter.appendValue(labelText);
        tagWriter.endTag();
        tagWriter.forceBlock();
    }

    /**
     * Autogenerate the '<code>for</code>' attribute value for this tag.
     * <p>The default implementation delegates to {@link #getPropertyPath()},
     * deleting invalid characters (such as "[" or "]").
     */
    protected String autogenerateFor() throws JspException {
        return StringUtils.deleteAny(getPropertyPath(), "[]");
    }

    protected String autogenerateErrorId() throws JspException {
        String path = getPropertyPath();
        if ("".equals(path) || "*".equals(path)) {
            path = (String) this.pageContext.getAttribute(
                    FormTag.MODEL_ATTRIBUTE_VARIABLE_NAME, PageContext.REQUEST_SCOPE);
        }
        return StringUtils.deleteAny(path, "[]") + ".errors";
    }
}
