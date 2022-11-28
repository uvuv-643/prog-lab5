package Input.Validation.CustomValidators;

import Entities.Coordinates;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import exceptions.ValidationException;

public class HeightValidator implements Validator {

    @Override
    public ValidatedData<Integer> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Height cannot be null");
        }
        int height;
        try {
            height = Integer.parseInt(data);
            if (height <= 0) {
                throw new ValidationException("Height must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong data provided");
        }
        return new ValidatedData<>(height);
    }

    public ValidatedData<Integer> validate(int data) throws ValidationException {
        if (data <= 0) {
            throw new ValidationException("Height must be greater than zero");
        }
        return new ValidatedData<>(data);
    }

}