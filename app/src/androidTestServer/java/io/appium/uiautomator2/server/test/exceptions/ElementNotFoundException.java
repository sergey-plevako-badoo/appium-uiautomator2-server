/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.uiautomator2.server.test.exceptions;

import io.appium.uiautomator2.server.test.model.Finder;

/**
 * An exception thrown when the element can not be found.
 */

@SuppressWarnings("serial")
public class ElementNotFoundException extends UiAutomator2Exception {
    final static String error = "Could not find an element using supplied strategy. ";

    public ElementNotFoundException() {
        super(error);
    }

    public ElementNotFoundException(final String extra) {
        super(error + extra);
    }

    public ElementNotFoundException(Finder finder) {
        super(failMessage(finder));
    }
    protected static String failMessage(Finder finder) {
        return "Could not find any element matching " + finder;
    }

    public ElementNotFoundException(Throwable t) {
        super(t);
    }
}