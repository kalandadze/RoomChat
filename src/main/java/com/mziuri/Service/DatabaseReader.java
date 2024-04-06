package com.mziuri.Service;

import com.mziuri.Classes.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class DatabaseReader {
    private final EntityManagerFactory factory;

    private static  DatabaseReader reader=null;

    private DatabaseReader() {
        this.factory = Persistence.createEntityManagerFactory("unit");
    }
    public static DatabaseReader getReader(){
        if(reader==null){
            reader=new DatabaseReader();
        }
        return reader;
    }
    public List<Room> findRooms(){
        EntityManager manager=factory.createEntityManager();
        manager.getTransaction().begin();
        Query query=manager.createQuery("from Room");
        List<Room> rooms=query.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return rooms;
    }
    public void addRoom(Room room){
        EntityManager manager=factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(room);
        manager.getTransaction().commit();
        manager.close();
    }
}
