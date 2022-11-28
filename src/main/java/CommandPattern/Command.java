package CommandPattern;

import Entities.Person;

import java.util.ArrayList;

public interface Command {

    String execute(Invoker invoker, ArrayList<Person> collection, String argument);
    String getHelp();

}
