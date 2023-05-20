package de.johannesbreitling.mealwhile.business.model.responses.query;

import lombok.Data;

@Data
public class SuccessfulQueryResponse {

    private static final String update = "Successfully updated entity: ";
    private static final String create = "Successfully created entity: ";
    private static final String delete = "Successfully deleted entity: ";
    private static final String add = "Successfully added: entity #entity1 #id1 to #entity2 #id2";
    private static final String remove = "Successfully removed: entity #entity1 #id1 to #entity2 #id2";

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

    public SuccessfulQueryResponse(String nameEntity, String nameHost, String idEntity, String idHost, QueryMode mode) {
        String messageTemplate;
        switch (mode) {
            case ADD -> messageTemplate = add;
            case REMOVE -> messageTemplate = remove;

            default -> messageTemplate = "";
        }

        messageTemplate = messageTemplate.replace("#id1", idEntity);
        messageTemplate = messageTemplate.replace("#id2", idHost);
        messageTemplate = messageTemplate.replace("#entity1", nameEntity);
        messageTemplate = messageTemplate.replace("#entity2", nameHost);

        this.message = messageTemplate;
        this.id = idEntity;
    }

}
