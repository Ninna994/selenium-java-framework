package custom_framework.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryLocation {
    private double latitude;
    private double longitude;

    // Static instances for each country
    public static final CountryLocation CANADA = new CountryLocation(56.1304, -106.3468);
    public static final CountryLocation UNITED_STATES = new CountryLocation(37.0902, -95.7129);
    public static final CountryLocation UNITED_KINGDOM = new CountryLocation(55.3781, -3.4360);
    public static final CountryLocation AUSTRALIA = new CountryLocation(-25.2744, 133.7751);
    public static final CountryLocation GERMANY = new CountryLocation(51.1657, 10.4515);
    public static final CountryLocation FRANCE = new CountryLocation(46.6034, 1.8883);
    public static final CountryLocation JAPAN = new CountryLocation(36.2048, 138.2529);
    public static final CountryLocation CHINA = new CountryLocation(35.8617, 104.1954);
    public static final CountryLocation INDIA = new CountryLocation(20.5937, 78.9629);
    public static final CountryLocation BRAZIL = new CountryLocation(-14.2350, -51.9253);
    public static final CountryLocation SOUTH_AFRICA = new CountryLocation(-30.5595, 22.9375);
    public static final CountryLocation RUSSIA = new CountryLocation(61.5240, 105.3188);
    public static final CountryLocation ITALY = new CountryLocation(41.8719, 12.5674);
    public static final CountryLocation MEXICO = new CountryLocation(23.6345, -102.5528);
    public static final CountryLocation SPAIN = new CountryLocation(40.4637, -3.7492);

    public static double getLatitude(String country) {
        return getCountryLocation(country).getLatitude();
    }

    public static double getLongitude(String country) {
        return getCountryLocation(country).getLongitude();
    }

    private static CountryLocation getCountryLocation(String country) {
        return switch (country.toUpperCase()) {
            case "CANADA" -> CANADA;
            case "UNITED_STATES" -> UNITED_STATES;
            case "UNITED_KINGDOM" -> UNITED_KINGDOM;
            case "AUSTRALIA" -> AUSTRALIA;
            case "GERMANY" -> GERMANY;
            case "FRANCE" -> FRANCE;
            case "JAPAN" -> JAPAN;
            case "CHINA" -> CHINA;
            case "INDIA" -> INDIA;
            case "BRAZIL" -> BRAZIL;
            case "SOUTH_AFRICA" -> SOUTH_AFRICA;
            case "RUSSIA" -> RUSSIA;
            case "ITALY" -> ITALY;
            case "MEXICO" -> MEXICO;
            case "SPAIN" -> SPAIN;
            default -> throw new IllegalArgumentException("Country not found: " + country);
        };
    }
}
