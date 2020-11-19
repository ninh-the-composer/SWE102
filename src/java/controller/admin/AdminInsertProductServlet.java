/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.AdminCategoryDAO;
import dao.AdminProductDAO;
import entity.Category;
import entity.Customer;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "AdminInsertProductServlet", urlPatterns = {"/admin-add-product"})
public class AdminInsertProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Customer user = (Customer) request.getSession().getAttribute("admin");
			if (user == null) {
				response.sendRedirect("login");
				return;
			}
                    AdminProductDAO apd = new AdminProductDAO();
        AdminCategoryDAO acd = new AdminCategoryDAO();
		
        List<Category> listCategory = acd.getAll();

        request.setAttribute("listCategory", listCategory);
        request.getRequestDispatcher("AdminAddProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminProductDAO apd = new AdminProductDAO();

        String name = request.getParameter("pName");
        int categoryID = Integer.parseInt(request.getParameter("pCategory"));
        double price = Double.parseDouble(request.getParameter("pPrice"));
        String description = request.getParameter("pDescription");

        Product p = new Product();
        p.setName(name);
        p.setCategory(categoryID + "");
        p.setPrice(price);
        p.setDescription(description);

        apd.add(p);

        response.sendRedirect("admin-display-product");
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
