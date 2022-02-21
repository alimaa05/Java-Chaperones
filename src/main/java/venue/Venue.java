package venue;

import java.util.Objects;

public class Venue {
    private String name;
    private String area;
    private String address;


    public Venue(String name, String area, String address) {
        this.name = name;
        this.area = area;
        this.address = address;
    }

    public Venue(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return Objects.equals(name, venue.name) && Objects.equals(area, venue.area) && Objects.equals(address, venue.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area, address);
    }
}
