package de.johannesbreitling.mealwhile.business.model.responses.query;

import lombok.Data;

@Data
public class SuccessfulQueryResponse {

    private static final String update = "Successfully updated entity: ";
    private static final String create = "Successfully created entity: ";
    private static final String delete = "Successfully deleted entity: ";

    private final String id;
    private final String message;

    public SuccessfulQueryResponse(String name, String id, QueryMode mode) {

        this.id = id;

        String prefix;
        switch (mode) {
            case CREATE -> prefix = create;
            case UPDATE -> prefix = update;
            case DELETE -> prefix = delete;
            default -> prefix = "";
        }

        String idPart = " [#" + id + "]";

        this.message = prefix + name + idPart;
    }
}
