package br.edu.up.todolist;

import br.edu.up.todolist.utils.Util;
import br.edu.up.todolist.views.ToDoView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        int op = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            exibirMenu();
            op = Util.lerOpcoesMenu(scanner);
            processaEscolhaUsuario(scanner, op);
        } while (op != 0);
    }

    /**
     * Método responsável por processar a escolha do usuário
     * @param scanner
     * @param op
     */
    private static void processaEscolhaUsuario(Scanner scanner, int op) {
        switch (op) {
            case 0 -> System.out.println("Programa encerrado");
            case 1 -> ToDoView.iniciar(scanner);
            case 99 -> Util.showFeedbackMessage("Você precisa informar um valor inteiro.");
            default -> Util.showFeedbackMessage("Opção invalida! Escolha uma opção de acordo com o menu.");
        }
    }

    /**
     * Método responsável por exibir o menu principal
     */
    private static void exibirMenu() {
        System.out.println("###########################");
        System.out.println("#           MENU          #");
        System.out.println("###########################");
        System.out.println("0 - SAIR");
        System.out.println("1 - TO Do LIST");
    }
}