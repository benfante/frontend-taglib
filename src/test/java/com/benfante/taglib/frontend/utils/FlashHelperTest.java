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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfante.taglib.frontend.utils;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class FlashHelperTest {
    
    public FlashHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setError method, of class FlashHelper.
     */
    @Test
    public void testSetError() {
        HttpServletRequest req = new MockHttpServletRequest();
        String code = "error.code";
        FlashHelper.setError(req, code);
        assertThatValueInRequestEqualToCode(req, code, FlashHelper.DEFAULT_ERROR_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
    }

    private void assertThatValueInRequestEqualToCode(HttpServletRequest req, String code, String type, String attribute) {
        @SuppressWarnings("unchecked")
        Map<String, String> flashMap = (Map<String, String>) req.getAttribute(attribute);
        String value = flashMap.get(type);
        assertThat(value, equalTo(code));
    }

    /**
     * Test of setNotice method, of class FlashHelper.
     */
    @Test
    public void testSetNotice() {
        HttpServletRequest req = new MockHttpServletRequest();
        String code = "notice.code";
        FlashHelper.setNotice(req, code);
        assertThatValueInRequestEqualToCode(req, code, FlashHelper.DEFAULT_NOTICE_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
    }

    /**
     * Test of doSet method, of class FlashHelper.
     */
    @Test
    public void testDoSet() {
        HttpServletRequest req = new MockHttpServletRequest();
        String code = "error.code";
        FlashHelper.doSet(req, code, FlashHelper.DEFAULT_ERROR_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
        assertThatValueInRequestEqualToCode(req, code, FlashHelper.DEFAULT_ERROR_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
    }

    /**
     * Test of setRedirectError method, of class FlashHelper.
     */
    @Test
    public void testSetRedirectError() {
        HttpServletRequest req = new MockHttpServletRequest();
        String code = "error.code";
        FlashHelper.setRedirectError(req, code);
        assertThatValueInSessionEqualToCode(req, code, FlashHelper.DEFAULT_ERROR_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
    }

    private void assertThatValueInSessionEqualToCode(HttpServletRequest req, String code, String type, String attribute) {
        @SuppressWarnings("unchecked")
        Map<String, String> flashMap = (Map<String, String>) req.getSession().getAttribute(attribute);
        String value = flashMap.get(type);
        assertThat(value, equalTo(code));
    }
    
    /**
     * Test of setRedirectNotice method, of class FlashHelper.
     */
    @Test
    public void testSetRedirectNotice() {
        HttpServletRequest req = new MockHttpServletRequest();
        String code = "notice.code";
        FlashHelper.setRedirectNotice(req, code);
        assertThatValueInSessionEqualToCode(req, code, FlashHelper.DEFAULT_NOTICE_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
    }

    /**
     * Test of doSetRedirect method, of class FlashHelper.
     */
    @Test
    public void testDoSetRedirect() {
        HttpServletRequest req = new MockHttpServletRequest();
        String code = "error.code";
        FlashHelper.doSetRedirect(req, code, FlashHelper.DEFAULT_ERROR_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
        assertThatValueInSessionEqualToCode(req, code, FlashHelper.DEFAULT_ERROR_TYPE, FlashHelper.DEFAULT_FLASH_ATTRIBUTE);
    }
    
}
