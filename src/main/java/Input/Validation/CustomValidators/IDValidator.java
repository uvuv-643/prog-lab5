package Input.Validation.CustomValidators;

import Entities.Person;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import exceptions.ValidationException;

import java.util.ArrayList;

public class IDValidator implements Validator {

    @Override
    public ValidatedData<Long> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("ID cannot be null");
        }
        long id;
        try {
            id = Long.parseLong(data);
            if (id <= 0) {
                throw new ValidationException("ID must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong ID provided");
        }
        return new ValidatedData<>(id);
    }

    public ValidatedData<Long> validateUnique(Long Id, ArrayList<Person> collection) throws ValidationException {
        boolean isUnique = collection.stream().anyMatch((element) -> element.getId().equals(Id));
        if (isUnique) {
            return new ValidatedData<>(Id);
        }
        throw new ValidationException("ID is not unique");
    }

}
