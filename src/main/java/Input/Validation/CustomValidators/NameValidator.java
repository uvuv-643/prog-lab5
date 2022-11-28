package Input.Validation.CustomValidators;

import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import exceptions.ValidationException;

public class NameValidator implements Validator {

    @Override
    public ValidatedData<String> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Name cannot be null");
        } else if (data.isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        return new ValidatedData<>(data);
    }

}
