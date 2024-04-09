package it.zancanela.peoplehub.enums;

public enum AddressType {

    COMMERCIAL("Comercial"),
    HOME("Residencial"),
    MAILING("Correspondência");

    private final String description;

    AddressType(String description) {
        this.description = description;
    }
}
