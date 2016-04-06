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
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 *
 * @author michele franzin <michele at franzin.net>
 */
public class BootstrapTagHelper {

    /**
     * Writes the opening '
     * <code>span</code>' tag and forces a block tag so that field prefix/suffix
     * is written correctly.
     */
    public static void writeInputTagDecorator(final TagWriter tagWriter, final String text) throws JspException {
        if (StringUtils.hasText(text)) {
            tagWriter.startTag("span");
            tagWriter.writeAttribute("class", "add-on");
            tagWriter.appendValue(text);
            tagWriter.endTag();
            tagWriter.forceBlock();
        }
    }

    /**
     * compute css classe for input in case of prefix & suffix
     *
     * @param prefix
     * @param suffix
     * @return
     */
    public static String prependAppendCssClasses(final String prefix, final String suffix) {
        return (StringUtils.hasText(prefix) ? "input-prepend" : "") + " "
                + (StringUtils.hasText(suffix) ? "input-append" : "");
    }

    /**
     * Writes the opening '
     * <code>p</code>' tag and forces a block tag so that field prefix/suffix is
     * written correctly.
     */
    public static void writeInputTagHelpBlock(final TagWriter tagWriter, final String text) throws JspException {
        if (StringUtils.hasText(text)) {
            tagWriter.startTag("p");
            tagWriter.writeAttribute("class", "help-block");
            tagWriter.appendValue(text);
            tagWriter.endTag();
            tagWriter.forceBlock();
        }
    }
}
