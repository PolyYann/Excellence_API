package com.excellencemassotherapie.controleur;

import com.excellencemassotherapie.DAO.ProduitDAO;
import com.excellencemassotherapie.modele.Produit;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.print.attribute.standard.PDLOverrideSupported;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletAffichage", value = "/ServletAffichage")
public class ServletAffichage extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String langue = (String) request.getAttribute("langue");
        request.setAttribute("langue", langue);

        
        ProduitDAO produitDAO = new ProduitDAO();
         List<Produit> listProduits = produitDAO.getAll();

        request.setAttribute("listProduits", listProduits);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/affichageProduitsServices.jsp");
        dispatcher.forward(request,response);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
