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
package ru.d_shap.formmodel;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Service finder.
 *
 * @author Dmitry Shapovalov
 */
public final class ServiceFinder {

    private ServiceFinder() {
        super();
    }

    /**
     * Find services with the specified interface.
     *
     * @param service the specified interface.
     * @param <T>     the generic type of the specified interface.
     *
     * @return services with the specified interface.
     */
    public static <T> List<T> find(final Class<T> service) {
        return AccessController.doPrivileged(new PrivilegedFinder<>(service));
    }

    /**
     * Privileged action to find services.
     *
     * @param <T> the generic type of the specified interface.
     *
     * @author Dmitry Shapovalov
     */
    private static final class PrivilegedFinder<T> implements PrivilegedAction<List<T>> {

        private final Class<T> _service;

        PrivilegedFinder(final Class<T> service) {
            super();
            _service = service;
        }

        @Override
        public List<T> run() {
            ServiceLoader<T> serviceLoader = ServiceLoader.load(_service);
            List<T> result = new ArrayList<>();
            Iterator<T> iterator = serviceLoader.iterator();
            while (iterator.hasNext()) {
                T service = iterator.next();
                result.add(service);
            }
            return result;
        }

    }

}
