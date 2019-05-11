package hse.se.aaizmaylov.petrinetscalculationserver.data;

public enum RequestType {
    REDUCTION, ANALYSIS;

    public static RequestType fromString(String string) {
        switch (string) {
            case "reduction":
                return REDUCTION;
            case "analysis":
                return ANALYSIS;

            default:
                return null;
        }
    }
}
