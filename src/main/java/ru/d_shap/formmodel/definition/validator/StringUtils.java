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
package ru.d_shap.formmodel.definition.validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Helper class to check string values.
 *
 * @author Dmitry Shapovalov
 */
final class StringUtils {

    private static final Set<Character> VALID_START_CHARACTERS;

    private static final Set<Character> VALID_CHARACTERS;

    static {
        VALID_START_CHARACTERS = new HashSet<>();
        for (char ch = 'a'; ch < 'z' + 1; ch++) {
            VALID_START_CHARACTERS.add(ch);
        }
        for (char ch = 'A'; ch < 'Z' + 1; ch++) {
            VALID_START_CHARACTERS.add(ch);
        }
        VALID_START_CHARACTERS.add('_');

        VALID_CHARACTERS = new HashSet<>(VALID_START_CHARACTERS);
        for (char ch = '0'; ch < '9' + 1; ch++) {
            VALID_CHARACTERS.add(ch);
        }
        VALID_CHARACTERS.add('-');
    }

    private StringUtils() {
        super();
    }

    static boolean isEmpty(final String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str);
    }

    static boolean hasValidCharacters(final String str) {
        if (!VALID_START_CHARACTERS.contains(str.charAt(0))) {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            if (!VALID_CHARACTERS.contains(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
