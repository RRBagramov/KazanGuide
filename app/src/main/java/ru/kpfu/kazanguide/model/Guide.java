package ru.kpfu.kazanguide.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Guide implements Serializable {
    private String name;
    private String photoLink;
    private List<String> coordinates;
    private String description;

    public Guide() {
    }

    public Guide(String name, String photoLink, ArrayList<String> coordinates, String description) {
        this.name = name;
        this.photoLink = photoLink;
        this.coordinates = coordinates;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public List<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<String> coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
