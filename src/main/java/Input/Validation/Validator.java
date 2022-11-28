package Input.Validation;

import exceptions.ValidationException;

public interface Validator {

    public ValidatedData validate(String data) throws ValidationException;

}
