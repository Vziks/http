package info.vziks.httpserver.testy;

/**
 * Class Organisation
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class Organisation {
    private String organisation_name;
    private String description;
    private int Employees;

    // Calling getters and setters
    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEmployees() {
        return Employees;
    }

    public void setEmployees(int employees) {
        Employees = employees;
    }

    // Creating toString
    @Override
    public String toString() {
        return "Organisation [organisation_name="
                + organisation_name
                + ", description="
                + description
                + ", Employees="
                + Employees + "]";
    }
}
