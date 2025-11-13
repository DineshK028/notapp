package com.notapp.dao;

import com.notapp.entity.Note;
import com.notapp.entity.User;
import com.notapp.helper.FactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class NoteDao {
    private SessionFactory factory;
    
    public NoteDao() {
        this.factory = FactoryProvider.getFactory();
    }
    
    public boolean saveNote(Note note) {
        boolean flag = false;
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            session.save(note);
            session.getTransaction().commit();
            session.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    public List<Note> getNotesByUser(User user) {
        List<Note> notes = null;
        try {
            Session session = factory.openSession();
            Query<Note> query = session.createQuery("from Note where user=:u order by addedDate desc", Note.class);
            query.setParameter("u", user);
            notes = query.list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }
    
    public Note getNoteById(int noteId) {
        Note note = null;
        try {
            Session session = factory.openSession();
            note = session.get(Note.class, noteId);
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }
    
    public boolean updateNote(Note note) {
        boolean flag = false;
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            session.update(note);
            session.getTransaction().commit();
            session.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    public boolean deleteNote(int noteId) {
        boolean flag = false;
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            Note note = session.get(Note.class, noteId);
            if (note != null) {
                session.delete(note);
                session.getTransaction().commit();
                flag = true;
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}