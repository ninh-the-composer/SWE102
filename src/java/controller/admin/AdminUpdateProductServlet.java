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

@WebServlet(name = "AdminUpdateProductServlet", urlPatterns = {"/admin-update-product"})
public class AdminUpdateProductServlet extends HttpServlet {

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
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = apd.getOne(id);

        request.setAttribute("p", p);
        request.setAttribute("listCategory", listCategory);
        request.getRequestDispatcher("AdminUpdateProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        AdminProductDAO apd = new AdminProductDAO();

        int id = Integer.parseInt(request.getParameter("pID"));
        String name = request.getParameter("pName");
        int categoryID = Integer.parseInt(request.getParameter("pCategory"));
        double price = Double.parseDouble(request.getParameter("pPrice"));
        String description = request.getParameter("pDescription");

        Product p = new Product();
        p.setName(name);
        p.setCategory(categoryID + "");
        p.setPrice(price);
        p.setDescription(description);

        apd.update(p, id);

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
