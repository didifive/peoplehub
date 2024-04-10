package it.zancanela.peoplehub.utils;

public class ValidationMessagesAndOpenApiConstantsUtils {

    private ValidationMessagesAndOpenApiConstantsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NOT_BE_NULL_EMPTY_OR_BLANK = "Not be null, empty or blank";
    public static final String NOT_BE_NULL = "Not be null";
    public static final String MINIMO_1_MAXIMO_2 = "Minimum 1 character and maximum 2 characters allowed";
    public static final String MINIMO_2_MAXIMO_9 = "Minimum 5 character and maximum 9 characters allowed";
    public static final String STRING = "string";
    public static final String DATE = "date";
    public static final String DATE_EXAMPLE = "2024-04-01";
    public static final String UUID_ID_EXAMPLE = "cbecf0d2-e9d2-445b-8b69-af731d0ab2a6";
    public static final String PERSON_NAME_EXAMPLE = "Luis Zancanela";

    public static final String ADDRESS_PUBLIC_PLACE_EXAMPLE = "Rua 01";
    public static final String ADDRESS_NUMBER_EXAMPLE = "254";
    public static final String ADDRESS_CITY_EXAMPLE = "São Paulo";
    public static final String ADDRESS_STATE_EXAMPLE = "SP";
    public static final String ADDRESS_ZIP_CODE_EXAMPLE = "01214-489";

}
