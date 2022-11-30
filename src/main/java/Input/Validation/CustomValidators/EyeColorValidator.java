package Input.Validation.CustomValidators;

import Entities.Color;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import Exceptions.ValidationException;

import java.util.Optional;

public class EyeColorValidator implements Validator {

    @Override
    public ValidatedData<Optional<Color>> validate(String data) throws ValidationException {
        if (data != null) {
            data = data.trim();
        }
        Optional<Color> color = switch (data) {
            case null, "" -> Optional.empty();
            case "BLACK" -> Optional.of(Color.BLACK);
            case "BLUE" -> Optional.of(Color.BLUE);
            case "YELLOW" -> Optional.of(Color.YELLOW);
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
