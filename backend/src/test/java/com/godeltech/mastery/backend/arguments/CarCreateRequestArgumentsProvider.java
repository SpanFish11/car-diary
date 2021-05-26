package com.godeltech.mastery.backend.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class CarCreateRequestArgumentsProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    return Stream.of(
        Arguments.of(
            "Model is mandatory", new String[]{"modelId", "null", "year", "2020",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be greater then or equal to 1900",
            new String[]{"modelId", "1", "year", "1899",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be less then or equal to 2021",
            new String[]{"modelId", "1", "year", "2210",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Year is mandatory",
            new String[]{"modelId", "1", "year", "null",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "VIN code is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be 17 characters long",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3R0CK68", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be 17 characters long",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BILL68B328RGF0TR56", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Mileage is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "null", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be greater then or equal to 0",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "-1", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be less then or equal to 1000000",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "1000001", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Price is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "null", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Price is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "null", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "numeric value out of bounds (<10 digits>.<2 digits> expected)",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0.000", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "numeric value out of bounds (<10 digits>.<2 digits> expected)",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "99999999999.999",
                "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Equipment is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "null",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be greater then or equal to 1",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "0",
                "clientId", "1", "ours", "true", "used", "false"}),
        Arguments.of(
            "Value should be greater then or equal to 1",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "0", "ours", "true", "used", "false"}),
        Arguments.of(
            "Client is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "null", "ours", "true", "used", "false"}),
        Arguments.of(
            "Ours is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "null", "used", "false"}),
        Arguments.of(
            "Used is mandatory",
            new String[]{"modelId", "1", "year", "1990",
                "vin", "4S3BMH468B3286050", "mileage", "0", "price", "0", "equipmentId", "1",
                "clientId", "1", "ours", "true", "used", "null"})
    );
  }
}
