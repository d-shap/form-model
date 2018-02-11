package ru.d_shap.formmodel.loader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.d_shap.formmodel.definition.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class FormDefinitionLoader {

    private FormDefinitionLoader() {
        super();
    }

    public static FormDefinition load(final InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(inputStream);
        Document document = builder.parse(inputSource);
        return createFormDefinition(document);
    }

    private static FormDefinition createFormDefinition(final Document document) {
        Element element = document.getDocumentElement();
        if (element.getNodeName().equals("form")) {
            String id = element.getAttribute("id");
            FormDefinition formDefinition = new FormDefinition(id);
            processChildNodes(element, formDefinition.getNodeDefinitions());
            return formDefinition;
        } else {
            return null;
        }
    }

    private static void processChildNodes(final Element element, final List<NodeDefinition> nodeDefinitions) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if (childElement.getNodeName().equals("element")) {
                    ElementDefinition childElementDefinition = createElementDefinition(childElement);
                    nodeDefinitions.add(childElementDefinition);
                }
                if (childElement.getNodeName().equals("form")) {
                    FormReferenceDefinition childFormReferenceDefinition = createFormReferenceDefinition(childElement);
                    nodeDefinitions.add(childFormReferenceDefinition);
                }
            }
        }
    }

    private static ElementDefinition createElementDefinition(final Element element) {
        String id = element.getAttribute("id");
        String lookup = element.getAttribute("lookup");
        String type = element.getAttribute("type");
        ElementDefinition elementDefinition = new ElementDefinition(id, lookup, ElementDefinitionType.getElementDefinitionType(type));
        processChildNodes(element, elementDefinition.getChildNodeDefinitions());
        return elementDefinition;
    }

    private static FormReferenceDefinition createFormReferenceDefinition(final Element element) {
        String refId = element.getAttribute("refid");
        return new FormReferenceDefinition(refId);
    }

}
