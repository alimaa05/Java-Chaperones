package com.chaperones.venue;

import java.util.Objects;

public class Venue {
    private Integer id;
    private String name;
    private String area;
    private String address;


    public Venue(Integer id, String name, String area, String address) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.address = address;
    }

    public Venue(){}

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return Objects.equals(id, venue.id) && Objects.equals(name, venue.name) && Objects.equals(area, venue.area) && Objects.equals(address, venue.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, area, address);
    }
}
