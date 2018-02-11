package ru.d_shap.formmodel.binding;

import java.util.ArrayList;
import java.util.List;

public final class Form<T extends BindingObject> {

    private final List<Element<T>> _elements;

    Form() {
        super();
        _elements = new ArrayList<>();
    }

    public List<Element<T>> getElements() {
        return _elements;
    }

}
