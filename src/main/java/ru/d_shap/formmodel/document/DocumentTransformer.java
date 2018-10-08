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
package ru.d_shap.formmodel.document;

import java.io.IOException;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import ru.d_shap.formmodel.OutputResultWriteException;

/**
 * Document transformation helper class.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentTransformer {

    private DocumentTransformer() {
        super();
    }

    /**
     * Write the XML node to the specified writer.
     *
     * @param node   the XML node.
     * @param writer the specified writer
     * @param indent true to add additional whitespaces.
     */
    public static void writeTo(final Node node, final Writer writer, final boolean indent) {
        try {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                if (indent) {
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                }
                transformer.transform(new DOMSource(node), new StreamResult(writer));
            } finally {
                writer.close();
            }
        } catch (IOException | TransformerException ex) {
            throw new OutputResultWriteException(ex);
        }
    }

}
