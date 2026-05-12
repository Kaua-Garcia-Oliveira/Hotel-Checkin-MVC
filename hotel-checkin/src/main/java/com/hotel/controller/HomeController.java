package com.hotel.controller;

import com.hotel.dao.HospedeDAO;
import com.hotel.dao.QuartoDAO;
import com.hotel.dao.ReservaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/", "/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
            try {
                HospedeDAO hospedeDAO = new HospedeDAO();
                QuartoDAO quartoDAO = new QuartoDAO();
                ReservaDAO reservaDAO = new ReservaDAO();

                req.setAttribute("totalHospedes", hospedeDAO.listar().size());
                req.setAttribute("totalQuartos", quartoDAO.listar().size());
                req.setAttribute("totalReservas", reservaDAO.listar().size());

            } catch (Exception e) {
                req.setAttribute("totalHospedes", 0);
                req.setAttribute("totalQuartos", 0);
                req.setAttribute("totalReservas", 0);
            }


            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
