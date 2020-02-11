package model;

import java.util.List;

/**
 * Created by mohamed on 11/27/17.
 */

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private List<Order> products;
    public Request(){

    }

    public Request(String phone, String name, String address, String total, List<Order> products) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.products = products;
        this.status="0"; // default 0 , 0 is placed , 1 is shipping ,2 is shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getproducts() {
        return products;
    }

    public void setproducts(List<Order> products) {
        this.products = products;
    }
}
