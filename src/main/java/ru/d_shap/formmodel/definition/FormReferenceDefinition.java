package ru.d_shap.formmodel.definition;

public final class FormReferenceDefinition extends NodeDefinition {

    private final String _refId;

    public FormReferenceDefinition(final String refId) {
        super();
        _refId = refId;
    }

    public String getRefId() {
        return _refId;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("FORM REFERENCE: {");
        result.append("refid=").append(_refId);
        result.append("}");
        return result.toString();
    }

}
