package CommandPattern;

import Entities.Person;
import Input.Generators.IDGenerator;
import Input.InputManager;
import Input.Validation.CustomValidators.IDValidator;
import exceptions.ValidationException;

import java.lang.reflect.Array;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class Receiver {

    private final ZonedDateTime creationDate;

    public Receiver() {
        creationDate = ZonedDateTime.now();
    }

    public String help(Invoker invoker) {
        StringBuilder commandsString = new StringBuilder();
        for (Command command : invoker.getCommandMap().values()) {
            commandsString.append(command.getHelp()).append("\n");
        }
        return commandsString.toString();
    }

    public void exit() {
        System.exit(0);
    }

    public void clear(ArrayList<Person> collection) {
        collection.clear();
    }

    public boolean add(ArrayList<Person> collection) {
        InputManager inputManager = new InputManager();
        try {
            IDGenerator idGenerator = new IDGenerator(collection);
            long generatedId = idGenerator.generate();
            Person person = inputManager.inputPerson(generatedId);
            collection.add(person);
            return true;
        } catch (ValidationException exception) {
            System.out.println("Internal server error. Provided data is incorrect.");
            return false;
        }
    }

    public String addIfMin(TreeSet<City> collection) {
        if (collection.isEmpty()) {
            return "Коллекция пуста. Создайте хотя бы один элемент, чтобы использовать эту команду.";
        }
        System.out.println("Следующий введённый город будет добавлен в колекцию в случае, если его поле population будет " +
                "наименьшим в коллекции.");
        System.out.println("На данный момент в коллекции наименьшее значение поля population = " + collection.first().getPopulation());
        return Adder.addCityIfMin(collection);
    }

    public String show(ArrayList<Person> collection) {
        if (collection.isEmpty()) {
            return "Коллекция пуста.";
        } else {
            System.out.println("Элементы коллекции в строковом предствлении: ");
            StringBuilder s = new StringBuilder();
            for (Person person : collection) {
                s.append(person);
            }
            return s.toString();
        }
    }

    public String filterStartsWithName(TreeSet<City> collection, String name) {
        System.out.println("Будут выведены элементы коллекции, значение поля name которых начинается с подстроки " + name + ":");
        StringBuilder namesString = new StringBuilder();
        for (City city : collection) {
            if (city.getName().startsWith(name)) {
                namesString.append(city).append(", ");
            }
        }
        if (namesString.toString().isEmpty()) {
            return "Элементов с таким условием в коллекции не найдено!";
        } else {
            return namesString.deleteCharAt(namesString.length() - 2).toString();
        }
    }

    public String printDescending(TreeSet<City> collection) {
        System.out.println("Элементы коллекции в порядке убывания (обратном): ");
        if (collection.isEmpty()) {
            return "Коллекция пуста.";
        } else {
            StringBuilder descCollection = new StringBuilder();
            Iterator<City> i = collection.descendingIterator();
            while (i.hasNext()) {
                descCollection.append(i.next()).append(", ");
            }
            return descCollection.deleteCharAt(descCollection.length() - 2).toString();
        }
    }

    public String executeScript(Invoker invoker, TreeSet<City> collection, String filename) throws FileNotFoundException {

        Terminal terminal = new Terminal(invoker, collection);

        return terminal.startFile(filename);

    }

    public String removeAllByGovernment(TreeSet<City> collection, String argument) {

        for (Government government : Government.values()) {

            if (government.toString().equals(argument)) {

                boolean flag = false;

                for (City city : collection) {
                    if (city.getGovernment().equals(government)) {
                        collection.remove(city);
                        flag = true;
                    }
                }
                if (flag) {
                    return "Элементы коллекции с заданным условием удалены.";
                } else {
                    return "Элементов коллекции с заданным полем government не найдено.";
                }
            }

        }

        return "Такого поля Government не существует.";
    }

    public String removeById(TreeSet<City> collection, String argument) {
        Long id;
        try {
            id = Long.parseLong(argument);
        } catch (NumberFormatException e) {
            id = null;
        }
        if (id == null) {
            return "Аргумент id передан неверно.";
        }
        String element = "Элемента с данным id не существует.";
        for (City city : collection) {
            if (city.getId().equals(id)) {
                element = city + " удалён из коллекции.";
                collection.remove(city);
                break;
            }
        }
        return element;
    }

    public String removeGreater(TreeSet<City> collection) {
        System.out.println("Создайте элемент.");
        City delCity = Adder.createCity();
        collection.removeIf(city -> delCity.compareTo(city) > 0);
        return "Элементы, меньшие, чем заданный, удалены.";
    }

    public String removeLower(TreeSet<City> collection) {
        System.out.println("Создайте элемент.");
        City delCity = Adder.createCity();
        collection.removeIf(city -> delCity.compareTo(city) < 0);
        return "Элементы, меньшие, чем заданный, удалены.";
    }

    public String info(ArrayList<Person> collection) {
        String typeOfCollection = String.format("Тип коллекции: %s", collection.getClass());
        String dateOfInitialization = String.format("Дата инициализации: %s", creationDate);
        String countOfElements = String.format("Количество элементов коллекции: %s", collection.size());
        return String.format("%s \n%s \n%s \n", typeOfCollection, dateOfInitialization, countOfElements);
    }

    public String updateId(TreeSet<City> collection, String idArgument) {
        Long id;
        try {
            id = Long.parseLong(idArgument);
        } catch (NumberFormatException e) {
            id = null;
        }
        if (id == null) {
            return "Аргумент id передан неверно.";
        }
        String result = "Элемента с данным id не существует.";
        for (City city : collection) {
            if (city.getId().equals(id)) {
                System.out.println("Будет произведена замена элемента " + city);
                City newCity = Adder.createCity();
                newCity.setId(id);
                collection.remove(city);
                collection.add(newCity);
                result = "Элемент успешно изменен. Его поле id останется прежним.";
            }
        }
        return result;
    }

    public String save(TreeSet<City> collection) {

        WorkWithFile worker = new WorkWithFile();

        return worker.writeInFile(collection);

    }

}
