package com.todolist.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.todolist.dao.TodoDao;
import com.todolist.dao.TodoDaoImpl;
import com.todolist.model.Todo;

@WebServlet("/LoadTodoController")
public class LoadTodoController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private TodoDao todoDao;
  private Logger logger;

  @Resource(name = "jdbc/todoapp")
  private DataSource dataSource;

  @Override
  public void init() throws ServletException {
    super.init();
    try {
      todoDao = new TodoDaoImpl(dataSource);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      String todoId = request.getParameter("tempId");
      Todo todo = todoDao.loadTodo(todoId);
      request.setAttribute("the_todo", todo);
      request.getRequestDispatcher("views/update-todo.jsp").forward(request, response);
    } catch (Exception e) {
      logger.warning("Error in update todo");
    }
  }
}
