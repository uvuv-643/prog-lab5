package Input.Generators;

import Entities.Person;

import java.util.ArrayList;
import java.util.Optional;

public interface Generator<T> {
    T generate();
}
