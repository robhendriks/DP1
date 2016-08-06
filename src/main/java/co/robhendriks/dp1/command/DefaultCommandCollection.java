package co.robhendriks.dp1.command;

import co.robhendriks.dp1.circuit.Circuit;
import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.node.common.InputNode;
import co.robhendriks.dp1.node.Input;

public class DefaultCommandCollection extends CommandCollection {
    @CommandHandler(name = "exit")
    public boolean exitCommand(String[] args) {
        return getApp().requestShutdown();
    }

    @CommandHandler(name = "set", usage = "%cmd% <node> <value>")
    public boolean setCommand(String[] args) {
        if (args.length != 2) {
            return false;
        }
        String nodeName = args[0];
        String nodeValue = args[1];

        Circuit circuit = getApp().getCircuit();
        InputNode node;
        if ((node = circuit.getInputNode(nodeName)) == null) {
            System.out.println("Unknown node: " + nodeName);
            return true;
        }

        int value;
        try {
            value = Integer.parseInt(nodeValue);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number: " + nodeValue);
            return true;
        }

        Input input;
        if ((input = Input.parse(value)) == null) {
            System.out.println("Invalid input: " + nodeValue);
            return true;
        }
        node.setValue(input);
        return true;
    }

    @CommandHandler(name = "list")
    public boolean listCommand(String[] args) {
        String message = "";
        message += "====== NODES ======\n";
        for (AbstractNode node : getApp().getCircuit().getNodes().values()) {
            message += node.toString() + "\n";
        }
        message += "===================\n";
        System.out.println(message);
        return true;
    }


    @CommandHandler(name = "run")
    public boolean runCommand(String[] args) {
        try {
            getApp().getCircuit().run();
        } catch (CircuitException e) {
            System.out.println("Error while running circuit");
            e.printStackTrace();
        }
        return true;
    }
}
