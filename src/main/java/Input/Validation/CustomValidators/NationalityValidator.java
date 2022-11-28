package Input.Validation.CustomValidators;

import Entities.Color;
import Entities.Country;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import exceptions.ValidationException;

import java.util.Optional;

public class NationalityValidator implements Validator {

    @Override
    public ValidatedData<Optional<Country>> validate(String data) throws ValidationException {
        if (data != null) {
            data = data.trim();
        }
        Optional<Country> nationality = switch (data) {
            case null, "" -> Optional.empty();
            case "Russia" -> Optional.of(Country.RUSSIA);
            case "China" -> Optional.of(Country.CHINA);
            case "Thailand" -> Optional.of(Country.THAILAND);
            case "Vatican" -> Optional.of(Country.VATICAN);
            default -> null;
        };
        if (nationality == null) {
            throw new ValidationException("Incorrect nationality");
        }
        return new ValidatedData<>(nationality);
    }

    public ValidatedData<Country> validate(Country data) throws ValidationException {
        return new ValidatedData<>(data);
    }

}
