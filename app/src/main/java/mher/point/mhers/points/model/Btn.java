package mher.point.mhers.points.model;


import java.io.Serializable;

public class Btn implements Serializable {
    private String id;
    private int x;
    private int y;
    private int point;
    private int categori;

    public Btn(int point) {
        this.point = point;
    }

    public Btn(String id, int x, int y, int point, int categori) {

        this.id = id;
        this.x = x;
        this.y = y;
        this.point = point;
        this.categori = categori;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCategori() {
        return categori;
    }

    public void setCategori(int categori) {
        this.categori = categori;
    }


}