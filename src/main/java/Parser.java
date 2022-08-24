import java.text.ParseException;

public class Parser {

    public static Commands parseText(String input)  {
        try {
            if (input.contains(";")) throw new UnallowedCharacterException(";");
            else if (input.toUpperCase().equals("BYE")) return new ExitCommand();
            else if (input.toUpperCase().equals("LIST")) return new ListCommand();
            else {
                String[] arr = input.split(" ", 2);
                String command = arr[0];
                String arguments = arr[1];
                switch (command.toUpperCase()) {
                    case "TODO":
                        Todo newTodo = new Todo(arguments, false);
                        return new AddCommand(newTodo);
                    case "DEADLINE":
                        String[] deadlineArgs = arguments.split("/by");
                        Deadline newDeadline = new Deadline(deadlineArgs[0].trim(), false, Duck.dateStorageConverter(deadlineArgs[1].trim()));
                        return new AddCommand(newDeadline);
                    case "EVENT":
                        String[] eventArgs = arguments.split("/at");
                        Event newEvent = new Event(eventArgs[0].trim(), false, Duck.dateStorageConverter(eventArgs[1].trim()));
                        return new AddCommand(newEvent);
                    case "MARK":
                        return new MarkCommand(Integer.parseInt(arguments) - 1);
                    case "UNMARK":
                        return new UnmarkCommand(Integer.parseInt(arguments) - 1);
                    case "DELETE":
                        return new DeleteCommand(Integer.parseInt(arguments) - 1);
                    default:
                        UI.unrecognizedCommandMessage();
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            if (input.toUpperCase().contains("TODO") ||
                    input.toUpperCase().contains("DEADLINE") ||
                    input.toUpperCase().contains("EVENT")) {
                System.out.println("Quack!!!!! " + input.toUpperCase() + " Arguments are missing!");
            } else {
                System.out.println("Speak properly! Quack!");
            }
        } catch (NumberFormatException n) {
            System.out.println("Invalid Arguments! Dummy!");
        } catch (UnallowedCharacterException e) {
            System.out.println("Character: " + e.getMessage() + " is not allowed! Quack!!");
        } catch (ParseException e) {
            System.out.println("Wrong date time format! Quack! (use: dd/MM/yyyy HHmm) ");
        }
        return new ErrorCommand();
    }

}
