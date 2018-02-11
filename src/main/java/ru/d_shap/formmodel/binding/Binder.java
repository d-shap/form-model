package ru.d_shap.formmodel.binding;

import java.util.List;

public interface Binder<T extends BindingObject> {

    void init();

    List<T> createBindingObjects(T bindingObject, String lookup);

    void dispose();

}
