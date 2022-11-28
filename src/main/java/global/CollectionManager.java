package global;

import Entities.Person;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class CollectionManager {
    ArrayList<Person> persons = new ArrayList<>();
    /**
     * The Init time.
     */
    ZonedDateTime initTime = ZonedDateTime.now();
}
