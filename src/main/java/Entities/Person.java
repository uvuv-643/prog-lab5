package Entities;

import Input.Validation.CustomValidators.*;
import exceptions.ValidationException;

import java.time.ZonedDateTime;
import java.util.Optional;

public class Person {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int height; //Значение поля должно быть больше 0
    private float weight; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null


    private Person(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, int height, float weight, Color eyeColor, Country nationality, Location location) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.location = location;
    }

    public static Person personCreator(Long id, String name, Coordinates coordinates, int height, int weight, Color eyeColor, Country nationality, Location location) throws ValidationException {
        NameValidator nameValidator = new NameValidator();
        CoordinatesValidator coordinatesValidator = new CoordinatesValidator();
        HeightValidator heightValidator = new HeightValidator();
        WeightValidator weightValidator = new WeightValidator();
        EyeColorValidator eyeColorValidator = new EyeColorValidator();
        NationalityValidator nationalityValidator = new NationalityValidator();
        LocationValidator locationValidator = new LocationValidator();

        ZonedDateTime created_at = ZonedDateTime.now();
        nameValidator.validate(name);
        coordinatesValidator.validate(coordinates);
        heightValidator.validate(height);
        weightValidator.validate(weight);
        eyeColorValidator.validate(eyeColor);
        nationalityValidator.validate(nationality);
        locationValidator.validate(location);

        return new Person(id, name, coordinates, created_at, height, weight, eyeColor, nationality, location);
    }

    public static void input() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}