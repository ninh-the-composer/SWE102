package controller.admin;

import dao.AdminOrderDAO;
import entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ninh
 */
@WebServlet(name = "AdminDisplayOrdersServlet", urlPatterns = {"/admin-display-orders"})
public class AdminDisplayOrdersServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Customer user = (Customer) request.getSession().getAttribute("admin");
			if (user == null) {
				response.sendRedirect("login");
				return;
			}
		AdminOrderDAO orderAccess = new AdminOrderDAO();
		
		int OrdersAPage = 8;
		List<Integer> listPage = new ArrayList<>();
		for(int i = 1, j = 0; j < orderAccess.countOrders(); i++, j+=OrdersAPage){
			listPage.add(i);
		}
		String rPage = request.getParameter("page");
		int page = rPage == null ? 1 : Integer.parseInt(rPage);
		
		request.setAttribute("pages", listPage);
		request.setAttribute("orders", orderAccess.getOrderedByUserId(page, OrdersAPage));
		request.getRequestDispatcher("admin-display-orders.jsp").forward(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
