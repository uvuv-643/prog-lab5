package Input.Validation.CustomValidators;

import Entities.Coordinates;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import Exceptions.ValidationException;

public class CoordinatesValidator implements Validator {

    private final String COORDINATES_DELIMITER = " ";

    @Override
    public ValidatedData<Coordinates> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Coordinates cannot be null");
        }
        String[] coordinates = data.replaceAll(",", ".").replaceAll("[\s]{2,}", " ").trim().split(this.COORDINATES_DELIMITER);
        if (coordinates.length != 2) {
            throw new ValidationException("Wrong coordinates format");
        }
        String xCoordinate = coordinates[0];
        String yCoordinate = coordinates[1];
        float x, y;
        try {
            x = Float.parseFloat(xCoordinate);
            y = Float.parseFloat(yCoordinate);
        } catch (NumberFormatException e) {
            throw new ValidationException("Given coordinates are not integers");
        }
        return new ValidatedData<>(new Coordinates(x, y));
    }

    public ValidatedData<Coordinates> validate(Coordinates data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Coordinates cannot be null");
        }
        return new ValidatedData<>(data);
    }

}
