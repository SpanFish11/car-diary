export default class Filter {
  constructor(
    page,
    pageSize,
    modelId,
    filterOn,
    search,
    specificYear,
    from,
    until
  ) {
    this.page = page;
    this.pageSize = pageSize;
    this.modelId = modelId;
    this.filterOn = filterOn;
    this.search = search;
    this.specificYear = specificYear;
    this.from = from;
    this.until = until;
  }

  setUp() {
    let params = { params: {} };
    this.page ? (params.params["page"] = this.page - 1) : undefined;
    this.pageSize ? (params.params["size"] = this.pageSize) : undefined;
    this.modelId ? (params.params["modelId"] = this.modelId) : undefined;
    this.filterOn === "VIN" ? (params.params["vin"] = this.search) : undefined;
    this.filterOn === "Last Name"
      ? (params.params["lastname"] = this.search)
      : undefined;
    this.specificYear
      ? (params.params["specificYear"] = this.specificYear)
      : undefined;
    this.from ? (params.params["from"] = this.from) : undefined;
    this.until ? (params.params["until"] = this.until) : undefined;
    return params;
  }
}
