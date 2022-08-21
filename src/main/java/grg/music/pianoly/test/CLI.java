package grg.music.pianoly.test;

import grg.music.pianoly.gui.GUI;

import java.util.Scanner;

public class CLI {

    public CLI() {
        new Thread(() -> {
            CLIDeviceIn[] ins = CLIDeviceIn.getDevices();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String[] s = scanner.nextLine().split(" ", 2);
                if (s.length == 2 && s[0].length() == 1 && Character.isDigit(s[0].charAt(0))
                        && Integer.parseInt(s[0]) < CLIDeviceIn.getDevices().length)
                    ins[Integer.parseInt(s[0])].addInput(s[1]);

                else
                    GUI.getInstance().getIn().studentsDisplayChanged(s[0], s[1], null);
            }
        }).start();
    }
}
