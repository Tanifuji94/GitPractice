package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.kadai1DAO;
import dto.kadai1dto;
import util.kadai1HashedPw;

/**
 * Servlet implementation class kadai1LoginServlet
 */
@WebServlet("/kadai1LoginServlet")
public class kadai1LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public kadai1LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getParameter("UTF-8");
		
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		String salt = kadai1DAO.getSalt(mail);
		
		if(salt == null) {
			String view = "WEB-INF/view/kadai1-login.jsp?error=1";
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
			return;
		}
			String hashedPw = kadai1HashedPw.getSafetyPassword(password, salt);
			
			System.out.println("DBから取得したソルト：" + salt);
			System.out.println("入力したPWから生成したハッシュ値：" + hashedPw);
			
			kadai1dto kadai1 = kadai1DAO.login(mail, hashedPw);
			
			if(kadai1 == null) {
				String view = "WEB-INF/view/kadai1-login.jsp?error=1";
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);
			} else {
				String view = "WEB-INF/view/sample-menu.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

