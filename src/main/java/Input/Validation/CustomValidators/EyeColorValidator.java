package Input.Validation.CustomValidators;

import Entities.Color;
import Entities.Country;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import exceptions.ValidationException;

import java.util.Optional;

public class EyeColorValidator implements Validator {

    @Override
    public ValidatedData<Optional<Color>> validate(String data) throws ValidationException {
        if (data != null) {
            data = data.trim();
        }
        Optional<Color> color = switch (data) {
            case null, "" -> Optional.empty();
            case "black" -> Optional.of(Color.BLACK);
            case "blue" -> Optional.of(Color.BLUE);
            case "yellow" -> Optional.of(Color.YELLOW);
            default -> null;
        };
        if (color == null) {
            throw new ValidationException("Incorrect eye color");
        }
        return new ValidatedData<>(color);
    }

    public ValidatedData<Color> validate(Color data) throws ValidationException {
        return new ValidatedData<>(data);
    }

}
