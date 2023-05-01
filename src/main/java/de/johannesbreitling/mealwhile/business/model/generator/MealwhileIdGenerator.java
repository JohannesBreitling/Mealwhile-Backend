package de.johannesbreitling.mealwhile.business.model.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.jpa.internal.util.ConfigurationHelper;
import org.hibernate.service.spi.Configurable;

import java.io.Serializable;
import java.util.Map;

/**
 * Custom Id-Generator for Entity-Ids
 * Generates a series of characters
 */
public class MealwhileIdGenerator implements IdentifierGenerator {

    private static final int ID_LENGTH = 12;
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {

        var result = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = (int) (Math.floor(Math.random() * CHARACTERS.length()));
            char nextChar = CHARACTERS.charAt(index);

            result.append(nextChar);
        }

        return result.toString();
    }

}
