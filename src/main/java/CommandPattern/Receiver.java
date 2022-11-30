package CommandPattern;

import App.Terminal;
import Entities.Country;
import Entities.Person;
import Input.FileManager.FileManager;
import Input.Generators.IDGenerator;
import Input.InputManager;
import Input.Validation.CustomValidators.IDValidator;
import Input.Validation.CustomValidators.IndexValidator;
import Input.Validation.CustomValidators.NationalityValidator;
import Exceptions.ValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public class Receiver {

    private final ZonedDateTime creationDate;
    private final InputManager inputManager = new InputManager();

    public Receiver() {
        creationDate = ZonedDateTime.now();
    }

    public boolean info(ArrayList<Person> collection) {
        String typeOfCollection = String.format("Type of collection: %s", collection.getClass());
        String dateOfInitialization = String.format("Initialization date: %s", creationDate);
        String countOfElements = String.format("Elements in collection: %d", collection.size());
        System.out.printf("%s \n%s \n%s \n", typeOfCollection, dateOfInitialization, countOfElements);
        return true;
    }

    public boolean show(ArrayList<Person> collection) {
        if (collection.isEmpty()) {
            System.out.println("Collection is empty");
        } else {
            System.out.println("Elements in collection:");
            System.out.println();
            for (Person person : collection) {
                System.out.println(person);
            }
        }
        return true;
    }

    public boolean add(ArrayList<Person> collection) {
        try {
            IDGenerator idGenerator = new IDGenerator(collection);
            long generatedId = idGenerator.generate();
            Person person = this.inputManager.inputPerson(generatedId);
            collection.add(person);
            return true;
        } catch (ValidationException exception) {
            System.out.println("Internal server error. Provided data is incorrect.");
            return false;
        }
    }

    public boolean update(ArrayList<Person> collection, String idRaw) {
        long id;
        IDValidator idValidator = new IDValidator();
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, collection);
            try {
                Person person = this.inputManager.inputPerson(id);
                int updatedCount = 0;
                for (Person collectionPerson : collection) {
                    if (collectionPerson.getId().equals(id)) {
                        collection.set(collection.indexOf(collectionPerson), person);
                        updatedCount++;
                    }
                }
                if (updatedCount == 0) {
                    throw new ValidationException("Not found element with this id");
                }
                return true;
            } catch (ValidationException exception) {
                System.out.println("Internal server error. " + exception.getMessage());
                return false;
            }
        } catch (ValidationException exception) {
            System.out.println("Passed incorrect ID");
            return false;
        }
    }

    public boolean removeById(ArrayList<Person> collection, String idRaw) {
        long id;
        IDValidator idValidator = new IDValidator();
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, collection);
            try {
                Optional<Person> elementInCollection = collection.stream().filter((element) -> element.getId().equals(id)).findFirst();
                elementInCollection.ifPresent(collection::remove);
                if (elementInCollection.isEmpty()) {
                    throw new ValidationException("Not found element with this id");
                }
                return true;
            } catch (ValidationException exception) {
                System.out.println("Internal server error. " + exception.getMessage());
                return false;
            }
        } catch (ValidationException exception) {
            System.out.println("Passed incorrect ID");
            return false;
        }
    }

    public void clear(ArrayList<Person> collection) {
        collection.clear();
    }

    public boolean save(ArrayList<Person> collection) {
        FileManager worker = new FileManager();
        try {
            worker.writeInFile(collection);
        } catch (IOException exception) {
            System.out.println("Passed incorrect argument or you have no access to file");
            return false;
        }
        return true;
    }

    public boolean executeScript(Invoker invoker, ArrayList<Person> collection, String filename) {
        try {
            Terminal terminal = new Terminal(invoker, collection);
            terminal.startFile(filename);
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("File was not found. Cannot execute script");
            return false;
        }
    }

    public void exit() {
        System.exit(0);
    }

    public boolean removeAt(ArrayList<Person> collection, String indexRaw) {
        int index;
        IndexValidator indexValidator = new IndexValidator();
        try {
            index = indexValidator.validate(indexRaw).getValidatedData();
            collection.remove(index);
            return true;
        } catch (ValidationException exception) {
            System.out.println(exception.getMessage());
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Index out of bounds");
        }
        return false;

    }

    public boolean addIfMin(ArrayList<Person> collection) {
        try {
            IDGenerator idGenerator = new IDGenerator(collection);
            long generatedId = idGenerator.generate();
            Person person = this.inputManager.inputPerson(generatedId);
            Optional<Person> min = collection.stream().min(Comparator.comparingInt(Person::getHeight));
            min.ifPresentOrElse((element) -> {
                if (person.compareTo(element) < 0) {
                    collection.add(person);
                }
            }, () -> collection.add(person));
            return false;
        } catch (ValidationException exception) {
            System.out.println("Internal server error. " + exception.getMessage());
            return false;
        }
    }

    public boolean reorder(ArrayList<Person> collection) {
        Collections.reverse(collection);
        return true;
    }

    public boolean filterGreaterThanNationality(ArrayList<Person> collection, String nationalityRaw) {
        NationalityValidator nationalityValidator = new NationalityValidator();
        try {
            Country nationality = nationalityValidator.validate(nationalityRaw).getValidatedData().get();
            long targetPopulation = nationality.getPopulation();
            ArrayList<Person> filteredCollection = new ArrayList<>(collection.stream().filter((element) -> element.getNationality().getPopulation() > targetPopulation).toList());
            this.show(filteredCollection);
            return true;
        } catch (ValidationException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public boolean printDescending(ArrayList<Person> collection) {
        ArrayList<Person> reversedCollection = new ArrayList<>(collection);
        Collections.reverse(reversedCollection);
        this.show(reversedCollection);
        return true;
    }

    public boolean printFieldDescendingLocation(ArrayList<Person> collection) {
        Country[] countries = collection.stream().map(Person::getNationality).sorted(Comparator.comparingLong(Country::getPopulation)).toArray(Country[]::new);
        if (countries.length == 0) {
            System.out.println("Collection in empty");
        } else {
            System.out.println("Elements in collection: ");
            for (Country country : countries) {
                System.out.println(country);
            }
        }
        return true;
    }

}
