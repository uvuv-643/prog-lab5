package Input.Validation.CustomValidators;

import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import exceptions.ValidationException;

public class WeightValidator implements Validator {

    @Override
    public ValidatedData<Integer> validate(String data) throws ValidationException {
        int weight;
        try {
            weight = Integer.parseInt(data);
            if (weight <= 0) {
                throw new ValidationException("Weight must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong data provided");
        }
        return new ValidatedData<>(weight);
    }

    public ValidatedData<Integer> validate(int data) throws ValidationException {
        if (data <= 0) {
            throw new ValidationException("Weight must be greater than zero");
        }
        return new ValidatedData<>(data);
    }

}
