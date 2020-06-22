package me.mateusneres.searchapartments.objects;

public class Apartments {

    private int id;
    private String name;
    private String location;
    private int rooms;
    private int cost;
    private String date_posted;
    private String date_end;

    public Apartments(int id, String name, String location, int rooms, int cost, String date_posted, String date_end) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rooms = rooms;
        this.cost = cost;
        this.date_posted = date_posted;
        this.date_end = date_end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(String date_posted) {
        this.date_posted = date_posted;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    @Override
    public String toString() {
        return "Apartments{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                ", cost=" + cost +
                ", date_posted='" + date_posted + '\'' +
                ", date_end='" + date_end + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
