package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.List;

public final class ElementDefinition extends NodeDefinition {

    private final String _id;

    private final String _lookup;

    private final ElementDefinitionType _elementDefinitionType;

    private final List<NodeDefinition> _childNodeDefinitions;

    public ElementDefinition(final String id, final String lookup, final ElementDefinitionType elementDefinitionType) {
        super();
        _id = id;
        _lookup = lookup;
        _elementDefinitionType = elementDefinitionType;
        _childNodeDefinitions = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    public String getLookup() {
        return _lookup;
    }

    public ElementDefinitionType getElementDefinitionType() {
        return _elementDefinitionType;
    }

    public List<NodeDefinition> getChildNodeDefinitions() {
        return _childNodeDefinitions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ELEMENT: {");
        if (_id != null) {
            result.append("id=").append(_id).append(",");
        }
        result.append("lookup=").append(_lookup).append(",");
        result.append("type=").append(_elementDefinitionType);
        if (!_childNodeDefinitions.isEmpty()) {
            result.append(",nodes=").append(_childNodeDefinitions);
        }
        result.append("}");
        return result.toString();
    }

}
