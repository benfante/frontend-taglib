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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.web.util.TagUtils;

public class LabelledOutputTagTest {

    private static MockServletContext mockServletContext;
    private MockPageContext mockPageContext;
    private LabelledOutputTag tag;

    @BeforeClass
    public static void init() {
        mockServletContext = TagTestUtils.createServletContext();
    }

    @Before
    public void setup() {
        mockPageContext = TagTestUtils.createPageContext(mockServletContext);
        tag = new LabelledOutputTag();
        tag.setPageContext(mockPageContext);
    }

    @Test
    public void testStartTagOutput() throws Exception {
        try {
            tag.setLabel("Username");
            tag.doStartTag();
            String output = TagTestUtils.getOutput(mockPageContext);
            final String expected = "<div class=\"row\"><label class=\"font-weight-bold\">Username</label><output>";
            assertThat(output, equalTo(expected));
        } catch (JspException je) {
            Assert.fail(je.getLocalizedMessage());
        }
    }
    
    @Test
    public void testEndTagOutput() throws Exception {
        try {
            tag.setLabel("Username");
            tag.doStartTag();
            tag.doAfterBody();
            tag.doEndTag();
            String output = TagTestUtils.getOutput(mockPageContext);
            final String expected = "</output></div>";
            assertThat(output, endsWith(expected));
        } catch (JspException je) {
            Assert.fail(je.getLocalizedMessage());
        }
    }
}
