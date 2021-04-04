package com.biachacon.tasks.controller;

import com.biachacon.tasks.domains.Task;
import com.biachacon.tasks.repositories.TaskRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@SessionScoped
public class TaskController {

	@Autowired
	private TaskRepository repo;

	private Task task = new Task();
	private List<Task> taskList;
	private Integer qtTasksIncomplete;
	private Integer qtTasksCompleted;
	private String lugar="Tarefas";

	@PostConstruct
	public void init() {
		taskList = repo.findAll();
		setLugar("Tarefas");
		task = new Task();
	}

	public String insert() {
			task.setStatus(false);
			repo.save(task);
			task = new Task();
			lugar();
			return "/index.xhtml";
	}

	public void search(){
		String desc = task.getTitle();
		taskList = repo.find(task.getId(), task.getTitle(), desc, task.getResponsible(), task.getStatus());
		task = new Task();
	}

	public void deleteAllStatusCompleted() {
		this.repo.deleteAllStatusCompleted();
		lugar();
	}

	public void changeListActive() {
		taskList = repo.findByStatus(false);
		setLugar("Tarefas em andamento");
	}

	public void changeListCompleted() {
		taskList = repo.findByStatus(true);
		setLugar("Tarefas concluídas");
	}

	public void updateStatus(Task obj) {
		obj.setStatus(!obj.getStatus());
		repo.save(obj);
		lugar();
	}

	public String details(Task obj) {
		task = repo.findOne(obj.getId());
		return "/details-task.xhtml";
	}

	public String edit(Task obj) {
		task = repo.findOne(obj.getId());
		return "/edit-task.xhtml";
	}

	public String update() {
		repo.save(task);
		task = new Task();
		return "/index.xhtml";
	}

	public void deleteById(Integer id) {
		repo.delete(id);
		lugar();
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public Integer getQtTasksIncomplete() {
		this.qtTasksIncomplete = this.repo.findByStatus(false).size();
		return this.qtTasksIncomplete;
	}

	public void setQtTasksIncomplete(Integer qtTasksIncomplete) {
		this.qtTasksIncomplete = qtTasksIncomplete;
	}

	public Integer getQtTasksCompleted() {
		this.qtTasksCompleted = this.repo.findByStatus(true).size();
		return qtTasksCompleted;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public void lugar(){
		if(getLugar()=="Tarefas")
			init();
		else if (getLugar()=="Tarefas em andamento")
			changeListActive();
		else
			changeListCompleted();
	}

	public String textPriority(){
		String priority[] = new String[]{"Alta","Média","Baixa"};
		return priority[(task.getPriority())-1];
	}

	public String textStatus(){
		Boolean status = task.getStatus();
		return status ? "Concluída" : "Em andamento";
	}

	public void clear(){
		task = new Task();
	}

}
