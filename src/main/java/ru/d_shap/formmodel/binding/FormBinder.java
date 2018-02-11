package ru.d_shap.formmodel.binding;

import java.util.ArrayList;
import java.util.List;

import ru.d_shap.formmodel.definition.ElementDefinition;
import ru.d_shap.formmodel.definition.FormDefinition;
import ru.d_shap.formmodel.definition.FormDefinitions;
import ru.d_shap.formmodel.definition.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.NodeDefinition;

public final class FormBinder {

    private FormBinder() {
        super();
    }

    public static <T extends BindingObject> Form<T> bind(final FormDefinitions formDefinitions, final String formId, final Binder<T> binder) {
        binder.init();

        Form<T> form = new Form<>();
        List<Element<T>> objects = createFormDefinitionBindings(formDefinitions, formId, binder);
        form.getElements().addAll(objects);

        binder.dispose();

        return form;
    }

    private static <T extends BindingObject> List<Element<T>> createFormDefinitionBindings(final FormDefinitions formDefinitions, final String formId, final Binder<T> binder) {
        FormDefinition formDefinition = formDefinitions.getFormDefinitions().get(formId);
        List<NodeDefinition> nodeDefinitions = formDefinition.getNodeDefinitions();
        return createNodeDefinitionBindings(formDefinitions, null, nodeDefinitions, binder);
    }

    private static <T extends BindingObject> List<Element<T>> createNodeDefinitionBindings(final FormDefinitions formDefinitions, final T parent, final List<NodeDefinition> nodeDefinitions, final Binder<T> binder) {
        List<Element<T>> elements = new ArrayList<>();
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                List<Element<T>> objects = createElementDefinitionBindings(formDefinitions, parent, (ElementDefinition) nodeDefinition, binder);
                elements.addAll(objects);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                List<Element<T>> objects = createFormReferenceDefinitionBindings(formDefinitions, parent, (FormReferenceDefinition) nodeDefinition, binder);
                elements.addAll(objects);
            }
        }
        return elements;
    }

    private static <T extends BindingObject> List<Element<T>> createElementDefinitionBindings(final FormDefinitions formDefinitions, final T parent, final ElementDefinition elementDefinition, final Binder<T> binder) {
        String lookup = elementDefinition.getLookup();
        List<T> bindingObjects = binder.createBindingObjects(parent, lookup);
        List<Element<T>> elements = new ArrayList<>();
        for (T bindingObject : bindingObjects) {
            Element<T> element = new Element<>(elementDefinition, bindingObject);
            elements.add(element);
        }
        for (Element<T> element : elements) {
            List<Element<T>> childElements = createNodeDefinitionBindings(formDefinitions, element.getBindingObject(), elementDefinition.getChildNodeDefinitions(), binder);
            element.getChildElements().addAll(childElements);
        }
        return elements;
    }

    private static <T extends BindingObject> List<Element<T>> createFormReferenceDefinitionBindings(final FormDefinitions formDefinitions, final T parent, final FormReferenceDefinition formReferenceDefinition, final Binder<T> binder) {
        String formId = formReferenceDefinition.getRefId();
        FormDefinition formDefinition = formDefinitions.getFormDefinitions().get(formId);
        List<NodeDefinition> nodeDefinitions = formDefinition.getNodeDefinitions();
        return createNodeDefinitionBindings(formDefinitions, parent, nodeDefinitions, binder);
    }

}
