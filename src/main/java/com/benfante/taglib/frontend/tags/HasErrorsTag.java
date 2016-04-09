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
import org.springframework.web.servlet.tags.form.AbstractDataBoundFormElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * A conditional tag for checking if a field or a bean produced binding and/or validation errors.
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class HasErrorsTag extends AbstractDataBoundFormElementTag {

    @Override
    protected int writeTagContent(TagWriter writer) throws JspException {
        if (getBindStatus().isError()) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
        
    }
    
}
