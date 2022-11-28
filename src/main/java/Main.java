import Input.Validation.CustomValidators.CoordinatesValidator;
import Input.Validation.CustomValidators.LocationValidator;
import Input.Validation.CustomValidators.NameValidator;
import exceptions.ValidationException;

public class Main {

    public static void main(String[] args) throws ValidationException {
        LocationValidator locationValidator = new LocationValidator();
        System.out.println(locationValidator.validate("1231 231").getValidatedData().get());
    }

}
