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
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.tags.form.AbstractFormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * A tag for writing data in page just like a read-only form.
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class LabelledOutputTag extends AbstractFormTag {

    /**
     * The {@link TagWriter} instance being used.
     * <p>
     * Stored so we can close the tag on {@link #doEndTag()}.
     */
    private TagWriter tagWriter;

    private String label;
    private String labelCssClass;
    private String cssClass;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelCssClass() {
        return labelCssClass;
    }

    public void setLabelCssClass(String labelCssClass) {
        this.labelCssClass = labelCssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "row");

        writeLabelTagContent(tagWriter);
        tagWriter.startTag("output");
        if (StringUtils.isNotBlank(this.getCssClass())) {
            tagWriter.writeAttribute("class", this.getCssClass());
        }
        tagWriter.forceBlock();
        this.tagWriter = tagWriter;
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        this.tagWriter.endTag();
        this.tagWriter.endTag();
        return EVAL_PAGE;
    }

    /**
     * Writes the opening '<code>label</code>' tag and forces a block tag so
     * that body content is written correctly.
     */
    protected void writeLabelTagContent(TagWriter tagWriter) throws JspException {
        String labelText = this.getRequestContext().getMessage(this.label, this.label);
        tagWriter.startTag("label");
        tagWriter.writeAttribute("class",
                "font-weight-bold "
                + (StringUtils.isNotBlank(this.getLabelCssClass()) ? this.getLabelCssClass() : ""));
        tagWriter.appendValue(labelText);
        tagWriter.endTag();
    }

}