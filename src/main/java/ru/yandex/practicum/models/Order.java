package ru.yandex.practicum.models;

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private Integer track;

    public String getFirstName() {
        return firstName;
    }

    public Order withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Order withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Order withAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getMetroStation() {
        return metroStation;
    }

    public Order withMetroStation(Integer metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Order withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public Order withRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Order withDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Order withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String[] getColor() {
        return color;
    }

    public Order withColor(String[] color) {
        this.color = color;
        return this;
    }

    public Integer getTrack() {
        return track;
    }

    public Order withTrack(Integer track) {
        this.track = track;
        return this;
    }
}
