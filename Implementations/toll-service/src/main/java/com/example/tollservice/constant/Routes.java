package com.example.tollservice.constant;

public final class Routes {

    private Routes() {
        // Private constructor to prevent instantiation
    }

    public static final String BASE_API = "/api/v1/toll";

    // TollPlaza Routes
    public static final String CREATE_TOLL_PLAZA = "/plazas"; // POST
    public static final String ADD_TOLL_BOOTH = "/plazas/{tollPlazaId}/booths"; // POST
    public static final String GET_TOLLBOOTHS_BY_PLAZA = "/plazas/{tollPlazaId}/booths"; // GET
    public static final String GET_TOLLPLAZA_BY_ID = "/plazas/{tollPlazaId}"; // GET

    // Analytics Routes
    public static final String TOTAL_COLLECTION_FOR_PLAZA = "/analytics/collection"; // GET
    public static final String TOTAL_VEHICLES_FOR_PLAZA = "/analytics/vehicles"; // GET
    public static final String TOTAL_PASSES_ISSUED_FOR_PLAZA = "/analytics/passes"; // GET

    // Pass Routes
    public static final String ISSUE_PASS = "/passes/issue"; // POST
    public static final String VALIDATE_PASS = "/passes/validate"; // GET
    public static final String GET_PASS_BY_ID = "/passes/{passId}"; // GET

    // Transaction Routes
    public static final String RECORD_TRANSACTION = "/transactions"; // POST

    // Vehicle Routes
    public static final String REGISTER_VEHICLE = "/vehicles"; // POST
    public static final String GET_VEHICLE_BY_ID = "/vehicles/{vehicleId}"; // GET
}

