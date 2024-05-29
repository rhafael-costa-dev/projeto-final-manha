package br.edu.up.todolist.controllers;

import br.edu.up.todolist.daos.TarefaDao;
import br.edu.up.todolist.models.Tarefa;

public abstract class TarefaController {

    public static void cadastrar(Tarefa tarefa) {
        TarefaDao.salvar(tarefa);
    }

}
