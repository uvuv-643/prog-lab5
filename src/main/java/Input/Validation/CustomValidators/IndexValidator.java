package Input.Validation.CustomValidators;

import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import Exceptions.ValidationException;

public class IndexValidator implements Validator {

    @Override
    public ValidatedData<Integer> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Index cannot be null");
        }
        data = data.trim();
        int index;
        try {
            index = Integer.parseInt(data);
            if (index < 0) {
                throw new ValidationException("Index must be non-negative");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong index provided");
        }
        return new ValidatedData<>(index);
    }

}
