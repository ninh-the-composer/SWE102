package controller.admin;

import dao.AdminProductDAO;
import entity.Customer;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminAddProductServlet", urlPatterns = {"/admin-display-product"})
public class AdminDisplayProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			Customer user = (Customer) request.getSession().getAttribute("admin");
			if (user == null) {
				response.sendRedirect("login");
				return;
			}
			System.out.println(request.getSession().getAttribute("user"));
			String pageStr = request.getParameter("page");
			int page = 1;

			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
            }

            AdminProductDAO apd = new AdminProductDAO();

            int numberProduct = 8;
            int totalProduct = apd.countTotalProduct();
            int totalPage = (totalProduct % numberProduct == 0) ? totalProduct / numberProduct : totalProduct / numberProduct + 1;

            List<Integer> lsPage = new ArrayList<>();
            for (int i = 1; i <= totalPage; ++i) {
                lsPage.add(i);
            }

            List<Product> lsProduct = apd.getProductPerPage((page * numberProduct - numberProduct), numberProduct);

            request.setAttribute("page", page);
            request.setAttribute("lsPage", lsPage);
            request.setAttribute("lsProduct", lsProduct);
            request.getRequestDispatcher("AdminDisplayProduct.jsp").forward(request, response);

        }
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
        processRequest(request, response);
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
