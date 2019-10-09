///////////////////////////////////////////////////////////////////////////////////////////////////
// Form model library is a form definition API and a form binding API.
// Copyright (C) 2018 Dmitry Shapovalov.
//
// This file is part of form model library.
//
// Form model library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Form model library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.formmodel.utils;

/**
 * Helper class to check empty strings.
 *
 * @author Dmitry Shapovalov
 */
public final class EmptyStringHelper {

    private EmptyStringHelper() {
        super();
    }

    /**
     * Check if the string is empty.
     *
     * @param str the string to check.
     *
     * @return true if the string is empty.
     */
    public static boolean isEmpty(final String str) {
        if (str == null) {
            return true;
        } else {
            return "".equals(str);
        }
    }

    /**
     * Check if the string is blank (empty or contains only whitespaces).
     *
     * @param str the string to check.
     *
     * @return true if the string is blank.
     */
    public static boolean isBlank(final String str) {
        if (isEmpty(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
