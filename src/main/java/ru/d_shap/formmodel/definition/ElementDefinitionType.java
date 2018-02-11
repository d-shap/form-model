package ru.d_shap.formmodel.definition;

public enum ElementDefinitionType {

    MANDATORY("mandatory"),

    MANDATORY_MULTIPLE("mandatory multiple"),

    OPTIONAL("optional"),

    OPTIONAL_MULTIPLE("optional multiple"),

    FORBIDDEN("forbidden");

    private final String _attrValue;

    ElementDefinitionType(final String attrValue) {
        _attrValue = attrValue;
    }

    public static ElementDefinitionType getElementDefinitionType(final String attrValue) {
        for (ElementDefinitionType elementDefinitionType : values()) {
            if (elementDefinitionType._attrValue.equals(attrValue)) {
                return elementDefinitionType;
            }
        }
        return null;
    }

}
