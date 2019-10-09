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

import java.util.List;

/**
 * Helper class to process object identities.
 *
 * @author Dmitry Shapovalov
 */
public final class IdentityHelper {

    private IdentityHelper() {
        super();
    }

    /**
     * Check if the list contains the specified item. Use item identity, NOT equals.
     *
     * @param list the list.
     * @param item the specified item.
     * @param <T>  the generic type of the list items.
     *
     * @return true if the list contains the specified item.
     */
    public static <T> boolean contains(final List<T> list, final T item) {
        for (T element : list) {
            if (element == item) {
                return true;
            }
        }
        return false;
    }

}
