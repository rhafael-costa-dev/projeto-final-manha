package br.edu.up.todolist.views;

import br.edu.up.todolist.controllers.TarefaController;
import br.edu.up.todolist.controllers.UsuarioController;
import br.edu.up.todolist.exceptions.TarefaNotFoundException;
import br.edu.up.todolist.models.Tarefa;
import br.edu.up.todolist.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.UUID;

public class ToDoView {
    private static final Logger logger = LogManager.getLogger(ToDoView.class);

    public static void iniciar(Scanner scanner) {
        int op;
        do {
            exibirMenu();
            op = Util.lerOpcoesMenu(scanner);
            exibirEscolha(scanner, op);
        } while (op != 0);
    }

    /**
     * Método responsável por exibir o menu de opções
     */
    private static void exibirMenu() {
        System.out.println("###########################");
        System.out.println("#        TO-DO-LIST       #");
        System.out.println("###########################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Alterar");
        System.out.println("3 - Remover");
        System.out.println("4 - Listar");
        System.out.println("5 - Detalhar Tarefa");
    }

    /**
     *
     * @param scanner
     * @param op
     */
    private static void exibirEscolha(Scanner scanner, int op) {
        switch (op) {
            case 0 -> Util.showFeedbackMessage("");
            case 1 -> cadastar(scanner);
            case 2 -> atualizar(scanner);
            case 3 -> remover(scanner);
            case 4 -> listar();
            case 5 -> detalhar(scanner);
            case 99 -> Util.showFeedbackMessage("Você precisa informar um valor inteiro.");
            default -> Util.showFeedbackMessage("Opção invalida! Escolha uma opção de acordo com o menu.");
        }
    }

    /**
     * Método responsável por cadastrar um nova tarefa
     * @param scanner
     */
    private static void cadastar(Scanner scanner) {
        try {
            System.out.println("Digite o titulo: ");
            var titulo = scanner.nextLine();

            System.out.println("Digite o descriçao: ");
            var descricao = scanner.nextLine();

            System.out.println("Digite o prioridade: ");
            var prioridade = scanner.nextLine();

            UsuarioView.exibirDadosUsuarios();
            System.out.println("Escolha o usuario por UUID: ");
            var uuid = scanner.nextLine();

            // Buscando os dados do usuario
            var usuario = UsuarioController.buscarUsuarioPorUUID(UUID.fromString(uuid));
            // criando o objeto tarefa
            var tarefa = new Tarefa(titulo, descricao, prioridade, usuario);
            //salvando o objeto tarefa
            TarefaController.cadastrar(tarefa);
        } catch (Exception ex) {
            logger.error("Ocorreu um erro ao tentar criar uma tarefa.", ex);
        }
    }

    /**
     * Método responsável por atualizar os dados da tarefa
     * @param scanner
     */
    private static void atualizar(Scanner scanner) {
        try {
            listar();
            System.out.println("Qual Tarefa você deseja atualizar?");
            var uuid = scanner.nextLine();

            System.out.println("####################################");
            System.out.println("ATUALIZAÇÃO");
            System.out.println("####################################");

            System.out.println("Digite a descriçao: ");
            var descricao = scanner.nextLine();

            System.out.println("Digite a prioridade: ");
            var prioridade = scanner.nextLine();

            // criando o objeto tarefa
            var tarefa = new Tarefa();
            tarefa.setDescricao(descricao);
            tarefa.setPrioridade(prioridade);

            //salvando o objeto tarefa
            TarefaController.atualizar(UUID.fromString(uuid), tarefa);
        } catch (TarefaNotFoundException ex) {
            Util.showFeedbackMessage(ex.getMessage());
            logger.warn("Ocorreu um erro ao tentar atualizar a tarefa.", ex);
        } catch (Exception ex) {
            var message = "Ocorreu um erro ao tentar criar uma tarefa.";
            Util.showFeedbackMessage(message);
            logger.error(message, ex);
        }
    }

    /**
     * Método responsável por remover um tarefa
     * @param scanner
     */
    private static void remover(Scanner scanner) {
        try {
            listar();
            System.out.println("Qual Tarefa você deseja remover? ");
            var uuid = scanner.nextLine();

            TarefaController.remover(UUID.fromString(uuid));
        } catch (TarefaNotFoundException ex) {
            Util.showFeedbackMessage(ex.getMessage(), true);
            logger.warn("Ocorreu um erro ao tentar remover a tarefa.", ex);
        }
    }

    /**
     * Método responável por listar todas as tarefas
     */
    private static void listar() {
        var tarefas = TarefaController.listar();
        System.out.println("######## LISTA DE TAREFAS ############");
        tarefas.forEach(tarefa -> {
            exibirDadosTarefa(tarefa, false);
        });
        System.out.println("########################################");
    }

    /**
     * Método responsável por exibir os detalhes da tarefa
     * @param scanner
     */
    private static void detalhar(Scanner scanner) {
        try {
            listar();

            System.out.println("Escolha uma tarefa.");
            var uuid = scanner.nextLine();

            var tarefa = TarefaController.buscarTarefaPorUuid(UUID.fromString(uuid));
            exibirDadosTarefa(tarefa, true);
        } catch (TarefaNotFoundException ex) {
            Util.showFeedbackMessage(ex.getMessage());
            logger.warn("Ocorreu um erro ao tentar buscar a tarefa por UUID.", ex);
        }
    }

    /**
     * Método responsável por exibir os dados da tarefa
     * @param tarefa
     * @param exibirDetalhes
     */
    private static void exibirDadosTarefa(Tarefa tarefa, boolean exibirDetalhes) {
        System.out.println("UUID: " + tarefa.getUuid());
        System.out.println("TITULO: " + tarefa.getTitulo());
        if (exibirDetalhes) {
            System.out.println("DESCRIÇÃO: " + tarefa.getDescricao());
            System.out.println("PRIORIDADE: " + tarefa.getPrioridade());
            System.out.println("USUARIO: " + tarefa.getUsuario().getNome());
        }
        System.out.println("-----------------------------------------");
    }
}
