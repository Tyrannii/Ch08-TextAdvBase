import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Colten Aguilar
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
private Room currentRoom;
private ArrayList<Room> roomHistory;
private Player player;
        
  /**
 * Create the game and initialize the rooms, parser,
 * room history, and player.
 */
public Game()
{
    createRooms();
    parser = new Parser();
    roomHistory = new ArrayList<>();
    player = new Player(currentRoom);
}
    
    /**
     * Create the eight rooms of the haunted mansion
     * and connect their exits.
     */
    private void createRooms()
    {
        Room entrance, livingRoom, kitchen, diningRoom;
        Room library, bedroom, basement, attic;

        // Create the rooms.
        entrance = new Room("in the entrance hall of a haunted mansion");
        livingRoom = new Room("in a dusty living room");
        kitchen = new Room("in an abandoned kitchen");
        diningRoom = new Room("in a dark dining room");
        library = new Room("in a library filled with old books");
        bedroom = new Room("in a creepy bedroom");
        basement = new Room("in a cold, dark basement");
        attic = new Room("in an attic filled with cobwebs");

        // Connect the rooms.
        entrance.setExit("north", livingRoom);
        entrance.setExit("east", kitchen);
        entrance.setExit("west", library);
        entrance.setExit("south", basement);

        livingRoom.setExit("south", entrance);
        livingRoom.setExit("east", diningRoom);

        kitchen.setExit("west", entrance);
        kitchen.setExit("north", diningRoom);

        diningRoom.setExit("west", livingRoom);
        diningRoom.setExit("south", kitchen);

        library.setExit("east", entrance);
        library.setExit("north", bedroom);

        bedroom.setExit("south", library);
        bedroom.setExit("up", attic);

        attic.setExit("down", bedroom);

        basement.setExit("north", entrance);
        
        // Add items to the rooms.
entrance.setItem(new Item("key", "An old rusty key", 1));
entrance.setItem(new Item("flashlight", "An old flashlight", 2));
livingRoom.setItem(new Item("candle", "A half-burned candle", 1));
kitchen.setItem(new Item("knife", "An old kitchen knife", 2));
diningRoom.setItem(new Item("plate", "A dusty silver plate", 2));
library.setItem(new Item("book", "A mysterious ancient book", 3));
bedroom.setItem(new Item("doll", "An old porcelain doll", 2));
basement.setItem(new Item("lantern", "A broken lantern", 4));
attic.setItem(new Item("chest", "A small wooden chest", 5));

        // Start the player in the entrance hall.
        currentRoom = entrance;
    }


    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
 * Print the welcome message and starting room information.
 */
private void printWelcome()
{
    System.out.println();
    System.out.println("Welcome to the Haunted Mansion!");
    System.out.println("You have entered an old mansion filled with mysteries.");
    System.out.println("Explore the rooms and discover what is hidden inside.");
    System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
    System.out.println();
    System.out.println(currentRoom.getLongDescription());
}

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
    goRoom(command);
    break;

case LOOK:
    System.out.println(currentRoom.getLongDescription());
    break;
    
   case BACK:
    if (roomHistory.isEmpty()) {
        System.out.println("There is no previous room!");
    }
    else {
        currentRoom = roomHistory.remove(roomHistory.size() - 1);
        player.setCurrentRoom(currentRoom);
        System.out.println(currentRoom.getLongDescription());
    }
    break;
    
    case TAKE:
    takeItem(command);
    break;
    
    case INVENTORY:
    printInventory();
    break;

case QUIT:
    wantToQuit = quit(command);
    break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
    System.out.println("There is no door!");
}
else {
    roomHistory.add(currentRoom);
    currentRoom = nextRoom;
    player.setCurrentRoom(currentRoom);
    System.out.println(currentRoom.getLongDescription());
}
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
 * Pick up an item from the current room and add it
 * to the player's inventory.
 *
 * @param command The command containing the item's name.
 */
private void takeItem(Command command)
{
    if (!command.hasSecondWord()) {
        System.out.println("Take what?");
        return;
    }

    String itemName = command.getSecondWord();
    Item item = currentRoom.removeItem(itemName);

    if (item == null) {
        System.out.println("That item is not here.");
    }
    else {
        player.addItem(item);
        System.out.println("You picked up: " + item.getName());
    }
}

/**
 * Display all items currently carried by the player.
 */
private void printInventory()
{
    if (player.getInventory().isEmpty()) {
        System.out.println("Your inventory is empty.");
    }
    else {
        System.out.println("You are carrying:");

        for (Item item : player.getInventory()) {
            System.out.println("- " + item.getName()
                + " (Weight: " + item.getWeight() + ")");
        }
    }
}
}
