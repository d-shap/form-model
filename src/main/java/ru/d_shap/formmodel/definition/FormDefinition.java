package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.List;

public final class FormDefinition {

    private final String _id;

    private final List<NodeDefinition> _nodeDefinitions;

    public FormDefinition(final String id) {
        super();
        _id = id;
        _nodeDefinitions = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    public List<NodeDefinition> getNodeDefinitions() {
        return _nodeDefinitions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("FORM: {");
        result.append("id=").append(_id).append(",");
        result.append("nodes=").append(_nodeDefinitions);
        result.append("}");
        return result.toString();
    }

}
