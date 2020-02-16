package root.sciencecenter.enums;


public enum ScientificAreaEnum {
    INFORMATION_SYSTEMS,
    DATABASES,
    ARTIFICIAL_INTELLIGENCE,
    SOFTWARE_ENGINEERING,
    COMPUTER_COMMUNICATIONS,
    INFORMATION_TECHNOLOGIES;

    public String convertToString(ScientificAreaEnum sa) {
        switch(sa) {
            case INFORMATION_SYSTEMS:
                return "Informacioni sistemi";
            case DATABASES:
                return "Baze podataka";
            case ARTIFICIAL_INTELLIGENCE:
                return "Veštačka inteligencija";
            case SOFTWARE_ENGINEERING:
                return "Softverski inženjering";
            case COMPUTER_COMMUNICATIONS:
                return "Računarske komunikacije";
            default:
                return "Informacione tehnologije";
        }
    }

    public ScientificAreaEnum convertToScientificArea(String scientificArea) {
        switch(scientificArea) {
            case "Informacioni sistemi":
                return INFORMATION_SYSTEMS;
            case "Baze podataka":
                return DATABASES;
            case "Veštačka inteligencija":
                return ARTIFICIAL_INTELLIGENCE;
            case "Softverski inženjering":
                return SOFTWARE_ENGINEERING;
            case "Računarske komunikacije":
                return COMPUTER_COMMUNICATIONS;
            default:
                return INFORMATION_TECHNOLOGIES;
        }
    }
}