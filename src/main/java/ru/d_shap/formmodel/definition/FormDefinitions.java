package ru.d_shap.formmodel.definition;

import java.util.HashMap;
import java.util.Map;

public final class FormDefinitions {

    private final Map<String, FormDefinition> _formDefinitions;

    public FormDefinitions() {
        super();
        _formDefinitions = new HashMap<>();
    }

    public Map<String, FormDefinition> getFormDefinitions() {
        return _formDefinitions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("FORMS: {");
        result.append("forms=").append(_formDefinitions);
        result.append("}");
        return result.toString();
    }

}
