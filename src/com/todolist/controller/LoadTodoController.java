package com.todolist.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.todolist.dao.TodoDaoImpl;
import com.todolist.model.Todo;

@WebServlet("/LoadTodoController")
public class LoadTodoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDaoImpl todoDaoImpl;

    @Resource(name = "jdbc/todoapp")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
	super.init();

	try {
	    todoDaoImpl = new TodoDaoImpl(dataSource);
	} catch (Exception e) {
	    throw new ServletException(e);
	}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String todoId = request.getParameter("tempId");
	try {
	    Todo todo = todoDaoImpl.loadTodo(todoId);
	    request.setAttribute("the_todo", todo);
	    request.getRequestDispatcher("views/update-todo.jsp").forward(request, response);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
