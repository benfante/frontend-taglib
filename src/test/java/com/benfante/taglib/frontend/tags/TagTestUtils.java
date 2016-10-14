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

import java.io.UnsupportedEncodingException;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;

/**
 * Utility for writing tag tests.
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class TagTestUtils {

    private TagTestUtils() {
    }

    public static MockServletContext createServletContext() {
        MockServletContext mockServletContext = new MockServletContext();
        String configLocations = "/WEB-INF/applicationContext-mock.xml";
        mockServletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocations);
        ContextLoader loader = new ContextLoader();
        loader.initWebApplicationContext(mockServletContext);
        return mockServletContext;
    }

    public static MockPageContext createPageContext(ServletContext servletContext) {
        return new MockPageContext(servletContext);
    }

    public static String getOutput(MockPageContext pageContext) throws UnsupportedEncodingException {
        return ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString();
    }

}
