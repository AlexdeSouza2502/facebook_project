package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"", "/save"})
public class UsersController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String action = req.getRequestURI();
		
		System.out.println(action);
		
		switch (action) {
		case "/facebook/": {
			//Carregar a lista de usuarios
			UserDAO dao = DAOFactory.createDAO(UserDAO.class);
			
			try {
				List<model.User> users = dao.listAll();
				//colocar ela na requisição
				req.setAttribute("usuarios", users);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//redirecionar para a index.jsp
			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, resp);
			
			break;
		} 
		case "/facebook/save":{
			//Recuperar dados da requisição
			String userName = req.getParameter("user_name");
			String userEmail = req.getParameter("user_email");
			String userGender = req.getParameter("user_gender");
			//criar objeto do tipo usuario
			User user = new User();
			user.setEmail(userEmail);
			user.setGender(userGender);
			user.setName(userName);
			
			
			//criar dao
			UserDAO dao = DAOFactory.createDAO(UserDAO.class);
			
			//usar o usuario
			try {
				dao.save(user);
				
				resp.sendRedirect("/facebook");
				
			}catch(ModelException e){
				e.printStackTrace();
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
		
		
	}

}
