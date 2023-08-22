package com.devas.travel.agency.infrastructure.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wizeline: gerardo.lucas
 * Description: Class to store dynamic data for SendGrid emails
 */
@EqualsAndHashCode(callSuper = true)
public class DynamicTemplatePersonalization extends Personalization {

    // Map to store data
    @JsonProperty(value = "dynamic_template_data")
    private Map<String, Object> dynamicTemplateData;

    /**
     * Method to get data stored in Map instance variable
     *
     * @return Data stored in instance variable
     */
    @JsonProperty("dynamic_template_data")
    @Override
    public Map<String, Object> getDynamicTemplateData() {
        return Objects.requireNonNullElse(dynamicTemplateData, Collections.emptyMap());
    }

    /**
     * Method to save data in Map instance variable
     *
     * @param key   Item key
     * @param value Item value
     */
    @Override
    public void addDynamicTemplateData(String key, Object value) {
        if (dynamicTemplateData == null) {
            dynamicTemplateData = new HashMap<>();
            dynamicTemplateData.put(key, value);
        } else {
            dynamicTemplateData.put(key, value);
        }
    }

    public void addDynamicTemplateData(Map<String, Object> items) {
        if (dynamicTemplateData == null) {
            dynamicTemplateData = new HashMap<>();
            dynamicTemplateData.putAll(items);
        } else {
            dynamicTemplateData.putAll(items);
        }
    }
}
