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
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 *
 * @author Lucio Benfante <lucio@benfante.com>
 * @author michele franzin <michele at franzin.net>
 */
public class InputTag extends org.springframework.web.servlet.tags.form.InputTag {

    private String label;
    private String prefix;
    private String suffix;
    private String help;
    private String labelCssClass;

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

        writeLabelTagContent(tagWriter);
        tagWriter.startTag("div");
        String controlsContainerClasses = extractControlContainerClasses();
        tagWriter.writeAttribute("class", controlsContainerClasses);
        if (StringUtils.hasText(prefix) || StringUtils.hasText(suffix) || StringUtils.hasText(help)) {
            tagWriter.startTag("div");
            tagWriter.writeAttribute("class", "input-group");
        }
        if (StringUtils.hasText(prefix)) {
            tagWriter.startTag("span");
            tagWriter.writeAttribute("class", "input-group-addon");
            tagWriter.appendValue(prefix);
            tagWriter.endTag();
            tagWriter.forceBlock();
        }
        addCssToControl();
        super.writeTagContent(tagWriter);
        if (StringUtils.hasText(suffix)) {
            tagWriter.startTag("span");
            tagWriter.writeAttribute("class", "input-group-addon");
            tagWriter.writeAttribute("style", "border-top-right-radius: .25rem; border-bottom-right-radius: .25rem; border-left: 0;");
            tagWriter.appendValue(suffix);
            tagWriter.endTag();
            tagWriter.forceBlock();
        }

        // Help Tooltip
        if (StringUtils.hasText(help)) {
            String labelText = this.getRequestContext().getMessage(this.label, this.label);
            tagWriter.startTag("a");
            tagWriter.writeAttribute("class", "");
            tagWriter.writeAttribute("role", "button");
            tagWriter.writeAttribute("tabindex", "0");
            tagWriter.writeAttribute("style", "display: table-cell; padding: .375rem .75rem; font-size: 1rem; font-weight: normal; line-height: 1; color: #55595c; text-align: center; width: 1%; white-space: nowrap; vertical-align: middle;");
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

        if (StringUtils.hasText(prefix) || StringUtils.hasText(suffix) || StringUtils.hasText(help)) {
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
            tagWriter.writeAttribute("class", "form-control-feedback" + " " + controlsContainerClasses);
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

    /**
     * Writes the opening '<code>label</code>' tag and forces a block tag so
     * that body content is written correctly.
     */
    protected void writeLabelTagContent(TagWriter tagWriter) throws JspException {
        String labelText = this.getRequestContext().getMessage(this.label, this.label);
        tagWriter.startTag("label");
        tagWriter.writeAttribute("for", autogenerateFor());
        StringBuilder cssClass = new StringBuilder("form-control-label");
        if (StringUtils.hasText(this.labelCssClass)) {
            cssClass.append(' ').append(this.labelCssClass);
        }
        tagWriter.writeAttribute("class", cssClass.toString());
        tagWriter.appendValue(labelText);
        tagWriter.endTag();
        tagWriter.forceBlock();
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
