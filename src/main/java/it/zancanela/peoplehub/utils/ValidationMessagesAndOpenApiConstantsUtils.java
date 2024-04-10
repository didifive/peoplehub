package it.zancanela.peoplehub.utils;

public class ValidationMessagesAndOpenApiConstantsUtils {

    private ValidationMessagesAndOpenApiConstantsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NOT_BE_NULL_EMPTY_OR_BLANK = "Not be null, empty or blank";
    public static final String NOT_BE_NULL_OR_EMPTY = "Not be null, empty or blank";
    public static final String NOT_BE_NULL = "Not be null";
    public static final String MINIMO_1_MAXIMO_2 = "Minimum 1 character and maximum 2 characters allowed";
    public static final String MINIMO_2_MAXIMO_9 = "Minimum 5 character and maximum 9 characters allowed";
    public static final String STRING = "string";
    public static final String INTEGER = "integer";
    public static final String DATE = "date";
    public static final String DATE_EXAMPLE = "2024-04-01";
    public static final String UUID_ID_EXAMPLE = "cbecf0d2-e9d2-445b-8b69-af731d0ab2a6";
    public static final String PERSON_NAME_EXAMPLE = "Luis Zancanela";

    public static final String ADDRESS_PUBLIC_PLACE_EXAMPLE = "Rua 01";
    public static final String ADDRESS_NUMBER_EXAMPLE = "254";
    public static final String ADDRESS_CITY_EXAMPLE = "São Paulo";
    public static final String ADDRESS_STATE_EXAMPLE = "SP";
    public static final String ADDRESS_ZIP_CODE_EXAMPLE = "01214-489";

    public static final String CREATE_201_DESCRIPTION = "Objeto foi salvo com sucesso";
    public static final String CREATE_400_DESCRIPTION = "Campo requerido vazio ou inválido, verificar mensagem de retorno";
    public static final String CREATE_409_DESCRIPTION = "Violação de integridade de dados, verificar mensagem de retorno";
    public static final String PAGEABLE_PARAMETER_SIZE_DESCRIPTION = "Quantidade de objetos por página";
    public static final String PAGEABLE_PARAMETER_SIZE_EXAMPLE = "10";
    public static final String PAGEABLE_PARAMETER_PAGE_DESCRIPTION = "Número da página";
    public static final String PAGEABLE_PARAMETER_PAGE_EXAMPLE= "0";
    public static final String PAGEABLE_SORT_DESCRIPTION = "Nome do campo que deseja ordenar. "
            + "Seguido da direção de ordenação desejada \',asc|desc\' (ascendente/descendente). "
            + "A direção de ordenação padrão é ascendent. "
            + "Aceita ordenação múltipla. ";
    public static final String PAGEABLE_SORT_EXAMPLE = "name,desc";
    public static final String FIND_ALL_200_DESCRIPTION = "Retorna lista paginada";

    public static final String FIND_BY_ID_PARAMETER_ID_DESCRIPTION = "Id do objeto";
    public static final String FIND_BY_ID_200_DESCRIPTION = "Retorna detalhe da consulta";
    public static final String FIND_BY_ID_404_DESCRIPTION = "Objeto não encontrado";

}
