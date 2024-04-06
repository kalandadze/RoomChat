package com.mziuri.Servlet;

import com.mziuri.Request.AddRoomRequest;
import com.mziuri.Response.AddRoomResponse;
import com.mziuri.Response.GetRoomsResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetRoomsResponse response=new GetRoomsResponse();
        resp.getWriter().write(response.getResultString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("name");
        String limit=req.getParameter("limit");
        if (name.isEmpty()||limit.isEmpty()){
            resp.sendError(401);
        }else {
            AddRoomRequest request = new AddRoomRequest(name, limit);
            AddRoomResponse response = new AddRoomResponse(request);
        }
    }
}
