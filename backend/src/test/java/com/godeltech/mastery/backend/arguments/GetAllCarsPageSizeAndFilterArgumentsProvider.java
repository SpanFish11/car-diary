package com.godeltech.mastery.backend.arguments;

import com.godeltech.mastery.backend.domain.dto.request.Filter;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class GetAllCarsPageSizeAndFilterArgumentsProvider implements ArgumentsProvider {

  private static final String PAGE_ONE = "allCarsPageOne.json";
  private static final String PAGE_TWO = "allCarsPageTwo.json";
  private static final String PAGE_SIZE_TEN = "allCarsPageSizeTen.json";
  private static final String FILTER_MODEL_ID = "allCarsFilterModel.json";
  private static final String FILTER_VIN = "allCarsFilterVin.json";
  private static final String FILTER_LAST_NAME = "allCarsFilterLastname.json";
  private static final String FILTER_SPECIFIC_YEAR = "allCarsFilterSpecificYear.json";
  private static final String FILTER_FROM = "allCarsFilterFrom.json";
  private static final String FILTER_UNTIL = "allCarsFilterUntil.json";
  private static final String FILTER_PERIOD = "allCarsFilterPeriod.json";

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
        Arguments.of(PAGE_ONE, 0, 5, new Filter(null, null, null, null, null, null)),
        Arguments.of(PAGE_TWO, 1, 5, new Filter(null, null, null, null, null, null)),
        Arguments.of(PAGE_SIZE_TEN, 0, 10, new Filter(null, null, null, null, null, null)),
        Arguments.of(FILTER_MODEL_ID, 0, 5, new Filter(2L, null, null, null, null, null)),
        Arguments.of(
            FILTER_VIN, 0, 5, new Filter(null, "4S3BMHB68B3286050", null, null, null, null)),
        Arguments.of(FILTER_LAST_NAME, 0, 5, new Filter(null, null, "Stephens", null, null, null)),
        Arguments.of(FILTER_SPECIFIC_YEAR, 0, 5, new Filter(null, null, null, 2021, null, null)),
        Arguments.of(FILTER_FROM, 0, 5, new Filter(null, null, null, null, null, 2019)),
        Arguments.of(FILTER_UNTIL, 0, 5, new Filter(null, null, null, null, 2018, null)),
        Arguments.of(FILTER_PERIOD, 0, 5, new Filter(null, null, null, null, 2020, 2018)));
  }
}
