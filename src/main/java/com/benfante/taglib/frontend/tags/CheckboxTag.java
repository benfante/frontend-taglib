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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.tags.form.AbstractSingleCheckedElementTag;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class CheckboxTag extends AbstractSingleCheckedElementTag {

    private String help;
    private String labelCssClass;

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getLabelCssClass() {
        return labelCssClass;
    }

    public void setLabelCssClass(String labelCssClass) {
        this.labelCssClass = labelCssClass;
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        tagWriter.startTag("div");
        if (this.getBindStatus().isError()) {
            tagWriter.writeAttribute("class", "form-group has-danger");
        } else {
            tagWriter.writeAttribute("class", "form-group");
        }
        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "row");

        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "checkbox " + this.getCssClass());
        String labelText = null;
        try {
            labelText = this.getRequestContext().getMessage(getLabel().toString());
        } catch (NoSuchMessageException nsme) {
            Object resolvedLabel = evaluate("label", getLabel());
            if (resolvedLabel != null) {
                labelText = convertToDisplayString(resolvedLabel);
            }
        }

        String id = resolveId();
        tagWriter.startTag("label");
        tagWriter.writeAttribute("for", id);
        tagWriter.writeOptionalAttributeValue("class", this.getLabelCssClass());

        if (!isDisabled()) {
            // Write out the 'field was present' marker.
            tagWriter.startTag("input");
            tagWriter.writeAttribute("type", "hidden");
            tagWriter.writeAttribute("name", WebDataBinder.DEFAULT_FIELD_MARKER_PREFIX + getName());
            tagWriter.writeAttribute("value", "on");
            tagWriter.endTag();
        }

        tagWriter.startTag("input");
        writeOptionalAttribute(tagWriter, "id", id);
        writeOptionalAttribute(tagWriter, "name", getName());
        writeTagDetails(tagWriter);
        tagWriter.endTag();

        tagWriter.appendValue(labelText);
        tagWriter.endTag();
        tagWriter.forceBlock();

        // Help Tooltip
        if (StringUtils.hasText(help)) {
            tagWriter.startTag("a");
            tagWriter.writeAttribute("class", "");
            tagWriter.writeAttribute("role", "button");
            tagWriter.writeAttribute("tabindex", "0");
            tagWriter.writeAttribute("style", "display: inline; padding: .375rem .75rem; font-size: 1rem; font-weight: normal; line-height: 1; color: #55595c; text-align: center; width: 1%; white-space: nowrap; vertical-align: middle;");
            writeOptionalAttribute(tagWriter, "data-toggle", "popover");
            writeOptionalAttribute(tagWriter, "data-trigger", "focus");
            writeOptionalAttribute(tagWriter, "data-html", "true");
            writeOptionalAttribute(tagWriter, "title", labelText);
            writeOptionalAttribute(tagWriter, "data-content", this.getRequestContext().getMessage(this.getHelp(), this.getHelp()));
            tagWriter.startTag("i");
            tagWriter.writeAttribute("class", "fa fa-question-circle");
            tagWriter.endTag(true);
            tagWriter.endTag();
        }

        tagWriter.endTag();
        tagWriter.endTag();

        // Validation messages
        if (this.getBindStatus().isError()) {
            tagWriter.startTag("div");
            tagWriter.writeAttribute("class", "row");

            tagWriter.startTag("div");
            writeOptionalAttribute(tagWriter, "class", labelCssClass);
            tagWriter.endTag(true);

            tagWriter.startTag("div");
            tagWriter.writeAttribute("id", autogenerateErrorId());
            tagWriter.writeAttribute("class", "text-help" + " " + extractControlContainerClasses());
            String[] errorMessages = getBindStatus().getErrorMessages();
            if (errorMessages.length > 0) {
                if (errorMessages.length > 1) {
                    tagWriter.startTag("ul");
                    for (int i = 0; i < errorMessages.length; i++) {
                        String errorMessage = errorMessages[i];
                        tagWriter.startTag("li");
                        tagWriter.appendValue(getDisplayString(errorMessage));
                        tagWriter.endTag();
                    }
                    tagWriter.endTag();
                } else {
                    tagWriter.appendValue(getDisplayString(errorMessages[0]));
                }
            }
            tagWriter.endTag();
            tagWriter.endTag();
        }

        tagWriter.endTag();
        return SKIP_BODY;
    }

    @Override
    protected void writeTagDetails(TagWriter tagWriter) throws JspException {
        tagWriter.writeAttribute("type", "checkbox");

        Object boundValue = getBoundValue();
        Class valueType = getBindStatus().getValueType();

        if (Boolean.class.equals(valueType) || boolean.class.equals(valueType)) {
            // the concrete type may not be a Boolean - can be String
            if (boundValue instanceof String) {
                boundValue = Boolean.valueOf((String) boundValue);
            }
            Boolean booleanValue = (boundValue != null ? (Boolean) boundValue : Boolean.FALSE);
            renderFromBoolean(booleanValue, tagWriter);
        } else {
            Object value = getValue();
            if (value == null) {
                throw new IllegalArgumentException("Attribute 'value' is required when binding to non-boolean values");
            }
            Object resolvedValue = (value instanceof String ? evaluate("value", (String) value) : value);
            renderFromValue(resolvedValue, tagWriter);
        }
    }

    /**
     * Autogenerate the '<code>for</code>' attribute value for this tag.
     * <p>
     * The default implementation delegates to {@link #getPropertyPath()},
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

    private String extractControlContainerClasses() {
        StringBuilder result = new StringBuilder();
        StringBuilder newCssClasses = new StringBuilder();
        String[] cssClasses = this.getCssClass().split(" ");
        for (String cssClass : cssClasses) {
            if (cssClass.startsWith("col-")) {
                result.append(' ').append(cssClass);
            } else {
                newCssClasses.append(' ').append(cssClass);
            }
            this.setCssClass(cssClass);
        }
        this.setCssClass(newCssClasses.toString());
        return result.toString();
    }

    private void addCssToControl() {
        this.setCssClass(this.getCssClass().concat(" form-control"));
    }

}
