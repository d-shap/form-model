package ru.d_shap.formmodel.binding;

import ru.d_shap.formmodel.definition.ElementDefinition;

import java.util.ArrayList;
import java.util.List;

public final class Element<T extends BindingObject> {

    private final ElementDefinition _elementDefinition;

    private final T _bindingObject;

    private final List<Element<T>> _childElements;

    Element(final ElementDefinition elementDefinition, final T bindingObject) {
        super();
        _elementDefinition = elementDefinition;
        _bindingObject = bindingObject;
        _childElements = new ArrayList<>();
    }

    public ElementDefinition getElementDefinition() {
        return _elementDefinition;
    }

    public T getBindingObject() {
        return _bindingObject;
    }

    public List<Element<T>> getChildElements() {
        return _childElements;
    }

}
