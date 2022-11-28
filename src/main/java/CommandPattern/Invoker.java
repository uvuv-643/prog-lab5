package CommandPattern;

import Entities.Person;
import global.CollectionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Invoker {

    private CollectionManager collectionManager;

    public Invoker(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    private final HashMap<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public String execute(String commandName, ArrayList<Person> collection, String argument) {
        Command command = commandMap.get(commandName);
        return command.execute(this, collection, argument);
    }

    public HashMap<String, Command> getCommandMap() {
        return this.commandMap;
    }

}
