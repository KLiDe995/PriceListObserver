package ru.ivglv.PriceListObserver.Ui;

import ru.ivglv.PriceListObserver.Controller.ConsoleController;
import ru.ivglv.PriceListObserver.Controller.Injector;

import java.util.Scanner;


public class MainWindow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Injector.getInstance().initConsoleController();
        ConsoleController controller = Injector.getInstance().getConsoleControllerFactory().controller();
        System.out.println("Pricelist Observer Test Task. Version 1.0 (C) Golovenkin I.G. 2020");
        System.out.println("Command list: start | stop | exit");
        while(scanner.hasNextLine())
        {
            controller.handleCommand(scanner.nextLine());
        }
    }
}
