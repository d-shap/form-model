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
/**
 * <p>
 * Form model library is a form definition API and a form binding API.
 * </p>
 * <p>
 * Form model represents the essential part of the GUI or any other source.
 * For example, HTML page can contain many elements, but only some of them make sence
 * for an application. Form model is a description of this essential elements. Then the source HTML
 * page is binded with this description. The result of binding is the binded elements.
 * An application can use this binded elements and do not care about how this elements were
 * obtained from the source HTML page.
 * </p>
 * <p>
 * Form model library mediates between the source and the application and encapsulates the
 * complexity of the source.
 * </p>
 * <p>
 * Form model library provides a form binding API, but does not bind the source with the form
 * description itself. The binding extension is needed to bind specific source with the form
 * description.
 * </p>
 */
package ru.d_shap.formmodel;
